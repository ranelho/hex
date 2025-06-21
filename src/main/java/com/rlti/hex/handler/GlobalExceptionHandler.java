package com.rlti.hex.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<StandardError>> handleValidationException(MethodArgumentNotValidException ex) {
        List<StandardError> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            StandardError error = StandardError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .error("Validation Error")
                    .message(fieldError.getDefaultMessage())
                    .timestamp(LocalDateTime.now())
                    .uuid(UUID.randomUUID().toString())
                    .build();
            errors.add(error);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        StandardError error = StandardError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code(HttpStatus.BAD_REQUEST.value())
                .error("Malformed JSON request")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .uuid(UUID.randomUUID().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> handleRuntimeException(RuntimeException ex) {
        StandardError error = StandardError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .uuid(UUID.randomUUID().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<StandardError> handleResourceNotFoundException(ResourceNotFoundException ex) {
        StandardError error = StandardError.builder()
                .status(HttpStatus.NOT_FOUND)
                .code(HttpStatus.NOT_FOUND.value())
                .error("Resource Not Found")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .uuid(UUID.randomUUID().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicidadeException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<StandardError> handleDuplicidadeException(DuplicidadeException ex) {
        StandardError error = StandardError.builder()
                .status(HttpStatus.CONFLICT)
                .code(HttpStatus.CONFLICT.value())
                .error("Resource Conflict")
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .uuid(UUID.randomUUID().toString())
                .build();
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
