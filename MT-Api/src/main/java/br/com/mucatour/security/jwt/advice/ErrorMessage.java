package br.com.mucatour.security.jwt.advice;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;

    public ErrorMessage(int statusCode, Date timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp;
        this.message = message;
        this.description = description;
    }

    public ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}