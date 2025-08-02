package com.rlti.hex.application.port.output;

import com.rlti.hex.application.core.domain.event.PersonCreatedEvent;

public interface EventPublisherOutputPort {
    void publishPersonCreatedEvent(PersonCreatedEvent event);
}