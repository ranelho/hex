package com.rlti.hex.adapters.output.kafka;

import com.rlti.hex.application.core.domain.event.PersonCreatedEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KafkaEventPublisherAdapterTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Mock
    private SendResult<String, Object> sendResult;

    @InjectMocks
    private KafkaEventPublisherAdapter kafkaEventPublisherAdapter;

    @Test
    void shouldPublishPersonCreatedEventSuccessfully() {
        ReflectionTestUtils.setField(kafkaEventPublisherAdapter, "personCreatedTopic", "person-created");
        
        PersonCreatedEvent event = new PersonCreatedEvent(
            1L,
            "João Silva",
            "12345678901",
            "joao@email.com"
        );

        CompletableFuture<SendResult<String, Object>> future = CompletableFuture.completedFuture(sendResult);
        when(kafkaTemplate.send(anyString(), anyString(), any(PersonCreatedEvent.class)))
            .thenReturn(future);

        kafkaEventPublisherAdapter.publishPersonCreatedEvent(event);

        verify(kafkaTemplate).send("person-created", "1", event);
    }

    @Test
    void shouldHandleKafkaTemplateException() {
        ReflectionTestUtils.setField(kafkaEventPublisherAdapter, "personCreatedTopic", "person-created");
        
        PersonCreatedEvent event = new PersonCreatedEvent(
            1L,
            "João Silva",
            "12345678901",
            "joao@email.com"
        );

        when(kafkaTemplate.send(anyString(), anyString(), any(PersonCreatedEvent.class)))
            .thenThrow(new RuntimeException("Kafka error"));

        try {
            kafkaEventPublisherAdapter.publishPersonCreatedEvent(event);
        } catch (RuntimeException e) {
        }

        verify(kafkaTemplate).send("person-created", "1", event);
    }
}