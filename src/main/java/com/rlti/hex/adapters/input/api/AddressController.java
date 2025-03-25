package com.rlti.hex.adapters.input.api;

import com.rlti.hex.adapters.input.api.request.AddressRequest;
import com.rlti.hex.adapters.input.api.response.AddressResponse;
import com.rlti.hex.application.port.input.DeleteAddressInputPort;
import com.rlti.hex.application.port.input.FindAddressInputPort;
import com.rlti.hex.application.port.input.InsertAddressToPersonInputPort;
import com.rlti.hex.application.port.input.UpdateAddressInputPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {

    private final InsertAddressToPersonInputPort insertAddressToPersonInputPort;
    private final FindAddressInputPort findAddressInputPort;
    private final UpdateAddressInputPort updateAddressInputPort;
    private final DeleteAddressInputPort deleteAddressInputPort;

    @PostMapping("/person/{idPerson}")
    public ResponseEntity<AddressResponse> insert(@PathVariable Long idPerson, @Valid @RequestBody AddressRequest request) {
        var address = insertAddressToPersonInputPort.insert(idPerson, request);
        URI location = URI.create(String.format("/address/%d", address.id()));
        return ResponseEntity.created(location).body(address);
    }

    @GetMapping("/{idAddress}")
    public ResponseEntity<AddressResponse> findById(@PathVariable Long idAddress) {
        var address = findAddressInputPort.findById(idAddress);
        return ResponseEntity.ok(address);
    }

    @PatchMapping("/{idAddress}")
    public ResponseEntity<AddressResponse> update(@PathVariable Long idAddress, @Valid @RequestBody AddressRequest request) {
        var address = updateAddressInputPort.updateAddressInputPort(idAddress, request);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{idAddress}")
    public ResponseEntity<Void> delete(@PathVariable Long idAddress) {
        deleteAddressInputPort.delete(idAddress);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/person/{idPerson}")
    public ResponseEntity<List<AddressResponse>> list(@PathVariable Long idPerson) {
        var address = findAddressInputPort.findAll(idPerson);
        return ResponseEntity.ok(address);
    }
}
