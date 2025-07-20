package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.Address;

/**
 * Porta de saída para validação e enriquecimento de endereços.
 * Responsável por se comunicar com APIs externas de CEP.
 */
public interface ValidateAddressOutputPort {

    /**
     * Valida e complementa as informações de endereço baseado no CEP.
     * 
     * @param zipCode o CEP a ser validado
     * @return um objeto Address com os dados complementados
     */
    Address validateAndCompleteAddress(String zipCode);

    /**
     * Verifica se um CEP é válido sem complementar os dados.
     * 
     * @param zipCode o CEP a ser validado
     * @return true se o CEP for válido, false caso contrário
     */
    boolean isValidZipCode(String zipCode);
}
