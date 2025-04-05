package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.request.DependentRequest;
import com.rlti.hex.adapters.input.api.response.ContactResponse;
import com.rlti.hex.adapters.input.api.response.DependentResponse;
import com.rlti.hex.application.port.input.DeleteDependentInputPort;
import com.rlti.hex.application.port.input.FindDependentInputPort;
import com.rlti.hex.application.port.input.InsertDependentToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateDependentInputPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Dependent", description = "Dependent API")
@RequestMapping("/dependent")
public class DependentController {

    private final InsertDependentToPersonInputPort insertDependentToPersonInputPort;
    private final FindDependentInputPort findDependentInputPort;
    private final UpdateDependentInputPort updateDependentInputPort;
    private final DeleteDependentInputPort deleteDependentInputPort;

    @PostMapping("/person/{idPerson}")
    public ResponseEntity<DependentResponse> insert(@PathVariable Long idPerson, @RequestBody DependentRequest dependentRequest) {
        var dependent = insertDependentToPersonInputPort.insert(idPerson, dependentRequest);
        URI location = URI.create(String.format("/dependent/%d", dependent.id()));
        return ResponseEntity.created(location).body(dependent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DependentResponse> find(@PathVariable Long id) {
        var dependent = findDependentInputPort.find(id);
        return ResponseEntity.ok(dependent);
    }

    //update
    @PatchMapping("/{id}")
    public ResponseEntity<DependentResponse> update(@PathVariable Long id, @RequestBody DependentRequest dependentRequest) {
        var dependent = updateDependentInputPort.update(id, dependentRequest);
        return ResponseEntity.ok(dependent);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteDependentInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }

    //list
    @GetMapping("/person/{idPerson}")
    public ResponseEntity<List<DependentResponse>> list(@PathVariable Long idPerson) {
        var dependent = findDependentInputPort.list(idPerson);
        return ResponseEntity.ok(dependent);
    }
}
