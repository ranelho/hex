package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.DependentRequest;
import com.rlti.hex.adapters.input.api.response.DependentResponse;
import com.rlti.hex.application.core.domain.Dependent;
import com.rlti.hex.application.port.input.InsertDependentToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateDependentInputPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dependent", description = "Dependent API")
@RequestMapping("/dependent")
public class DependentController {

    private final UpdateDependentInputPort updateDependentInputPort;
    private final InsertDependentToPersonInputPort insertDependentToPersonInputPort;

    @PatchMapping("/{id}")
    public ResponseEntity<DependentResponse> updateDependent(@PathVariable Long id, @Valid @RequestBody DependentRequest request) {
        Dependent dependent = updateDependentInputPort.update(
            id,
            request.name(),
            request.cpf(),
            request.birthDate(),
            request.dependentType()
        );
        return ResponseEntity.ok(new DependentResponse(dependent));
    }

    @PostMapping("/person/{idPerson}")
    public ResponseEntity<DependentResponse> insertDependentToPerson(
            @PathVariable Long idPerson,
            @Valid @RequestBody DependentRequest request
    ) {
        Dependent dependent = insertDependentToPersonInputPort.insert(request.toDomain(), idPerson);
        return ResponseEntity.ok(new DependentResponse(dependent));
    }
}