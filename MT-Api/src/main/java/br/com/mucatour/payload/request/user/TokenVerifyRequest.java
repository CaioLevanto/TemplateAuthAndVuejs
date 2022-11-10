package br.com.mucatour.payload.request.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVerifyRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String token;
}
