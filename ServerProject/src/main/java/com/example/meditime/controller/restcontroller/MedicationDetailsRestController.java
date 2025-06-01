package com.example.meditime.controller.restcontroller;


import com.example.meditime.model.ClientMedsDescriptions;
import com.example.meditime.model.Medication;
import com.example.meditime.service.MedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Request;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/meds")
public class MedicationDetailsRestController {

    private final OpenAiChatClient chatClient;
    private final MedicationService medicationService;

    public MedicationDetailsRestController(
            OpenAiChatClient chatClient,
            MedicationService medicationService) {
        this.chatClient = chatClient;
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
    You are a clinical pharmacist analyzing these medications for interactions: %s

    Return EXCLUSIVELY in this JSON format:
    {
      "medications": ["Med1", "Med2"],
      "interactions": Here goes interactions,
      "recommendations": Here goes recommendation
    }

    Critical Safety Rules:
    1. Interaction Reporting:
       - If ANY known interaction or side effects exists (even theoretical): 
         "interactions": "[Drugs] may cause [effect] (e.g., "Ibuprofen + Warfarin may increase bleeding risk")
       - If no interaction or side effects found, just say 'No interaction found'
    2. Recommendation Rules:
       - Must be clinically actionable (e.g., "Monitor blood pressure" not just "Be careful")
       - Just provide any, recommendation
    3. Safety Measures:
       - Never suggest dosage changes
       - Always use conservative language ("may" instead of "will")
       - Flag severe interactions with "WARNING: " prefix
    4. Format Requirements:
       - Pure JSON only (no Markdown, no extra text)
       - Escape all special characters
       - Max 25 words per response field

    Format:
    {
      "medications": ["Loratadine", "Pantoprazole"],
      "interactions": "",
      "recommendations": ""
    }
    """.formatted(joinedMeds);

        // OpenAI-specific invocation
        ChatResponse response = chatClient.call(new Prompt(prompt));
        String aiResponse = response.getResult().getOutput().getContent();


        try {
            String jsonStr = aiResponse.replaceAll("(?s)^.*?(\\{.*\\}).*$", "$1");
            jsonStr = jsonStr.replaceAll("\\w+=\"[^\"]*\"", "");

            if (!jsonStr.contains("\"interactions\"")) {
                jsonStr = jsonStr.replaceFirst("\\}$", ", \"interactions\":\"No interactions found\" }");
            }
            if (!jsonStr.contains("\"recommendations\"")) {
                jsonStr = jsonStr.replaceFirst("\\}$", ", \"recommendations\":\"No recommendations\" }");
            }
            jsonStr = jsonStr.replaceAll("(?<!\")recommendations\":", "\"recommendations\":");

            return new ObjectMapper().readValue(jsonStr, ClientMedsDescriptions.class);
        } catch (Exception e) {
            return new ClientMedsDescriptions(
                    medsList,
                    "Error: Could not parse AI response",
                    "Please check the model output format"
            );
        }
    }

    @GetMapping("one/get/{medsId}")
    public String getMedicationDetails(@PathVariable Long medsId) {
        return medicationService.getMedicationById(medsId).getName();
    }
}