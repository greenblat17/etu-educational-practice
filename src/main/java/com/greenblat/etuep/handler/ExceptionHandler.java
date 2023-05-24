package com.greenblat.etuep.handler;

import com.greenblat.etuep.exception.ResourceNotFoundException;
import com.greenblat.etuep.exception.UserNotRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ResourceNotFoundException.class)
    public ExceptionDto handleException(ResourceNotFoundException e) {
        return new ExceptionDto(
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = UserNotRegisterException.class)
    public ExceptionDto handleException(UserNotRegisterException e) {
        return new ExceptionDto(
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );
    }
}
