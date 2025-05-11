package com.example.meditime.service;

import com.example.meditime.model.Medication;
import com.example.meditime.repository.MedicationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class OpenFdaMedicationService {

    @Autowired
    private MedicationRepository repository;

    public void fetchOpenFdaMedications() {
        RestTemplate restTemplate = new RestTemplate();  // Used to make HTTP requests
        ObjectMapper mapper = new ObjectMapper();        // Used to parse JSON response

        int skip = 0;                 // Used for pagination
        int limit = 100;             // Number of records per API call
        int maxRecords = 42;        // Maximum number of records to fetch in total
        boolean moreData = true;     // Controls the while loop

        while (moreData && skip < maxRecords) {
            // Construct the URL with pagination
            String url = "https://api.fda.gov/drug/label.json?limit=" + limit + "&skip=" + skip;

            try {
                System.out.println("Fetching medications... skip=" + skip);

                // Fetch response from OpenFDA API as a String
                String response = restTemplate.getForObject(url, String.class);

                // Parse JSON response
                JsonNode root = mapper.readTree(response);
                JsonNode results = root.path("results");  // Extract the "results" array

                // Check if the results array is valid and contains data
                if (results.isArray() && results.size() > 0) {

                    // Loop through each medication entry
                    for (JsonNode med : results) {
                        // Extract brand name (name of the medication)
                        JsonNode brandNode = med.path("openfda").path("brand_name");

                        // Skip if brand name is not available
                        if (!brandNode.isArray() || brandNode.size() == 0) continue;

                        // Extract the actual name (first item in brand_name array)
                        String name = brandNode.get(0).asText().trim();

                        // Optional: Avoid saving duplicates based on name
                        Optional<Medication> existing = repository.findByName(name);
                        if (existing.isPresent()) {
                            continue; // Skip this medication if it already exists
                        }

                        // Create Medication object and set the name
                        Medication medication = new Medication();
                        medication.setName(name);

                        // Save the medication name to the database
                        repository.save(medication);
                    }

                    // Move to the next set of results for pagination
                    skip += limit;

                } else {
                    // If no results are found, exit the loop
                    moreData = false;
                }

            } catch (Exception e) {
                // If there's an error (e.g., network or parsing issue), log it and stop the loop
                System.err.println("Error fetching medications: " + e.getMessage());
                moreData = false;
            }
        }

        System.out.println("Medication import completed.");
    }
}

