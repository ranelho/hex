package com.rlti.hex.adapters.input.kafka;

import com.rlti.hex.application.core.domain.event.PersonCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonCreatedEventConsumer {

    @KafkaListener(
        topics = "${kafka.topics.person-created:person-created}",
        groupId = "${spring.kafka.consumer.group-id:hex-group}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handlePersonCreatedEvent(
            @Payload PersonCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment
    ) {
        try {
            log.info("Received person created event from topic: {}, partition: {}, offset: {}, personId: {}", 
                topic, partition, offset, event.getPersonId());
            
            // Aqui você pode implementar a lógica de negócio para processar o evento
            // Por exemplo:
            // - Enviar email de boas-vindas
            // - Criar perfil em outros sistemas
            // - Atualizar cache
            // - Gerar relatórios
            
            processPersonCreatedEvent(event);
            
            // Confirma o processamento da mensagem
            acknowledgment.acknowledge();
            
            log.info("Successfully processed person created event for person ID: {}", event.getPersonId());
            
        } catch (Exception e) {
            log.error("Error processing person created event for person ID: {}", event.getPersonId(), e);
            // Em caso de erro, você pode decidir se quer fazer retry ou enviar para DLQ
            // Por enquanto, vamos apenas logar o erro e não fazer acknowledge
            throw e; // Isso fará com que o Kafka tente reprocessar a mensagem
        }
    }
    
    private void processPersonCreatedEvent(PersonCreatedEvent event) {
        // Simula processamento do evento
        log.info("Processing person created event:");
        log.info("  - Person ID: {}", event.getPersonId());
        log.info("  - Name: {}", event.getName());
        log.info("  - CPF: {}", event.getCpf());
        log.info("  - Email: {}", event.getEmail());
        log.info("  - Created At: {}", event.getCreatedAt());
        
        // Aqui você implementaria a lógica específica do seu domínio
        // Exemplos:
        sendWelcomeEmail(event);
        updateAnalytics(event);
        notifyOtherSystems(event);
    }
    
    private void sendWelcomeEmail(PersonCreatedEvent event) {
        // Simula envio de email de boas-vindas
        if (event.getEmail() != null && !event.getEmail().isEmpty()) {
            log.info("Sending welcome email to: {} for person: {}", event.getEmail(), event.getName());
            // Aqui você integraria com um serviço de email
        }
    }
    
    private void updateAnalytics(PersonCreatedEvent event) {
        // Simula atualização de analytics
        log.info("Updating analytics for new person registration: {}", event.getPersonId());
        // Aqui você integraria com um serviço de analytics
    }
    
    private void notifyOtherSystems(PersonCreatedEvent event) {
        // Simula notificação para outros sistemas
        log.info("Notifying other systems about new person: {}", event.getPersonId());
        // Aqui você integraria com outros sistemas/microserviços
    }
}