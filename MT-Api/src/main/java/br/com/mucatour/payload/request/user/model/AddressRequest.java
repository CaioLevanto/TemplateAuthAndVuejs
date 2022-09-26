package br.com.mucatour.payload.request.user.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String cep;
    @NotBlank
    private String state;
    @NotBlank
    private String city;
    @NotBlank
    private String zipcode;
    @NotBlank
    private String numberAddress;
}
