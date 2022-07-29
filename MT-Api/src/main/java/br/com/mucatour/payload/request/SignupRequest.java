package br.com.mucatour.payload.request;

import br.com.mucatour.payload.request.model.AddressRequest;
import br.com.mucatour.payload.request.model.PersonalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private Set<String> role;
    @NotBlank
    private Long phone;
    @NotBlank
    private PersonalRequest personal;
    @NotBlank
    private AddressRequest address;
}
