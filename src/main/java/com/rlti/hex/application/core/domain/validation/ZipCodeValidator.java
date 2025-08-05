package com.rlti.hex.application.core.domain.validation;

import com.rlti.hex.application.core.domain.messages.ErrorMessages;
import java.util.regex.Pattern;

/**
 * Validador de CEP (Código de Endereçamento Postal) brasileiro.
 * Centraliza as regras de validação de CEP para garantir consistência.
 * 
 * @author Sistema
 * @since 1.0
 */
public final class ZipCodeValidator {

    /**
     * Padrão regex para validação de CEP brasileiro.
     * Aceita formatos: 12345-678 ou 12345678
     */
    private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("^\\d{5}-?\\d{3}$");
    
    /**
     * Comprimento esperado do CEP sem formatação.
     */
    private static final int ZIP_CODE_LENGTH_WITHOUT_DASH = 8;
    
    /**
     * Comprimento esperado do CEP com formatação.
     */
    private static final int ZIP_CODE_LENGTH_WITH_DASH = 9;
    
    /**
     * Caractere separador do CEP.
     */
    private static final String ZIP_CODE_SEPARATOR = "-";

    private ZipCodeValidator() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Valida se o CEP fornecido está em um formato válido.
     * 
     * @param zipCode o CEP a ser validado
     * @return true se o CEP for válido, false caso contrário
     */
    public static boolean isValid(String zipCode) {
        if (zipCode == null || zipCode.isBlank()) {
            return false;
        }
        
        String cleanZipCode = zipCode.trim();
        return ZIP_CODE_PATTERN.matcher(cleanZipCode).matches();
    }

    /**
     * Normaliza o CEP removendo caracteres especiais e espaços.
     * 
     * @param zipCode o CEP a ser normalizado
     * @return o CEP normalizado (apenas dígitos)
     * @throws IllegalArgumentException se o CEP for inválido
     */
    public static String normalize(String zipCode) {
        if (!isValid(zipCode)) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ZipCode.INVALID_FORMAT, zipCode));
        }
        
        return zipCode.replace(ZIP_CODE_SEPARATOR, "").trim();
    }

    /**
     * Formata o CEP adicionando o hífen se necessário.
     * 
     * @param zipCode o CEP a ser formatado
     * @return o CEP formatado (12345-678)
     * @throws IllegalArgumentException se o CEP for inválido
     */
    public static String format(String zipCode) {
        String normalized = normalize(zipCode);
        
        if (normalized.length() != ZIP_CODE_LENGTH_WITHOUT_DASH) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ZipCode.INVALID_LENGTH, zipCode));
        }
        
        return normalized.substring(0, 5) + ZIP_CODE_SEPARATOR + normalized.substring(5);
    }

    /**
     * Verifica se o CEP já está formatado (contém hífen).
     * 
     * @param zipCode o CEP a ser verificado
     * @return true se o CEP estiver formatado, false caso contrário
     */
    public static boolean isFormatted(String zipCode) {
        return zipCode != null && zipCode.contains(ZIP_CODE_SEPARATOR) && 
               zipCode.length() == ZIP_CODE_LENGTH_WITH_DASH;
    }
}