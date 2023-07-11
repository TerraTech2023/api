package com.terratech.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(NotFoundException error) {
        var errorStatus = NOT_FOUND;
        var errorMessage = error.getMessage();
        var timestamp = LocalDateTime.now();
        var response = new ErrorResponse(errorMessage, errorStatus.value(), timestamp.toString());
        log.error("ERROR {}", errorMessage);
        return new ResponseEntity<>(response, errorStatus);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(CONFLICT)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ConflictException error) {
        var errorStatus = CONFLICT;
        var errorMessage = error.getMessage();
        var timestamp = LocalDateTime.now();
        var response = new ErrorResponse(errorMessage, errorStatus.value(), timestamp.toString());
        log.error("ERROR {}", errorMessage);
        return new ResponseEntity<>(response, errorStatus);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(Exception error) {
        var errorStatus = INTERNAL_SERVER_ERROR;
        var errorMessage = error.getMessage();
        var timestamp = LocalDateTime.now();
        var response = new ErrorResponse(errorMessage, errorStatus.value(), timestamp.toString());
        log.error("ERROR {}", error.getMessage());
        return new ResponseEntity<>(response, errorStatus);
    }
}
