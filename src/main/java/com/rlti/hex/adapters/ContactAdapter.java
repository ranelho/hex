package com.rlti.hex.adapters;

import com.rlti.hex.adapters.mapper.ContactMapper;
import com.rlti.hex.adapters.output.repository.ContactJpaRepository;
import com.rlti.hex.application.core.domain.Contact;
import com.rlti.hex.application.port.output.DeleteContactOutputPort;
import com.rlti.hex.application.port.output.FindContactOutputPort;
import com.rlti.hex.application.port.output.InsertContactToPersonOutputPort;
import com.rlti.hex.application.port.output.UpdateContactOutputPort;
import com.rlti.hex.config.aspect.Monitored;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Monitored
public class ContactAdapter implements InsertContactToPersonOutputPort,
        FindContactOutputPort, DeleteContactOutputPort, UpdateContactOutputPort {

    private final ContactJpaRepository contactRepository;
    private final ContactMapper contactMapper;

    @Transactional
    @Override
    public Contact insert(Contact contact) {
        var contactEntity = contactMapper.toEntity(contact);
        var savedContact = contactRepository.save(contactEntity);
        return contactMapper.toModel(savedContact);
    }

    @Override
    public Optional<Contact> find(Long id) {
        return contactRepository.findById(id)
                .map(contactMapper::toModel);
    }

    @Override
    public List<Contact> findAllByPerson(Long id) {
        return contactRepository.findAllByFisica_Id(id)
                .stream()
                .map(contactMapper::toModel)
                .toList();
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.delete(contactMapper.toEntity(contact));
    }

    @Override
    public Contact update(Contact contact) {
        return insert(contact);
    }
}