package com.rlti.hex.application.core.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonCreatedEvent {
    private Long personId;
    private String name;
    private String cpf;
    private String email;
    private LocalDateTime createdAt;
    private String eventType = "PERSON_CREATED";
    
    public PersonCreatedEvent(Long personId, String name, String cpf, String email) {
        this.personId = personId;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
}