package com.greenblat.etuep.handler;

import com.greenblat.etuep.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = ResourceNotFoundException.class)
    public ExceptionDto handleException(ResourceNotFoundException e) {
        ExceptionDto exceptionDto = new ExceptionDto(
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );
        return exceptionDto;
    }
}
