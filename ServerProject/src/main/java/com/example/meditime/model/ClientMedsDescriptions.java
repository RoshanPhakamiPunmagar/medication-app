package com.example.meditime.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represent a client's medication details summary.
 * Includes a list of medication names, along with associated recommendations
 * and potential interaction warnings.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientMedsDescriptions {

    private List<String> medications;

    private String recommendations;

    private String interactions;

}
