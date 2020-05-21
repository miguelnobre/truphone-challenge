package com.truphone.challenge.config;

import com.truphone.challenge.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice("com.truphone.challenge.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> notFound(NotFoundException ex) {
        log.error(ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
