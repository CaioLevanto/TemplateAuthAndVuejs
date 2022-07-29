package br.com.mucatour.payload.request.model;

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
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String addressName;
    @NotBlank
    private Long numberAddress;
}
