package br.com.mucatour.payload.request.user.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRequest {
    @Size(max = 11)
    @NotBlank
    private String cpf;
    @NotBlank
    private String birthday;
    @NotBlank
    private String rg;
    @NotBlank
    private String passportNumber;
    @NotBlank
    private boolean driverPass;
    private String driverNumber;
}
