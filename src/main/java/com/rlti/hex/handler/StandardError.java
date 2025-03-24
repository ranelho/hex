package com.rlti.hex.handler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "StandardError", description = "Schema padrão para erros")
public class StandardError {
    private String field;
    private String message;

    public StandardError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
