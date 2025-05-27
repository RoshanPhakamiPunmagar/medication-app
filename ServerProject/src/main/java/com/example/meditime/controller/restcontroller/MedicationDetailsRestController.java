package com.example.meditime.controller.restcontroller;

import com.example.meditime.model.ClientMedsDescriptions;
import com.example.meditime.service.MedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for providing detailed information and AI analysis of medications.
 */
@RestController
@RequestMapping("meds")
public class MedicationDetailsRestController {

    private final OpenAiChatClient chatClient;
    private final MedicationService medicationService;

    // Constructor to inject dependencies
    public MedicationDetailsRestController(
            OpenAiChatClient chatClient,
            MedicationService medicationService) {
        this.chatClient = chatClient;
        this.medicationService = medicationService;
    }

    /**
     * Generates AI-powered medication details based on a list of medication IDs.
     * Returns a JSON object including:
     *  - List of medication names
     *  - Any interactions among them
     *  - Recommendations
     *
     * @param medicationList List of medication IDs
     * @return ClientMedsDescriptions (medications, interactions, and recommendations)
     */
    @GetMapping("details")
    public ClientMedsDescriptions getMedicationDetails(@RequestParam List<Long> medicationList) {
        ArrayList<String> medsList = new ArrayList<>();

        // Retrieve medication names from IDs
        for (Long x : medicationList) {
            medsList.add(medicationService.getMedicationById(x).getName());
        }

        // Join medication names into a single string
        String joinedMeds = String.join(", ", medsList);

        // Prompt sent to OpenAI for analysis
        String prompt = """
        You are a medical expert. For these medications: %s
        Return STRICTLY in this JSON format (example shown):
        {
          "medications": ["Med1", "Med2"],
          "interactions": "Describe interactions or say 'No interactions found'",
          "recommendations": "Provide recommendations or say 'No recommendations'"
        }
        Important:
        - Only return valid JSON
        - Never add comments or markdown
        - All fields must be present
        - Escape all special characters
        """.formatted(joinedMeds);

        // Send prompt to OpenAI and get the response
        ChatResponse response = chatClient.call(new Prompt(prompt));
        String aiResponse = response.getResult().getOutput().getContent();

        try {
            // Attempt to clean and parse JSON string from AI response
            String jsonStr = aiResponse.replaceAll("(?s)^.*?(\\{.*\\}).*$", "$1"); // Extract JSON content
            jsonStr = jsonStr.replaceAll("\\w+=\"[^\"]*\"", ""); // Clean invalid format if any

            // Ensure required fields are present
            if (!jsonStr.contains("\"interactions\"")) {
                jsonStr = jsonStr.replaceFirst("\\}$", ", \"interactions\":\"No interactions found\" }");
            }
            if (!jsonStr.contains("\"recommendations\"")) {
                jsonStr = jsonStr.replaceFirst("\\}$", ", \"recommendations\":\"No recommendations\" }");
            }

            // Ensure JSON formatting is correct
            jsonStr = jsonStr.replaceAll("(?<!\")recommendations\":", "\"recommendations\":");

            // Deserialize into POJO
            return new ObjectMapper().readValue(jsonStr, ClientMedsDescriptions.class);

        } catch (Exception e) {
            // Fallback in case of failure parsing AI response
            return new ClientMedsDescriptions(
                    medsList,
                    "Error: Could not parse AI response",
                    "Please check the model output format"
            );
        }
    }

    /**
     * Retrieves the name of a single medication by its ID.
     * @param medsId Medication ID
     * @return Medication name
     */
    @GetMapping("one/get/{medsId}")
    public String getMedicationDetails(@PathVariable Long medsId) {
        return medicationService.getMedicationById(medsId).getName();
    }
}
