# Implementação Apache Kafka - Arquitetura Hexagonal

Este documento descreve a implementação do Apache Kafka no projeto de arquitetura hexagonal para processar eventos após o cadastro de pessoas.

## 📋 Visão Geral

A implementação segue os princípios da arquitetura hexagonal, onde:
- **Domínio**: Contém os eventos de negócio
- **Ports**: Definem as interfaces para publicação de eventos
- **Adapters**: Implementam a integração com o Kafka
- **Use Cases**: Orquestram a publicação de eventos

## 🏗️ Arquitetura

```
┌─────────────────────────────────────────────────────────────┐
│                    HEXAGONAL ARCHITECTURE                   │
├─────────────────────────────────────────────────────────────┤
│  Domain Events                                              │
│  ├── PersonCreatedEvent                                     │
│                                                             │
│  Application Core                                           │
│  ├── InsertPersonUseCase (Publisher)                       │
│  └── EventPublisherOutputPort (Interface)                  │
│                                                             │
│  Adapters                                                   │
│  ├── KafkaEventPublisherAdapter (Producer)                 │
│  └── PersonCreatedEventConsumer (Consumer)                 │
└─────────────────────────────────────────────────────────────┘
```

## 🚀 Componentes Implementados

### 1. Evento de Domínio
- **PersonCreatedEvent**: Representa o evento de criação de pessoa
  - Contém: ID, nome, CPF, email, timestamp

### 2. Port de Saída
- **EventPublisherOutputPort**: Interface para publicação de eventos

### 3. Adapter Producer
- **KafkaEventPublisherAdapter**: Implementa a publicação no Kafka
  - Usa KafkaTemplate para envio assíncrono
  - Tratamento de erros e logs

### 4. Adapter Consumer
- **PersonCreatedEventConsumer**: Processa eventos recebidos
  - Acknowledgment manual para controle de processamento
  - Exemplos de processamento: email, analytics, notificações

### 5. Use Case Atualizado
- **InsertPersonUseCase**: Publica evento após cadastro bem-sucedido
  - Não falha a operação principal se evento não for publicado

## ⚙️ Configurações

### Docker Compose
Adicionados os seguintes serviços:
- **Zookeeper**: Coordenação do cluster Kafka
- **Kafka**: Broker de mensagens
- **Kafka UI**: Interface web para gerenciamento (porta 8081)

### Configurações por Ambiente

**application-dev.yml (Desenvolvimento):**
```yaml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS_DEV:localhost:9092}
    consumer:
      group-id: ${KAFKA_CONSUMER_GROUP_ID_DEV:hex-group-dev}
    producer:
      acks: all
      retries: 3

kafka:
  topics:
    person-created: ${KAFKA_TOPIC_PERSON_CREATED_DEV:person-created-dev}
```

**application-prod.yml (Produção):**
```yaml
spring:
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS_PROD:kafka-prod:9092}
    consumer:
      group-id: ${KAFKA_CONSUMER_GROUP_ID_PROD:hex-group-prod}
    producer:
      acks: all
      retries: 3
      properties:
        max.in.flight.requests.per.connection: 1
        batch.size: 16384
        linger.ms: 5

kafka:
  topics:
    person-created: ${KAFKA_TOPIC_PERSON_CREATED_PROD:person-created-prod}
```

## 🔧 Como Usar

### 1. Configurar Variáveis de Ambiente

**Para Desenvolvimento (application-dev.yml):**
```bash
# Opcionais - valores padrão já configurados
KAFKA_BOOTSTRAP_SERVERS_DEV=localhost:9092
KAFKA_CONSUMER_GROUP_ID_DEV=hex-group-dev
KAFKA_TOPIC_PERSON_CREATED_DEV=person-created-dev
```

**Para Produção (application-prod.yml):**
```bash
# Obrigatórias para produção
KAFKA_BOOTSTRAP_SERVERS_PROD=kafka-cluster.prod.com:9092
KAFKA_CONSUMER_GROUP_ID_PROD=hex-group-prod
KAFKA_TOPIC_PERSON_CREATED_PROD=person-created-prod
```

### 2. Subir a Infraestrutura
```bash
docker-compose -f docker-compose-kafka.yml up -d

```

### 3. Verificar Serviços
- **Aplicação**: http://localhost:8080/hex/api
- **Kafka UI**: http://localhost:8081
- **Swagger**: http://localhost:8080/hex/api/public/swagger

### 4. Testar o Fluxo
1. Cadastrar uma pessoa via API
2. Verificar logs da aplicação para evento publicado
3. Verificar logs do consumer para evento processado
4. Usar Kafka UI para monitorar tópicos e mensagens

## 📊 Monitoramento

### Logs
- **Producer**: Logs de publicação com offset
- **Consumer**: Logs de processamento com detalhes da mensagem
- **Erros**: Tratamento e logs de falhas

### Kafka UI
- Visualizar tópicos
- Monitorar mensagens
- Verificar consumers e lag

## 🔄 Fluxo de Processamento

1. **Cadastro de Pessoa**
   - API recebe requisição
   - Use case valida e salva pessoa
   - Evento PersonCreatedEvent é criado
   - Evento é publicado no tópico `person-created`

2. **Processamento do Evento**
   - Consumer recebe evento
   - Processa lógicas de negócio:
     - Envio de email de boas-vindas
     - Atualização de analytics
     - Notificação para outros sistemas
   - Confirma processamento (acknowledge)

## 🛡️ Tratamento de Erros

- **Producer**: Retry automático e logs de falha
- **Consumer**: Reprocessamento em caso de erro
- **Isolamento**: Falha na publicação não afeta cadastro principal

## 🎯 Benefícios da Implementação

1. **Desacoplamento**: Sistemas independentes
2. **Escalabilidade**: Processamento assíncrono
3. **Confiabilidade**: Garantia de entrega
4. **Observabilidade**: Logs e monitoramento
5. **Flexibilidade**: Fácil adição de novos consumers

## 📝 Próximos Passos

- [ ] Implementar Dead Letter Queue (DLQ)
- [ ] Adicionar métricas com Micrometer
- [ ] Implementar retry policy configurável
- [ ] Adicionar testes de integração
- [ ] Implementar outros eventos de domínio