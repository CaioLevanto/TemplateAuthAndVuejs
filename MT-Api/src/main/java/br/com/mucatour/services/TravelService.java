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
import br.com.mucatour.model.travel.enums.GoBankEnum;
import br.com.mucatour.model.user.User;
import br.com.mucatour.payload.request.viagem.ViagemRequest;
import br.com.mucatour.payload.response.MessageResponse;
import br.com.mucatour.repository.TravelRepository;
import br.com.mucatour.repository.UserRepository;

@Service
public class TravelService {

    static String MESSAGE_ERROR = "";

    UserRepository userRepository;
    TravelRepository travelRepository;
    SchedulerJobService jobService;

    @Autowired
    public TravelService(UserRepository userRepository, TravelRepository travelRepository,
            SchedulerJobService jobService) {
        this.userRepository = userRepository;
        this.travelRepository = travelRepository;
        this.jobService = jobService;
    }

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
                    throw new TravelException("Não foi possível realizar solicitação da viagem, ocorreu um erro e deve contatar o administrador.");
                }
            } else {
                throw new TravelException("Não foi possível localizar o usuário, contate um administrador.");
            }
        }
        throw new TravelException("Solicitação de viagem não foi possível ser realizada por falta de informações válidas.");
    }

    private Boolean validateInfoTravel(ViagemRequest vRequest) throws TravelException {
        Instant startDate = Instant.parse(vRequest.getStartDate());
        Instant backDate = Instant.parse(vRequest.getBackDate());

        if (travelRepository.existsTravelByUser(vRequest.getUserId(),
                startDate.getEpochSecond(), backDate.getEpochSecond())) {
            throw new TravelException("Já existe uma viagem agendada confirmada para a data solitada, permitido solicitar somente uma viagem dentro de cada periodo.");
        }

        if (vRequest.getUserId() != null) {
            if (!userRepository.existsById(vRequest.getUserId())) {
                throw new TravelException("Usuário não foi possível encontrar registrado em nosso banco de dados.");
            }
        } else {
            throw new TravelException("Necessário existir usuário dentro da solicitação.");
        }

        if (vRequest.getRadioGoBack().equals(GoBankEnum.IDA_E_VOLTA.ordinal())) {
            if (startDate.isAfter(backDate) || startDate.equals(backDate)) {
                throw new TravelException("Data inicial não pode ser maior ou igual que a data final.");
            } else if (startDate.equals(Instant.now())) {
                throw new TravelException("Data inicial não pode ser igual ou menor ao seu dia atual.");
            }

            if (backDate.isBefore(startDate) || backDate.equals(startDate)) {
                throw new TravelException("Data final não pode ser menor ou igual que a data inicial.");
            } else if (backDate.equals(Instant.now())) {
                throw new TravelException("Data final não pode ser igual ou menor ao seu dia atual.");
            }
        } else {
            if (startDate.isBefore(Instant.now()) || startDate.equals(Instant.now())) {
                throw new TravelException("Data inicial não pode ser igual ou menor ao seu dia atual.");
            }
        }
        return true;
    }

}
