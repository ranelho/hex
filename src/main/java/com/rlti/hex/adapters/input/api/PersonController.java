package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.PersonRequest;
import com.rlti.hex.adapters.input.api.request.PersonUpdateRequest;
import com.rlti.hex.adapters.input.api.response.PageResult;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.core.domain.Fisica;
import com.rlti.hex.application.port.input.FindPersonInputPort;
import com.rlti.hex.application.port.input.InsertPersonInputPort;
import com.rlti.hex.application.port.input.UpdatePersonInputPort;
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

    @PostMapping
    public ResponseEntity<PersonResponse> createPerson(@Valid @RequestBody PersonRequest request) {
        Fisica personRequest = request.toDomain();
        var response = insertPersonInputPort.insert(personRequest);
        URI location = UriComponentsBuilder.fromPath("/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(location).body(new PersonResponse(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonResponse> findPerson(@PathVariable Long id) {
        var response = findPersonInputPort.find(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResult<PersonResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        var response = findPersonInputPort.findAll(page, size);
        return ResponseEntity.ok(PersonResponse.convertToPageResult(response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonUpdateRequest request) {
        Fisica person = updatePersonInputPort.update( request.updateDomain(), id);

        return ResponseEntity.ok(new PersonResponse(person));
    }
}