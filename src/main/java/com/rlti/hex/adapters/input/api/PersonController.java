package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.PersonRequest;
import com.rlti.hex.adapters.input.api.request.PersonUpdateRequest;
import com.rlti.hex.adapters.input.api.response.PageResult;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.port.input.DeletePersonInputPort;
import com.rlti.hex.application.port.input.FindPersonInputPort;
import com.rlti.hex.application.port.input.InsertPersonInputPort;
import com.rlti.hex.application.port.input.UpdatePersonInputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Tag(name = "Person", description = "Person API")
@RequestMapping("/person")
public class PersonController {

    private final InsertPersonInputPort insertPersonInputPort;
    private final FindPersonInputPort findPersonInputPort;
    private final UpdatePersonInputPort updatePersonInputPort;
    private final DeletePersonInputPort deletePersonInputPort;

    @Operation(summary = "Create a new person", description = "Create a new person with provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest request) {
        Fisica personRequest = request.toDomain();
        var response = insertPersonInputPort.insert(personRequest);
        URI location = UriComponentsBuilder.fromPath("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(new PersonResponse(response));
    }

    @Operation(summary = "Find person by ID", description = "Get a person's details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person found successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findPerson(@PathVariable Long id) {
        var response = findPersonInputPort.find(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Find all persons", description = "Get a paginated list of all persons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping("/all")
    public ResponseEntity<PageResult<PersonResponse>> findAll(
            @Parameter(description = "Page number (starts from 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "12") int size
    ) {
        var response = findPersonInputPort.findAll(page, size);
        return ResponseEntity.ok(PersonResponse.convertToPageResult(response));
    }

    @Operation(summary = "Update person", description = "Update an existing person's details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonUpdateRequest request) {
        Fisica person = updatePersonInputPort.update(request.updateDomain(), id);

        return ResponseEntity.ok(new PersonResponse(person));
    }

    @Operation(summary = "Delete person", description = "Delete a person by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Person deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Person not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        deletePersonInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }
}