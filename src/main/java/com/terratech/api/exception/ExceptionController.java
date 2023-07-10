package com.terratech.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException error) {
         log.error("ERROR {}", error.getMessage());
         var errorStatus = error.getStatusCode();
         var errorMessage = error.getReason();
         var timestamp = LocalDateTime.now();
         var response = new ErrorResponse(errorMessage,errorStatus.value(), timestamp.toString());
         return new ResponseEntity<>(response, errorStatus);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(Exception error) {
         log.error("ERROR {}", error.getMessage());
         var errorStatus = INTERNAL_SERVER_ERROR;
         var errorMessage = error.getMessage();
         var timestamp = LocalDateTime.now();
         var response = new ErrorResponse(errorMessage, errorStatus.value(), timestamp.toString());
         return new ResponseEntity<>(response, errorStatus);
    }
}
