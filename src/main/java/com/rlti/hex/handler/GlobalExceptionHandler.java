package com.rlti.hex.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String TIMESTAMP = "timestamp";

    @ExceptionHandler(DuplicidadeException.class)
    public ResponseEntity<Object> handleDuplicidadeException(DuplicidadeException ex) {
        logger.warn("Erro de duplicidade: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                ERROR, "Duplicidade de Registro",
                MESSAGE, ex.getMessage(),
                TIMESTAMP, Instant.now()
        ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        logger.error("Erro inesperado: ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                ERROR, "Erro Interno",
                MESSAGE, ex.getMessage(),
                TIMESTAMP, Instant.now()
        ));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> handleEntidadeException(ResourceNotFoundException ex) {
        StandardError error = new StandardError("id", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
