package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.PersonRequest;
import com.rlti.hex.adapters.input.api.response.PersonResponse;
import com.rlti.hex.application.port.input.FindPersonInputPort;
import com.rlti.hex.application.port.input.InsertPersonInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {

    private final InsertPersonInputPort insertPersonInputPort;
    private final FindPersonInputPort findPersonInputPort;

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
}
