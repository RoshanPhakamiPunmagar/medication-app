package com.example.meditime.controller.restcontroller;

import com.example.meditime.dto.AdherenceLogDTO;
import com.example.meditime.dto.MedicationLogDTO;
import com.example.meditime.model.*;
import com.example.meditime.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.openai.OpenAiChatClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST controller that handles medication adherence logs and AI-based analysis.
 */
@RestController
@RequestMapping("/logs")
public class AdherenceLogRestController {

    private final AdherenceLogService adherenceLogService;
    private final ClientMedicationService clientMedicationService;
    private final ClientService clientService;
    private final MedicationService medicationService;
    private final OpenAiChatClient chatClient; // OpenAI Chat Client used for generating AI-based analysis
    private final MedicationLogService medicationLogService;

    // Constructor for injecting all required services and the OpenAI chat client
    public AdherenceLogRestController(
            OpenAiChatClient chatClient,
            AdherenceLogService adherenceLogService,
            ClientMedicationService clientMedicationService,
            ClientService clientService,
            MedicationService medicationService,
            MedicationLogService medicationLogService) {

        this.adherenceLogService = adherenceLogService;
        this.clientMedicationService = clientMedicationService;
        this.clientService = clientService;
        this.medicationService = medicationService;
        this.medicationLogService = medicationLogService;
        this.chatClient = chatClient;
    }

    /**
     * Retrieves all adherence logs associated with a specific client ID.
     * @param id Client ID
     * @return List of AdherenceLogDTOs
     */
    @GetMapping("get/{id}")
    public List<AdherenceLogDTO> getAdherenceLog(@PathVariable Long id) {
        List<AdherenceLogDTO> dtoList = new ArrayList<>();
        List<ClientMedication> clientsLists = clientMedicationService.getClientMedicationByClientId(id);

        for (ClientMedication clientMedication : clientsLists) {
            if (clientMedication != null) {
                AdherenceLog log = adherenceLogService.getLogsByClientMedicationId(clientMedication.getClientMedicationId());
                if (log != null) {
                    dtoList.add(AdherenceLogDTO.fromEntity(log));
                }
            }
        }

        return dtoList;
    }

    /**
     * Saves a medication log entry submitted by the client.
     * @param medicationLog DTO containing log details
     */
    @PostMapping("post/log")
    public void postMedicationLog(@RequestBody MedicationLogDTO medicationLog) {
        medicationLogService.save(medicationLog);
    }

    /**
     * Generates an AI-powered analysis report of the client's last 5 medication logs.
     * Includes adherence patterns, risks, and improvement suggestions.
     * @param patientId ID of the patient
     * @return JSON-formatted analysis or error message
     */
    @GetMapping("get/ai/{patientId}")
    public ResponseEntity<?> getAiReportMedicalLog(@PathVariable Long patientId) {
        try {
            // Retrieve the last 5 logs for the given client
            List<MedicationLog> logs = medicationLogService.findMedicalLogByClientMedication(patientId);
            List<Medication> meds = new ArrayList<>();
            List<Long> medsId = clientMedicationService.getClientMedicationByUserId(patientId);

            for (Long id : medsId) {
                meds.add(medicationService.getMedicationById(id));
            }

            if (logs.isEmpty()) {
                return ResponseEntity.ok("No medication logs found for this medication");
            }

            // Get last 5 logs and medications
            List<MedicationLog> lastFiveLogs = logs.stream()
                    .skip(Math.max(0, logs.size() - 5))
                    .collect(Collectors.toList());

            List<Medication> lastFiveMeds = meds.stream()
                    .skip(Math.max(0, logs.size() - 5))
                    .collect(Collectors.toList());

            // Prepare data for AI prompt
            List<Map<String, String>> logData = lastFiveLogs.stream()
                    .map(log -> Map.of(
                            "date", log.getScheduledTime() != null ? log.getScheduledTime().toString() : "N/A",
                            "status", log.getStatus().name(),
                            "time", log.getActualTime() != null ? log.getActualTime().toString() : "N/A",
                            "notes", log.getNotes() != null ? log.getNotes() : ""
                    ))
                    .collect(Collectors.toList());

            List<Map<String, String>> medsData = meds.stream()
                    .map(med -> Map.of(
                            "name", med.getName() != null ? med.getName() : "N/A"
                    ))
                    .collect(Collectors.toList());

            // Compose the prompt for OpenAI
            String prompt = """
                You are a medical expert analyzing medication adherence patterns.
                Here are the last 5 medication logs in JSON format:
                
                %s
                Here are the last 5 medications in JSON format:
                %s
                Please analyze this data and provide:
                1. Adherence rate percentage
                2. Pattern recognition (e.g., consistently late in mornings)
                3. Risk factors (e.g., frequent skipped doses)
                4. Recommendations for improvement. Just add medical improvements.

                Respond in this JSON format:
                {
                    "patterns": "Pattern description",
                    "risks": "Risk factors",
                    "recommendations": "Suggestions"
                }
                """.formatted(
                    new ObjectMapper().writeValueAsString(logData),
                    new ObjectMapper().writeValueAsString(medsData));

            // Send prompt to OpenAI and parse the response
            ChatResponse response = chatClient.call(new Prompt(prompt));
            String aiResponse = response.getResult().getOutput().getContent();

            // Convert AI response JSON to Java object
            AiAnalysisResponse analysisResponse = parseAiResponse(aiResponse);

            return ResponseEntity.ok(analysisResponse);

        } catch (Exception e) {
            // Return error message on failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not generate report", "details", e.getMessage()));
        }
    }

    /**
     * Helper method to parse AI's raw JSON response string into an AiAnalysisResponse object.
     * @param aiResponse Raw JSON string (possibly wrapped with ```json)
     * @return Parsed AiAnalysisResponse
     * @throws JsonProcessingException If parsing fails
     */
    private AiAnalysisResponse parseAiResponse(String aiResponse) throws JsonProcessingException {
        // Remove code block markers from AI's response
        String cleanJson = aiResponse.replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();
        return new ObjectMapper().readValue(cleanJson, AiAnalysisResponse.class);
    }
}
