package com.tecnocampus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Usuari not found")
public class UsuariNotFoundException extends RuntimeException {
    public UsuariNotFoundException(String message) {
        super(message);
    }
}

