package br.com.mucatour.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String email;
    private String name;
    private List<String> roles;

    public JwtResponse(String accessToken, 
                       String refreshToken,    
                       Long id, 
                       String email,
                       String name,
                       List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }
}
