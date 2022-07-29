package br.com.mucatour.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import br.com.mucatour.payload.request.SignupRequest;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "personal")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Personal implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_seq_gen")
    @SequenceGenerator(name = "personal_seq_gen", sequenceName = "personal_id_seq")
    private Long id;
    @Size(max = 11)
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

    public Personal(String cpf,
                    Date birthday,
                    String rg,
                    String passportNumber,
                    boolean driverPass,
                    String driverNumber) {
        
        this.cpf = cpf;
        this.birthday = birthday;
        this.rg = rg;
        this.passportNumber = passportNumber;
        this.driverPass = driverPass;
        this.driverNumber = driverNumber;
    }

    public static Personal build(SignupRequest signUpRequest) {
        return new Personal(
            signUpRequest.getPersonal().getCpf(),
            signUpRequest.getPersonal().getBirthday(),
            signUpRequest.getPersonal().getRg(),
            signUpRequest.getPersonal().getPassportNumber(),
            signUpRequest.getPersonal().isDriverPass(),
            signUpRequest.getPersonal().getDriverNumber()
        );
    }
}
