package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.PersonRequest;
import com.rlti.hex.adapters.input.api.request.PersonUpdateRequest;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.port.input.FindPersonInputPort;
import com.rlti.hex.application.port.input.InsertPersonInputPort;
import com.rlti.hex.application.port.input.UpdatePersonInputPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        var response = insertPersonInputPort.insert(request);
        URI location = UriComponentsBuilder.fromPath("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
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
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        var response = findPersonInputPort.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonUpdateRequest request) {
        var response = updatePersonInputPort.update(id, request);
        return ResponseEntity.ok(response);
    }
}
