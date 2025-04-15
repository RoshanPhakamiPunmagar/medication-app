package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medication", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "description"})
})
@Getter
@Setter
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medication_id;

    private String name;
    private String description;
    @Column(name = "side_effects")
    private String sideEffects;
    @Column(name = "interaction_info")
    private String interactionInfo;
}

