package com.rlti.hex.adapters.input.api;

import com.rlti.hex.application.core.domain.enuns.Gender;
import com.rlti.hex.application.core.domain.enuns.MaritalStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/enums")
@Tag(name = "Enums", description = "Endpoints para buscar valores de enumerações")
public class EnumController {

    @Operation(
            summary = "Buscar estados civis",
            description = "Retorna uma lista de todos os estados civis disponíveis"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estados civis encontrados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/marital-status")
    public ResponseEntity<List<String>> findMaritalStatus() {
        List<String> maritalStatuses = Stream.of(MaritalStatus.values())
                .map(MaritalStatus::getDescription)
                .toList();
        return ResponseEntity.ok(maritalStatuses);
    }

    @Operation(
            summary = "Buscar gêneros",
            description = "Retorna uma lista de todos os gêneros disponíveis"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Gêneros encontrados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))
            )
    })
    @GetMapping("/gender")
    public ResponseEntity<List<String>> findGender() {
        List<String> genders = Stream.of(Gender.values())
                .map(Gender::getDescription)
                .toList();
        return ResponseEntity.ok(genders);
    }
}