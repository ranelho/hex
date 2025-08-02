package com.rlti.hex.adapters.output.kafka;

import com.rlti.hex.application.core.domain.event.PersonCreatedEvent;
import com.rlti.hex.application.port.output.EventPublisherOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventPublisherAdapter implements EventPublisherOutputPort {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.person-created:person-created}")
    private String personCreatedTopic;

    @Override
    public void publishPersonCreatedEvent(PersonCreatedEvent event) {
        try {
            log.info("Publishing person created event for person ID: {}", event.getPersonId());
            
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(
                personCreatedTopic, 
                event.getPersonId().toString(), 
                event
            );
            
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Successfully published person created event for person ID: {} with offset: {}", 
                        event.getPersonId(), result.getRecordMetadata().offset());
                } else {
                    log.error("Failed to publish person created event for person ID: {}", 
                        event.getPersonId(), ex);
                }
            });
            
        } catch (Exception e) {
            log.error("Error publishing person created event for person ID: {}", event.getPersonId(), e);
            throw new RuntimeException("Failed to publish person created event", e);
        }
    }
}