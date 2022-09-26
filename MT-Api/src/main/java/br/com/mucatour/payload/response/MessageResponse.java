package br.com.mucatour.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageResponse {
    private Object entity;
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(Object entity,
                    String message) {
        this.entity = entity;
        this.message = message;
    }
}
