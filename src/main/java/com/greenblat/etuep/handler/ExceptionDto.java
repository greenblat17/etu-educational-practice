package com.greenblat.etuep.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

    private String message;
    private LocalDateTime localDateTime;
    private HttpStatus httpStatus;
}
