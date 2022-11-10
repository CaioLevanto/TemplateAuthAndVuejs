package br.com.mucatour.exception;

import lombok.AllArgsConstructor;
import lombok.experimental.StandardException;

@AllArgsConstructor
@StandardException
public class TravelException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String message;
}
