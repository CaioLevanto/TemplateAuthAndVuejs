package br.com.mucatour.payload.request.viagem;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import br.com.mucatour.model.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViagemRequest {
    @NotBlank(message = "É obrigatório informar o local de origem")
    private String origin;

    @NotBlank(message = "É obrigatório informar o local de destino")
    private String destiny;

    @NotBlank(message = "Necessário possuir um usuário vínculado na solicitação")
    private Long userId;

    private User user;

    @NotBlank(message = "É obrigatório informar data de partida")
    private String startDate;

    private String backDate;

    @NotBlank(message = "Necessário informar tipo de retorno ou ida")
    private Integer radioGoBack;

    @Min(value = 0, message = "Quantidade de adultos na viagem não pode ser menor do que 0")
    private Integer adult;
    
    @Min(value = 0, message = "Quantidade de criança na viagem não pode ser menor do que 0")
    private Integer kids;
}
