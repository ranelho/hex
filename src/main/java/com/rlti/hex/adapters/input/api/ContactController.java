package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.ContactRequest;
import com.rlti.hex.adapters.input.api.response.ContactResponse;
import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.port.input.InsertContactToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateContactInputPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Contact", description = "Contact API")
@RequestMapping("/contact")
public class ContactController {

    private final UpdateContactInputPort updateContactInputPort;
    private final InsertContactToPersonInputPort insertContactToPersonInputPort;

    @PatchMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Long id, @Valid @RequestBody ContactRequest request) {
        Contact contact = updateContactInputPort.update(request.toDomain(), id);
        return ResponseEntity.ok(new ContactResponse(contact));
    }

    @PostMapping("/person/{idPerson}")
    public ResponseEntity<ContactResponse> insertContactToPerson(
            @PathVariable Long idPerson,
            @Valid @RequestBody ContactRequest request
    ) {
        Contact contact = insertContactToPersonInputPort.insert(request.toDomain(), idPerson);
        return ResponseEntity.ok(new ContactResponse(contact));
    }
}