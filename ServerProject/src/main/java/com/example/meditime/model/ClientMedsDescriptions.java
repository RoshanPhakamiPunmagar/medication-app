package com.example.meditime.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO class representing a client's medication details including
 * medication names, any recommendations, and potential interactions.
 *
 * Note: This class is not annotated as an entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientMedsDescriptions {

    /**
     * List of medication names associated with the client.
     */
    private List<String> medications;

    /**
     * Recommendations related to the client's medications.
     */
    private String recommendations;

    /**
     * Descriptions of potential medication interactions for the client.
     */
    private String interactions;

}
