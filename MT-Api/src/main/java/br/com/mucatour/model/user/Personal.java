package br.com.mucatour.model.user;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import br.com.mucatour.payload.request.user.model.PersonalRequest;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table(name = "personal", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf"),
        @UniqueConstraint(columnNames = "rg"),
        @UniqueConstraint(columnNames = "passportNumber")
})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Personal implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_seq_gen")
    @SequenceGenerator(name = "personal_seq_gen", sequenceName = "personal_id_seq")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "cpf_personal", nullable = false)
    private String cpf;

    @Column(name = "birthday_at", nullable = false)
    private long birthday;

    @Column(name = "rg_personal", nullable = false)
    private String rg;

    @Column(name = "passport_number", nullable = false)
    private String passportNumber;

    @Column(name = "driver_pass", nullable = false)
    private boolean driverPass;

    @Column(name = "driver_number", nullable = true)
    private String driverNumber;

    public static Personal buildToPersonal(PersonalRequest pr) {
        return Personal.builder()
                .cpf(pr.getCpf())
                .birthday(Instant.parse(pr.getBirthday()).getEpochSecond())
                .rg(pr.getRg())
                .passportNumber(pr.getPassportNumber())
                .driverPass(pr.isDriverPass())
                .driverNumber(pr.getDriverNumber())
                .build();
    }
}
