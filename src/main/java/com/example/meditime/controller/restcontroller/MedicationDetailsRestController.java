package com.example.meditime.controller.restcontroller;


import com.example.meditime.model.ClientMedsDescriptions;
import com.example.meditime.model.Medication;
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

    public MedicationDetailsRestController(OllamaChatModel chatClient) {
        this.chatClient = ChatClient.builder(chatClient).build();
    }


    @GetMapping("/details")
    public ClientMedsDescriptions getMedicationDetails(@RequestParam List<String> medicationList) {
        String joinedMeds = String.join(", ", medicationList);

        // Simplified prompt - local LLMs respond better to clear examples
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

        // Debug raw response
        System.out.println("RAW RESPONSE:\n" + aiResponse);

        // Handle cases where LLM adds unwanted text
        try {
            // Extract first JSON block if response contains extra text
            String jsonStr = aiResponse.replaceAll("(?s)^.*?(\\{.*\\}).*$", "$1");
            return new ObjectMapper().readValue(jsonStr, ClientMedsDescriptions.class);
        } catch (Exception e) {

            return new ClientMedsDescriptions(
                    medicationList,
                    "Error: Could not parse LLM response",
                    "Please check the model output format"
            );
        }
    }
}
