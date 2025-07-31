package com.rlti.hex.adapters.output.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
@EntityListeners(PersonEntity.class)
@Table(name = "person")
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq_generator")
    @SequenceGenerator(name = "pessoa_seq_generator", sequenceName = "person_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<AddressEntity> addresses;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDate createdAt;

    public PersonEntity() {
    }
}
