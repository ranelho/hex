package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.response.AddressResponse;
import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.port.input.InsertAddressToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateAddressInputPort;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Address", description = "Address API")
@RequestMapping("/address")
public class AddressController {

    private final UpdateAddressInputPort updateAddressInputPort;
    private final InsertAddressToPersonInputPort insertAddressToPersonInputPort;

    @PatchMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id, @Valid @RequestBody AddressRequest request) {
        Address updatedAddress = updateAddressInputPort.update(request.toDomain(), id);
        return ResponseEntity.ok(new AddressResponse(updatedAddress));
    }

    @PostMapping("/person/{idPerson}")
    public ResponseEntity<AddressResponse> insertAddressToPerson(
            @PathVariable Long idPerson,
            @Valid @RequestBody AddressRequest request
    ) {
        Address address = insertAddressToPersonInputPort.insert(request.toDomain(), idPerson);
        return ResponseEntity.ok(new AddressResponse(address));
    }
}