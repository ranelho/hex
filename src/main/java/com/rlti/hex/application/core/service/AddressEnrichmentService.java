package com.rlti.hex.application.core.service;

import com.rlti.hex.application.core.domain.Address;
import com.rlti.hex.application.core.usecase.config.UseCase;
import com.rlti.hex.application.port.input.AddressEnrichmentInputPort;
import com.rlti.hex.application.port.output.ValidateAddressOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Serviço utilitário para enriquecimento de dados de endereço.
 * Centraliza a lógica de complementação de endereços para ser reutilizada por diferentes casos de uso.
 */
@UseCase
@Monitored
public class AddressEnrichmentService implements AddressEnrichmentInputPort {

    private static final Logger logger = LoggerFactory.getLogger(AddressEnrichmentService.class);
    private final ValidateAddressOutputPort validateAddressOutputPort;

    public AddressEnrichmentService(ValidateAddressOutputPort validateAddressOutputPort) {
        this.validateAddressOutputPort = validateAddressOutputPort;
    }

    /**
     * Complementa os dados de todos os endereços da lista utilizando serviço externo.
     * 
     * @param addresses lista de endereços a serem complementados
     */
    public void complementAddressesData(List<Address> addresses) {
        if (addresses == null || addresses.isEmpty()) return;

        // Para cada endereço, tenta complementar os dados
        addresses.forEach(this::complementAddressData);
    }

    /**
     * Complementa os dados de um endereço específico usando o serviço de validação.
     * 
     * @param address o endereço a ser complementado
     */
    public void complementAddressData(Address address) {
        if (address == null) return;

        String zipCode = address.getZipCode();

        if (isNullOrEmpty(zipCode)) {
            return;
        }

        try {
            Address validatedAddress = validateAddressOutputPort.validateAndCompleteAddress(zipCode);
            mergeAddressData(address, validatedAddress);
            logger.info("Endereço complementado com sucesso via serviço externo para o CEP: {}", zipCode);
        } catch (Exception e) {
            // Em caso de erro, apenas loga e continua o fluxo
            logger.warn("Não foi possível complementar o endereço via serviço externo: {}", e.getMessage());
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
