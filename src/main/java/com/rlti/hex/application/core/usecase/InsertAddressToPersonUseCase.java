package com.rlti.hex.application.core.usecase;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.InsertAddressToPersonInputPort;
import com.rlti.hex.application.port.output.FindPersonOutputPort;
import com.rlti.hex.application.port.output.InsertAddressToPersonOutputPort;
import com.rlti.hex.application.port.output.ValidateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import com.rlti.hex.handler.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Supplier;

@UseCase
@Monitored
public class InsertAddressToPersonUseCase implements InsertAddressToPersonInputPort {

    private static final Logger logger = LoggerFactory.getLogger(InsertAddressToPersonUseCase.class);

    private final InsertAddressToPersonOutputPort insertAddressToPersonOutputPort;
    private final FindPersonOutputPort findPersonByIdUseCase;
    private final ValidateAddressOutputPort validateAddressOutputPort;

    public InsertAddressToPersonUseCase(
            InsertAddressToPersonOutputPort insertAddressToPersonOutputPort,
            FindPersonOutputPort findPersonByIdUseCase,
            ValidateAddressOutputPort validateAddressOutputPort
    ) {
        this.insertAddressToPersonOutputPort = insertAddressToPersonOutputPort;
        this.findPersonByIdUseCase = findPersonByIdUseCase;
        this.validateAddressOutputPort = validateAddressOutputPort;
    }

    @Override
    public Address insert(Address address, Long idPerson) {
        var person = findPersonByIdUseCase.findPerson(idPerson)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        // Complementa o endereço com dados do ViaCEP
        complementAddressData(address);

        address.setPerson(person);

        return insertAddressToPersonOutputPort.insert(address);
    }

    /**
     * Complementa os dados do endereço usando o serviço de CEP.
     * Se o serviço estiver indisponível, continua o fluxo com os dados fornecidos pelo usuário.
     * 
     * @param address o endereço a ser complementado
     */
    private void complementAddressData(Address address) {
        String zipCode = address.getZipCode();

        if (isNullOrEmpty(zipCode)) {
            return;
        }

        try {
            Address validatedAddress = validateAddressOutputPort.validateAndCompleteAddress(zipCode);
            mergeAddressData(address, validatedAddress);
            logger.info("Endereço complementado com sucesso via ViaCEP para o CEP: {}", zipCode);
        } catch (Exception e) {
            // Em caso de erro, apenas loga e continua o fluxo
            logger.warn("Não foi possível complementar o endereço via ViaCEP: {}", e.getMessage());
        }
    }

    /**
     * Mescla os dados de um endereço validado com o endereço original,
     * preenchendo apenas os campos vazios do endereço original.
     *
     * @param originalAddress o endereço original a ser complementado
     * @param validatedAddress o endereço validado com os dados a serem mesclados
     */
    private void mergeAddressData(Address originalAddress, Address validatedAddress) {
        updateFieldIfEmpty(originalAddress::getStreet, originalAddress::setStreet, validatedAddress.getStreet());
        updateFieldIfEmpty(originalAddress::getNeighborhood, originalAddress::setNeighborhood, validatedAddress.getNeighborhood());
        updateFieldIfEmpty(originalAddress::getCity, originalAddress::setCity, validatedAddress.getCity());
        updateFieldIfEmpty(originalAddress::getState, originalAddress::setState, validatedAddress.getState());
        updateFieldIfEmpty(originalAddress::getCountry, originalAddress::setCountry, validatedAddress.getCountry());
    }

    /**
     * Atualiza um campo se o valor original estiver vazio e o novo valor não estiver vazio.
     *
     * @param getter função para obter o valor original
     * @param setter função para definir o novo valor
     * @param newValue o novo valor a ser definido
     */
    private void updateFieldIfEmpty(Supplier<String> getter, Consumer<String> setter, String newValue) {
        if (isNullOrEmpty(getter.get()) && !isNullOrEmpty(newValue)) {
            setter.accept(newValue);
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}