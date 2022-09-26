package br.com.mucatour.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.mucatour.payload.request.user.model.AddressRequest;
import br.com.mucatour.payload.request.user.model.PersonalRequest;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "Primeiro nome é um campo obrigatório")
    private String firstName;

    @NotBlank(message = "Sobrenome é um campo obrigatório")
    private String secondName;

    @NotBlank(message = "E-mail é um campo obrigatório")
    @Size(max = 50, message = "Tamanho máximo do e-mail atingido")
    @Email(message = "E-mail com formato inválido")
    private String email;

    @NotBlank(message = "A senha é um campo obrigatório")
    @Size(min = 6, max = 40, message = "A senha deve conter entre 6 a 40 caracteres")
    private String password;

    private Set<String> role;

    @NotBlank(message = "Telefone é um campo obrigatório para meio de contato")
    private String phone;
    
    @NotBlank(message = "Obrigatório preencher os dados pessoais")
    private PersonalRequest personal;

    @NotBlank(message = "Obrigatório preencher os dados do endereço")
    private AddressRequest address;
}
