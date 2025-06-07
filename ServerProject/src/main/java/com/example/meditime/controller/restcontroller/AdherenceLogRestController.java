package com.example.meditime.controller.restcontroller;

import com.example.meditime.dto.AdherenceLogDTO;
import com.example.meditime.dto.AiReport;
import com.example.meditime.dto.MedicationLogDTO;
import com.example.meditime.model.*;
import com.example.meditime.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.openai.OpenAiChatClient;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AdherenceLogRestController handles mobile-facing REST endpoints related to medication adherence logging.
 *
 * Responsibilities:
 * - Retrieve adherence logs for a specific patient.
 * - Save new medication log entries.
 * - Generate AI-based reports analyzing adherence behavior using OpenAI.
 *
 * Endpoints:
 * - GET  /mobile/logs/get/{id}      : Retrieves adherence logs for a given patient ID.
 * - POST /mobile/logs/post/log      : Saves a new medication log submitted by the mobile client.
 * - GET  /mobile/logs/get/ai/{id}   : Generates an AI report analyzing adherence patterns, risks, and recommendations.
 *
 * Integrations:
 * - OpenAiChatClient: Communicates with an OpenAI-powered service for natural language processing.
 * - AdherenceLogService, MedicationLogService, ClientMedicationService, MedicationService: Used to access and manage patient data and logs.
 *
 * Notes:
 * - The AI analysis endpoint prepares a prompt using recent logs and prescribed medications,
 *   then parses the AI's JSON-formatted medical insights into a structured response.
 */

@RestController
@RequestMapping("mobile/logs")
public class AdherenceLogRestController {

    private final AdherenceLogService adherenceLogService;
    private final ClientMedicationService clientMedicationService;
    private final MedicationService medicationService;
    private final OpenAiChatClient  chatClient; // Now using OpenAI's ChatClient
    private final MedicationLogService medicationLogService;

    public AdherenceLogRestController(
            OpenAiChatClient chatClient, // Injected OpenAI ChatClient
            AdherenceLogService adherenceLogService,
            ClientMedicationService clientMedicationService,
            ClientService clientService,
            MedicationService medicationService,
            MedicationLogService medicationLogService) {

        this.adherenceLogService = adherenceLogService;
        this.clientMedicationService = clientMedicationService;
        this.medicationService = medicationService;
        this.medicationLogService = medicationLogService;
        this.chatClient = chatClient; // No need for builder with OpenAI
    }

    // Get adherence log by ID
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


    @PostMapping("post/log")
    public void postMedicationLog(@RequestBody MedicationLogDTO medicationLog) {
        medicationLogService.save(medicationLog);

    }



    @GetMapping("get/ai/{patientId}")
    public ResponseEntity<?> getAiReportMedicalLog(@PathVariable Long patientId) {
        try {
            //Validate input
            if (patientId == null || patientId <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid patient ID"));
            }

            // Get medication logs
            List<MedicationLog> allLogs = getPatientMedicationLogs(patientId);
            if (allLogs.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "No medication logs found for this patient"));
            }

            //Get medications
            List<Medication> medications = getPatientMedications(patientId);

            //Prepare data for AI analysis
            List<Map<String, String>> allLogData = prepareAllLogData(allLogs);
            List<Map<String, String>> allMedData = prepareAllMedicationData(medications);

            //Generate AI prompt
            String prompt = buildAnalysisPrompt(allLogData, allMedData);

            // Get AI response
            AiAnalysisResponse analysisResponse = getAiAnalysis(prompt);

