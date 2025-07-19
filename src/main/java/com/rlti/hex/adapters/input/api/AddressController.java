package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.response.AddressResponse;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.port.input.InsertAddressToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateAddressInputPort;
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
@Tag(name = "Address", description = "API para gerenciamento de endereços")
@RequestMapping("/address")
public class AddressController {

    private final UpdateAddressInputPort updateAddressInputPort;
    private final InsertAddressToPersonInputPort insertAddressToPersonInputPort;

    @Operation(
        summary = "Atualizar endereço",
        description = "Atualiza as informações de um endereço existente através do seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Endereço atualizado com sucesso",
            content = @Content(mediaType = "application/json", 
                      schema = @Schema(implementation = AddressResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Endereço não encontrado",
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
    public ResponseEntity<AddressResponse> updateAddress(
        @Parameter(description = "ID do endereço a ser atualizado", required = true, example = "1")
        @PathVariable Long id, 

        @Parameter(description = "Informações atualizadas do endereço", required = true)
        @Valid @RequestBody AddressRequest request) {
        Address updatedAddress = updateAddressInputPort.update(request.toDomain(), id);
        return ResponseEntity.ok(new AddressResponse(updatedAddress));
    }

    @Operation(
        summary = "Adicionar endereço a uma pessoa",
        description = "Cria um novo endereço e o associa a uma pessoa existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Endereço criado e associado com sucesso",
            content = @Content(mediaType = "application/json", 
                      schema = @Schema(implementation = AddressResponse.class))
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
    public ResponseEntity<AddressResponse> insertAddressToPerson(
            @Parameter(description = "ID da pessoa a qual o endereço será associado", required = true, example = "1")
            @PathVariable Long idPerson,

            @Parameter(description = "Informações do endereço a ser criado", required = true)
            @Valid @RequestBody AddressRequest request
    ) {
        Address address = insertAddressToPersonInputPort.insert(request.toDomain(), idPerson);
        return ResponseEntity.ok(new AddressResponse(address));
    }
}