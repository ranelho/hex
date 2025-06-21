package com.rlti.hex.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Schema(name = "StandardError", description = "Schema padrão para erros")
public class StandardError {
    private HttpStatus status;
    private Integer code;
    private String error;
    private String message;
    private LocalDateTime timestamp;
    private String uuid;
}
