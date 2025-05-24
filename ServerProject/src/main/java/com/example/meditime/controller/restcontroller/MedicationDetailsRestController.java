package com.example.meditime.controller.restcontroller;


import com.example.meditime.model.ClientMedsDescriptions;
import com.example.meditime.model.Medication;
import com.example.meditime.service.MedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Request;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("meds")
public class MedicationDetailsRestController {

    private final ChatClient chatClient;
    private final MedicationService medicationService;
    public MedicationDetailsRestController(OllamaChatModel chatClient, MedicationService medicationService) {
        this.chatClient = ChatClient.builder(chatClient).build();
        this.medicationService = medicationService;
    }

    @GetMapping("details")
    public ClientMedsDescriptions getMedicationDetails(@RequestParam List<Long> medicationList) {
        ArrayList<String> medsList = new ArrayList<>();
        for (Long x : medicationList) {
            medsList.add(medicationService.getMedicationById(x).getName());
        }
        String joinedMeds = String.join(", ", medsList);

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
        """.formatted(joinedMeds);

        String aiResponse = chatClient.prompt(new Prompt(prompt)).call().content();

        System.out.println("RAW RESPONSE:\n" + aiResponse);

        try {
            // Extract JSON block only
            String jsonStr = aiResponse.replaceAll("(?s)^.*?(\\{.*\\}).*$", "$1");

            // Clean invalid tokens like com="..."
            jsonStr = jsonStr.replaceAll("\\w+=\"[^\"]*\"", "");

            // Ensure mandatory keys exist; if missing, add default values
            if (!jsonStr.contains("\"interactions\"")) {
                jsonStr = jsonStr.replaceFirst("\\}$", ", \"interactions\":\"No interactions found\" }");
            }
            if (!jsonStr.contains("\"recommendations\"")) {
                jsonStr = jsonStr.replaceFirst("\\}$", ", \"recommendations\":\"No recommendations\" }");
            }

            // Fix common missing quotes typo
            jsonStr = jsonStr.replaceAll("(?<!\")recommendations\":", "\"recommendations\":");

            return new ObjectMapper().readValue(jsonStr, ClientMedsDescriptions.class);
        } catch (Exception e) {
            return new ClientMedsDescriptions(
                    medsList,
                    "Error: Could not parse LLM response",
                    "Please check the model output format"
            );
        }
    }


    @GetMapping("one/get/{medsId}")
    public String getMedicationDetails(@PathVariable  Long medsId) {
       return medicationService.getMedicationById(medsId).getName();

    }



}