            return ResponseEntity.ok(analysisResponse);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Could not generate report",
                            "details", e.getMessage()));
        }
    }

    // Helper methods
    private List<MedicationLog> getPatientMedicationLogs(Long patientId) {
        return clientMedicationService.getClientMedicationByClientId(patientId).stream()
                .filter(Objects::nonNull)
                .map(clientMed -> medicationLogService.findMedicalLogByClientMedication(
                        clientMed.getClientMedicationId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private List<Medication> getPatientMedications(Long patientId) {
        return clientMedicationService.getClientMedicationByUserId(patientId).stream()
                .map(medicationService::getMedicationById)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> prepareAllLogData(List<MedicationLog> logs) {
        return logs.stream()
                .sorted(Comparator.comparing(MedicationLog::getScheduledTime).reversed())
                .map(log -> Map.of(
                        "date", log.getScheduledTime() != null ? log.getScheduledTime().toString() : "N/A",
                        "status", log.getStatus().name(),
                        "time", log.getActualTime() != null ? log.getActualTime().toString() : "N/A",
                        "notes", StringUtils.defaultString(log.getNotes())
                ))
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> prepareAllMedicationData(List<Medication> medications) {
        return medications.stream()
                .map(med -> Map.of(
                        "name", StringUtils.defaultString(med.getName(), "N/A")
                ))
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> prepareLogData(List<MedicationLog> logs) {
        return logs.stream()
                .sorted(Comparator.comparing(MedicationLog::getScheduledTime).reversed())
                .limit(5)
                .map(log -> Map.of(
                        "date", log.getScheduledTime() != null ? log.getScheduledTime().toString() : "N/A",
                        "status", log.getStatus().name(),
                        "time", log.getActualTime() != null ? log.getActualTime().toString() : "N/A",
                        "notes", StringUtils.defaultString(log.getNotes())
                ))
                .collect(Collectors.toList());
    }

    private List<Map<String, String>> prepareMedicationData(List<Medication> medications) {
        return medications.stream()
                .map(med -> Map.of(
                        "name", StringUtils.defaultString(med.getName(), "N/A")
                ))
                .collect(Collectors.toList());
    }

    private String buildAnalysisPrompt(List<Map<String, String>> logData,
                                       List<Map<String, String>> medData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return """
        You are a medical expert analyzing medication adherence patterns.
        Here are the last 5 medication logs in JSON format:
        
        %s
        
        Here are the patient's medications in JSON format:
        %s
        
        Please analyze this data and provide:
        1. Adherence patterns (e.g., consistently late in mornings)
        2. Risk factors (e.g., frequent skipped doses)
        3. Clinical recommendations for improvement
        
                Respond in EXACTLY this JSON format:
                          {
                              "patterns": "paragraph describing patterns",
                              "risks": "paragraph describing risks",
                              "recommendations": "paragraph with recommendations"
                          }
        
        """.formatted(
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logData),
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(medData)
        );
    }




    private AiAnalysisResponse getAiAnalysis(String prompt) throws JsonProcessingException {
        ChatResponse response = chatClient.call(new Prompt(prompt));
        String aiResponse = response.getResult().getOutput().getContent();
        return parseAiResponse(aiResponse);
    }


    private AiAnalysisResponse parseAiResponse(String aiResponse) throws JsonProcessingException {
        // Clean the response
        String cleanJson = aiResponse.replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();
        return new ObjectMapper().readValue(cleanJson, AiAnalysisResponse.class);
    }

    @GetMapping("get/ai/report/{patientId}")
    public  ResponseEntity<?> getAiReport(@PathVariable Long patientId) {
        try {
            //Validate input
            if (patientId == null || patientId <= 0) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid patient ID"));
            }

            // Get medication logs
            List<MedicationLog> allLogs = getPatientMedicationLogs(patientId);
            if (allLogs.isEmpty()) {
                return ResponseEntity.ok(Map.of("message", "No medication logs found for this patient"));
            }

            //Get medications
            List<Medication> medications = getPatientMedications(patientId);

            //Prepare data for AI analysis
            List<Map<String, String>> logData = prepareLogData(allLogs);
            List<Map<String, String>> medData = prepareMedicationData(medications);

            //Generate AI prompt
            String prompt = buildReportPrompt(logData, medData);

            // Get AI response
            AiReport analysisResponse = getAiReport(prompt);

            return ResponseEntity.ok(analysisResponse);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "Could not generate report",
                            "details", e.getMessage()));
        }
    }

    private String buildReportPrompt(List<Map<String, String>> logData,
                                     List<Map<String, String>> medData) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return """
        You are a medical expert analyzing medication logs and patterns.
        Here are the medication logs in JSON format:
        
        %s
        
        Here are the patient's medications in JSON format:
        %s
        
        Please analyze this data and provide the report:
        1. patterns in which medication is taken
        2. alerts in patterns and recommendation to apply
      
        
                Respond in EXACTLY this JSON format:
                            {
                                          "patterns": "in bullet points patterns in which medication is taken",
                                          "alerts": "in bullet points, alerts in patterns and recommendation to apply"
                                      }
        
        """.formatted(
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logData),
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(medData)
        );
    }
    private AiReport getAiReport(String prompt) throws JsonProcessingException {
        ChatResponse response = chatClient.call(new Prompt(prompt));
        String aiResponse = response.getResult().getOutput().getContent();
        return parseAiReportResponse(aiResponse);
    }


    private AiReport parseAiReportResponse(String aiResponse) throws JsonProcessingException {
        // Clean the response
        String cleanJson = aiResponse.replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();
        return new ObjectMapper().readValue(cleanJson, AiReport.class);
    }
}
