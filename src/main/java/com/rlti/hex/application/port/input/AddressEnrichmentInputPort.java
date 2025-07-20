package com.rlti.hex.application.port.input;

import com.rlti.hex.application.core.domain.Address;

import java.util.List;

/**
 * Porta de entrada para o serviço de enriquecimento de dados de endereço.
 */
public interface AddressEnrichmentInputPort {

    /**
     * Complementa os dados de todos os endereços da lista utilizando serviço externo.
     * 
     * @param addresses lista de endereços a serem complementados
     */
    void complementAddressesData(List<Address> addresses);

    /**
     * Complementa os dados de um endereço específico usando o serviço de validação.
     * 
     * @param address o endereço a ser complementado
     */
    void complementAddressData(Address address);
}
