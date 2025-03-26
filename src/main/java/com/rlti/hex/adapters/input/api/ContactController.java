package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.response.ContactResponse;
import com.rlti.hex.application.port.input.DeleteContactInputPort;
import com.rlti.hex.application.port.input.FindContactInputPort;
import com.rlti.hex.application.port.input.InsertContactToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateContactInputPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Contact", description = "Contact API")
@RequestMapping("/contact")
public class ContactController {

    private final InsertContactToPersonInputPort insertContactToPersonInputPort;
    private final FindContactInputPort findContactInputPort;
    private final UpdateContactInputPort updateContactInputPort;
    private final DeleteContactInputPort deleteContactInputPort;

    @PostMapping("/person/{idPerson}")
    public ResponseEntity<ContactResponse> insert(@PathVariable Long idPerson, @Valid @RequestBody ContactRequest request) {
        var contact = insertContactToPersonInputPort.insert(idPerson, request);
        URI location = URI.create(String.format("/contact/%d", contact.id()));
        return ResponseEntity.created(location).body(contact);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> find(@PathVariable Long id) {
        var contact = findContactInputPort.find(id);
        return ResponseEntity.ok(contact);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ContactResponse> update(@PathVariable Long id, @Valid @RequestBody ContactRequest request) {
        var contact = updateContactInputPort.update(id, request);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deleteContactInputPort.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/person/{idPerson}")
    public ResponseEntity<List<ContactResponse>> findAllByPerson(@PathVariable Long idPerson) {
        var contacts = findContactInputPort.findAllByPerson(idPerson);
        return ResponseEntity.ok(contacts);
    }
}
