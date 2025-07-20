# API
## Tecnologias Utilizadas

- Java 21
- Spring Boot
- PostgreSQL
- JPA (Java Persistence API)
- Resilience4j (Circuit Breaker)
- ViaCEP API (Validação de CEP)

## Variáveis de Ambiente

O projeto utiliza as seguintes variáveis de ambiente:

### Banco de Dados
- `DB_HOST_DEV` - URL do banco de dados para ambiente de desenvolvimento
- `DB_USERNAME_DEV` - Usuário do banco de dados para ambiente de desenvolvimento
- `DB_PASSWORD_DEV` - Senha do banco de dados para ambiente de desenvolvimento
- `DB_HOST_PROD` - URL do banco de dados para ambiente de produção
- `DB_USERNAME_PROD` - Usuário do banco de dados para ambiente de produção
- `DB_PASSWORD_PROD` - Senha do banco de dados para ambiente de produção

### APIs Externas
- `VIACEP_BASE_URL_DEV` - URL base da API ViaCEP para ambiente de desenvolvimento (padrão: https://viacep.com.br)
- `VIACEP_BASE_URL_PROD` - URL base da API ViaCEP para ambiente de produção (padrão: https://viacep.com.br)

### Perfis
- `SPRING_PROFILES_ACTIVE` - Perfil ativo do Spring (padrão: dev)

## Estrutura do Projeto

```sh
src/main/java
└── com
└── rlti.hex
├── adapters/
│   ├── config/ 
│   ├── input/
│   │    └──controller/
│   │        ├── api/
│   │        │   ├── request/
│   │        │   └── response/
│   ├── output/
│   │   ├── entity/
│   │   └── repository/
├── application/
│   ├── core
│   │   ├── domain/   
│   │   ├── usecase/
│   ├── port        
│   │   ├── input/
│   │   └── output/
│   └── usecase/      
├── config/      
├─── handler/      
└── Application.java 

```

### Swagger

A documentação da API está disponível através do Swagger UI no seguinte endereço:

```
http://localhost:8080/hex/api/public/swagger-ui/index.html# 
```

### Circuit Breaker

O projeto utiliza o padrão Circuit Breaker através do Resilience4j para garantir a resiliência nas chamadas à API externa ViaCEP. As configurações do Circuit Breaker estão definidas no arquivo `application.yml`.
## Autor

Este projeto foi desenvolvido por [Ranelho Lacerda].