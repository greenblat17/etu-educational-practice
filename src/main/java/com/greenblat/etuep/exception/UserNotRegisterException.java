package com.greenblat.etuep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNotRegisterException extends RuntimeException {
    public UserNotRegisterException(String message) {
        super(message);
    }
}
