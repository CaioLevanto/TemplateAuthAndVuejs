package br.com.mucatour.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import br.com.mucatour.payload.request.user.model.AddressRequest;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_gen")
    @SequenceGenerator(name = "address_seq_gen", sequenceName = "address_id_seq")
    private Long id;

    @OneToOne(mappedBy = "address")
    private User user;

    @Column(name = "cep", nullable = true, length = 10)
    private String cep;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "zipcode", nullable = false)
    private String zipcode;

    @Column(name = "number_address", nullable = false)
    private String numberAddress;

    public static Address buildToAddress(AddressRequest addr) {
        return Address.builder()
                .cep(addr.getCep())
                .state(addr.getState())
                .city(addr.getCity())
                .zipcode(addr.getZipcode())
                .numberAddress(addr.getNumberAddress())
                .build();
    }
}
