package com.rlti.hex.application.core.domain.messages;

/**
 * Centraliza as mensagens de erro da aplicação para garantir consistência
 * e facilitar a manutenção e internacionalização futura.
 * 
 * @author Sistema
 * @since 1.0
 */
public final class ErrorMessages {

    private ErrorMessages() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    /**
     * Mensagens relacionadas a validação de dados.
     */
    public static final class Validation {
        public static final String REQUIRED_FIELD = "Campo obrigatório";
        public static final String INVALID_FORMAT = "Formato inválido";
        public static final String INVALID_LENGTH = "Tamanho inválido";
        public static final String INVALID_VALUE = "Valor inválido";
        
        private Validation() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Mensagens relacionadas a telefone e contato.
     */
    public static final class Phone {
        public static final String REQUIRED = "Telefone é obrigatório";
        public static final String INVALID_DDD_OR_NUMBER = "Telefone ou DDD inválido para o tipo informado";
        
        private Phone() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Mensagens relacionadas a CEP e endereço.
     */
    public static final class ZipCode {
        public static final String INVALID_FORMAT = "Formato de CEP inválido: %s";
        public static final String INVALID_LENGTH = "Tamanho de CEP inválido: %s";
        public static final String NOT_FOUND = "CEP não encontrado: %s";
        
        private ZipCode() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Mensagens relacionadas a pessoa.
     */
    public static final class Person {
        public static final String ALREADY_EXISTS = "Pessoa já tem cadastro!";
        public static final String NOT_FOUND = "Pessoa não encontrada";
        public static final String INVALID_CPF = "CPF inválido";
        
        private Person() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Mensagens relacionadas a recursos não encontrados.
     */
    public static final class NotFound {
        public static final String RESOURCE_NOT_FOUND = "Recurso não encontrado";
        public static final String ENTITY_NOT_FOUND = "Entidade não encontrada com ID: %s";
        
        private NotFound() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Mensagens relacionadas a operações de sistema.
     */
    public static final class System {
        public static final String OPERATION_TIMEOUT = "Operação expirou";
        public static final String EXTERNAL_SERVICE_ERROR = "Erro no serviço externo";
        public static final String INTERNAL_ERROR = "Erro interno do sistema";
        
        private System() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }

    /**
     * Mensagens relacionadas a eventos e publicação.
     */
    public static final class Event {
        public static final String PUBLISH_FAILED = "Falha ao publicar evento para pessoa ID: %s";
        public static final String PUBLISH_SUCCESS = "Evento publicado com sucesso para pessoa ID: %s";
        
        private Event() {
            throw new UnsupportedOperationException("Utility class cannot be instantiated");
        }
    }
}