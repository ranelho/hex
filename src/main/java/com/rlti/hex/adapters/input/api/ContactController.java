package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.response.ContactResponse;
import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.port.input.InsertContactToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateContactInputPort;
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
@Tag(name = "Contact", description = "API para gerenciamento de contatos")
@RequestMapping("/contact")
public class ContactController {

    private final UpdateContactInputPort updateContactInputPort;
    private final InsertContactToPersonInputPort insertContactToPersonInputPort;

    @Operation(
        summary = "Atualizar contato",
        description = "Atualiza as informações de um contato existente através do seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Contato atualizado com sucesso",
            content = @Content(mediaType = "application/json", 
                      schema = @Schema(implementation = ContactResponse.class))
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Contato não encontrado",
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
    public ResponseEntity<ContactResponse> updateContact(
        @Parameter(description = "ID do contato a ser atualizado", required = true, example = "1")
        @PathVariable Long id, 

        @Parameter(description = "Informações atualizadas do contato", required = true)
        @Valid @RequestBody ContactRequest request) {
        Contact contact = updateContactInputPort.update(request.toDomain(), id);
        return ResponseEntity.ok(new ContactResponse(contact));
    }

    @Operation(
        summary = "Adicionar contato a uma pessoa",
        description = "Cria um novo contato e o associa a uma pessoa existente"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Contato criado e associado com sucesso",
            content = @Content(mediaType = "application/json", 
                      schema = @Schema(implementation = ContactResponse.class))
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
    public ResponseEntity<ContactResponse> insertContactToPerson(
            @Parameter(description = "ID da pessoa a qual o contato será associado", required = true, example = "1")
            @PathVariable Long idPerson,

            @Parameter(description = "Informações do contato a ser criado", required = true)
            @Valid @RequestBody ContactRequest request
    ) {
        Contact contact = insertContactToPersonInputPort.insert(request.toDomain(), idPerson);
        return ResponseEntity.ok(new ContactResponse(contact));
    }
}