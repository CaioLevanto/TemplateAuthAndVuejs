package br.com.mucatour.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import br.com.mucatour.payload.request.user.model.PersonalRequest;

import java.time.Instant;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table( uniqueConstraints = {
        @UniqueConstraint(columnNames = "cpf"),
        @UniqueConstraint(columnNames = "rg")
})
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Personal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personal_seq_gen")
    @SequenceGenerator(name = "personal_seq_gen", sequenceName = "personal_id_seq")
    private Long id;

    @OneToOne(mappedBy = "personal")
    private User user;

    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "birthday", nullable = false)
    private long birthday;

    @Column(name = "rg", nullable = false)
    private String rg;

    @Column(name = "passport_number", nullable = true)
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
