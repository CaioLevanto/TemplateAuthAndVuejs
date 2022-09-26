package br.com.mucatour.services;

import java.time.Instant;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mucatour.exception.TravelException;
import br.com.mucatour.model.job.enums.ListJobs;
import br.com.mucatour.model.travel.entity.Travel;
import br.com.mucatour.model.user.User;
import br.com.mucatour.payload.request.viagem.ViagemRequest;
import br.com.mucatour.payload.response.MessageResponse;
import br.com.mucatour.repository.TravelRepository;
import br.com.mucatour.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TravelService {

    static String MESSAGE_ERROR = "";
    UserRepository userRepository;
    TravelRepository travelRepository;
    SchedulerJobService jobService;

    @Transactional(rollbackFor = TravelException.class)
    public ResponseEntity<?> request(ViagemRequest trDto) throws TravelException {
        if (validateInfoTravel(trDto)) {
            Optional<User> user = userRepository.findById(trDto.getUserId());
            if (user.isPresent()) {
                Travel hasTravel = travelRepository.save(Travel.buildToTravel(trDto));

                if (jobService.newJob(hasTravel, ListJobs.TRAVEL_PENDENCY.getName())) {
                    return ResponseEntity.ok(
                            new MessageResponse(
                                    "Solicitação realizada com exito, aguarde o assessor confirmar sua viagem."));
                } else {
                    MESSAGE_ERROR = "Não foi possível realizar solicitação da viagem.";
                }
            } else {
                MESSAGE_ERROR = "Não foi possível localizar o usuário.";
            }
        }
        throw new TravelException(MESSAGE_ERROR);
    }

    private Boolean validateInfoTravel(ViagemRequest vRequest) {
        if (vRequest.getUserId() != null) {
            if (!userRepository.existsById(vRequest.getUserId())) {
                MESSAGE_ERROR = "Usuário não foi possível encontrar registrado em nosso banco de dados.";
                return false;
            }
        } else {
            MESSAGE_ERROR = "Necessário existir usuário dentro da solicitação.";
            return false;
        }

        Instant startDate = Instant.parse(vRequest.getStartDate());
        Instant backDate = Instant.parse(vRequest.getBackDate());

        if (startDate.isAfter(backDate)) {
            MESSAGE_ERROR = "Data inicial não pode ser maior ou igual que a data final.";
            return false;
        }
        if (backDate.isBefore(startDate)) {
            MESSAGE_ERROR = "Data final não pode ser menor ou igual que a data inicial.";
            return false;
        }
        if (travelRepository.existsTravelByUser(vRequest.getUserId(),
                startDate.getEpochSecond(), backDate.getEpochSecond())) {
            MESSAGE_ERROR = "Já existe uma viagem agendada confirmada para a data solitada, permitido solicitar somente uma viagem dentro de cada periodo.";
            return false;
        }
        return true;
    }

}
