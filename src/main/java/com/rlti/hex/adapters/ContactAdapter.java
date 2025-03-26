package com.rlti.hex.adapters;

import com.rlti.hex.adapters.output.entity.ContactEntity;
import com.rlti.hex.adapters.output.repository.ContactJpaRepository;
import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.port.output.DeleteContactOutputPort;
import com.rlti.hex.application.port.output.FindContactOutputPort;
import com.rlti.hex.application.port.output.InsertContactToPersonOutputPort;
import com.rlti.hex.application.port.output.UpdateContactOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Monitored
public class ContactAdapter implements InsertContactToPersonOutputPort,
        FindContactOutputPort, DeleteContactOutputPort, UpdateContactOutputPort {

    private final ContactJpaRepository contactRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Contact insert(Contact contact) {
        var contactEntity = modelMapper.map(contact, ContactEntity.class);
        var savedContact = contactRepository.save(contactEntity);
        return modelMapper.map(savedContact, Contact.class);
    }

    @Override
    public Optional<Contact> find(Long id) {
        return contactRepository.findById(id)
                .map(contactEntity -> modelMapper.map(contactEntity, Contact.class));
    }

    @Override
    public List<Contact> findAllByPerson(Long id) {
        return contactRepository.findAllByFisica_Id(id)
                .stream()
                .map(contactEntity -> modelMapper.map(contactEntity, Contact.class))
                .toList();
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(modelMapper.map(contact, ContactEntity.class));
    }

    @Override
    public Contact update(Contact contact) {
        return insert(contact);
    }
}
