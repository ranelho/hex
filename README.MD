# API
## Tecnologias Utilizadas

- Java 21
- Spring Boot
- PostgreSQL
- JPA (Java Persistence API)

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
```
http://localhost:8080/hex/api/public/swagger-ui/index.html# 
```
## Autor

Este projeto foi desenvolvido por [Ranelho Lacerda].