package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.DependentRequest;
import com.rlti.hex.adapters.input.api.response.DependentResponse;
import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.port.input.InsertDependentToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateDependentInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dependent", description = "API para gerenciamento de dependentes")
@RequestMapping("/dependent")
public class DependentController {

    private final UpdateDependentInputPort updateDependentInputPort;
    private final InsertDependentToPersonInputPort insertDependentToPersonInputPort;

    @Operation(
        summary = "Atualizar dependente",
        description = "Atualiza as informações de um dependente existente através do seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Dependente atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = DependentResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Dependente não encontrado",
            content = @Content(mediaType = "application/json",
                      schema = @Schema(implementation = Object.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Dados de entrada inválidos",
            content = @Content(mediaType = "application/json",
                      schema = @Schema(implementation = Object.class))
        )
    })
    @PatchMapping("/{id}")
    public ResponseEntity<DependentResponse> updateDependent(
        @Parameter(description = "ID do dependente a ser atualizado", required = true, example = "1")
        @PathVariable Long id, 

        @Parameter(description = "Informações atualizadas do dependente", required = true)
        @Valid @RequestBody DependentRequest request) {
        Dependent dependent = updateDependentInputPort.update(
            id,
            request.name(),
            request.cpf(),
            request.birthDate(),
            request.dependentType()
        );
        return ResponseEntity.ok(new DependentResponse(dependent));
    }

    @Operation(
        summary = "Adicionar dependente a uma pessoa",
        description = "Cria um novo dependente e o associa a uma pessoa existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Dependente criado e associado com sucesso",
            content = @Content(schema = @Schema(implementation = DependentResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Pessoa não encontrada",
            content = @Content(mediaType = "application/json",
                      schema = @Schema(implementation = Object.class))
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "Dados de entrada inválidos",
            content = @Content(mediaType = "application/json",
                      schema = @Schema(implementation = Object.class))
        )
    })
    @PostMapping("/person/{idPerson}")
    public ResponseEntity<DependentResponse> insertDependentToPerson(
            @Parameter(description = "ID da pessoa a qual o dependente será associado", required = true, example = "1")
            @PathVariable Long idPerson,

            @Parameter(description = "Informações do dependente a ser criado", required = true)
            @Valid @RequestBody DependentRequest request
    ) {
        Dependent dependent = insertDependentToPersonInputPort.insert(request.toDomain(), idPerson);
        return ResponseEntity.ok(new DependentResponse(dependent));
    }
}