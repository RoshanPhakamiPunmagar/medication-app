package com.example.meditime.controller.restcontroller;


import com.example.meditime.model.ClientMedsDescriptions;
import com.example.meditime.model.Medication;
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

        var converter = new BeanOutputConverter<>(ClientMedsDescriptions.class);
        String format = converter.getFormat();


        var msg = ("""
        You are a clinical pharmacology expert specializing in drug interactions and medication safety. 
        Your task is to analyze possible combinations of medications and provide evidence-based guidance. It always must not be correct
        If no known interactions or usage guidelines exist for the given medications, strictly respond with "No result found" for both sections.
        
        {medsDetails}
        
        Please provide:
        - Interactions between these medications (if any).
        - Recommendations for safe usage.
        
        Use the following response format:
        {format}
        """);

        PromptTemplate promptTemplate = new PromptTemplate(
                msg,
                Map.of(
                        "medsDetails", joinedMeds,
                        "format", format
                )
        );


        Prompt prompt = promptTemplate.create();
        String aiResponse = chatClient.prompt(prompt).call().content();

        // Convert AI response to a structured Java object
        ClientMedsDescriptions medsDetails = converter.convert(aiResponse);

        return medsDetails;
    }
}
