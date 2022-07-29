package br.com.mucatour.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import br.com.mucatour.payload.request.SignupRequest;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq_gen")
    @SequenceGenerator(name = "address_seq_gen", sequenceName = "address_id_seq")
    private Long id;
    private String cep;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String addressName;
    @NotBlank
    private Long numberAddress;

    public Address(String cep,
                   String city,
                   String state,
                   String addressName,
                   Long numberAddress) {

        this.cep = cep;
        this.city = city;
        this.state = state;
        this.addressName = addressName;
        this.numberAddress = numberAddress;
    }

    public static Address build(SignupRequest signUpRequest) {
        return new Address(
            signUpRequest.getAddress().getCep(),
            signUpRequest.getAddress().getCity(),
            signUpRequest.getAddress().getState(),
            signUpRequest.getAddress().getAddressName(),
            signUpRequest.getAddress().getNumberAddress()
        );
    }
}
