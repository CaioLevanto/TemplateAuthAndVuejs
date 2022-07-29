package br.com.mucatour.payload.request.model;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRequest {
    @Size(max = 11)
    @NotBlank
    private String cpf;
    @NotBlank
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthday;
    @NotBlank
    private String rg;
    @NotBlank
    private String passportNumber;
    @NotBlank
    private boolean driverPass;
    private String driverNumber;
}
