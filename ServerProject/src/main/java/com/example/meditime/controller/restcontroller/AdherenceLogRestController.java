package com.example.meditime.controller.restcontroller;

import com.example.meditime.dto.AdherenceLogDTO;
import com.example.meditime.dto.MedicationLogDTO;
import com.example.meditime.model.*;
import com.example.meditime.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/logs")
public class AdherenceLogRestController {

    private final AdherenceLogService adherenceLogService;
   // private final AdherenceLogDTO adherenceLogMapper;
   private final ClientMedicationService clientMedicationService;

    private final ClientService clientService;

    private final MedicationService medicationService;

    private final ChatClient chatClient;

    private final MedicationLogService medicationLogService;
    public AdherenceLogRestController(OllamaChatModel chatClient, AdherenceLogService adherenceLogService, ClientMedicationService clientMedicationService, ClientService clientService, MedicationService medicationService, MedicationLogService medicationLogService){
        this.adherenceLogService = adherenceLogService;
        this.clientMedicationService = clientMedicationService;
        this.clientService = clientService;
        this.medicationService = medicationService;
        this.medicationLogService = medicationLogService;
        this.chatClient = ChatClient.builder(chatClient).build();
    }
    // Create new adherence log
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public AdherenceLogDTO createAdherenceLog(@Valid @RequestBody CreateAdherenceLogDto createDto) {
//        return adherenceLogMapper.toDto(
//                adherenceLogService.createAdherenceLog(createDto)
//        );
//    }

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
        System.out.println("Done");
        System.out.println(medicationLog.getLogId());
        System.out.println(medicationLog.getClientMedicationId());
        System.out.println(medicationLog.getActualTime());

        System.out.println(medicationLog.getScheduledTime());
        System.out.println(medicationLog.getNotes());

        medicationLogService.save(medicationLog);

    }


    @GetMapping("get/ai/{patientId}")
    public ResponseEntity<?> getAiReportMedicalLog(@PathVariable Long patientId) {
        try {

            List<MedicationLog> logs = medicationLogService.findMedicalLogByClientMedication(patientId);
            List<Medication> meds = new ArrayList<>();
            List<Long> medsId = clientMedicationService.getClientMedicationByUserId(patientId);
            for(Long log : medsId){
                System.out.println(log + "here");
            }
            for(Long i : medsId){
                meds.add(medicationService.getMedicationById(i));
            }

            for(Medication log : meds){
                System.out.println(log.getName() + "nane");
            }
            if (logs.isEmpty()) {
                return ResponseEntity.ok("No medication logs found for this medication");
            }


            List<MedicationLog> lastFiveLogs = logs.stream()
                    .skip(Math.max(0, logs.size() - 5))
                    .collect(Collectors.toList());

            for(MedicationLog log : lastFiveLogs){
                System.out.println(log.getStatus());
            }
            List<Medication> lastFiveMeds = meds.stream()
                    .skip(Math.max(0, logs.size() - 5))
                    .collect(Collectors.toList());

            for(Medication log : lastFiveMeds){
                System.out.println(log.getName());
            }

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

            // 4. Create detailed prompt
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
        """.formatted(new ObjectMapper().writeValueAsString(logData),
                    new ObjectMapper().writeValueAsString(medsData));

            // 5. Get AI response
            String aiResponse = chatClient.prompt(new Prompt(prompt)).call().content();


            // 6. Parse and validate response
            AiAnalysisResponse response = parseAiResponse(aiResponse);
            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Could not generate report", "details", e.getMessage()));
        }
    }

    private AiAnalysisResponse parseAiResponse(String aiResponse) throws JsonProcessingException {

        String cleanJson = aiResponse.replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();

        return new ObjectMapper().readValue(cleanJson, AiAnalysisResponse.class);
    }





//
//    // Get all adherence logs for a user
//    @GetMapping("/user/{userId}")
//    public List<AdherenceLogDto> getAdherenceLogsByUser(@PathVariable Long userId) {
//        return adherenceLogMapper.toDtoList(
//                adherenceLogService.getAdherenceLogsByUser(userId)
//        );
//    }
//
//    // Get adherence logs for a specific medication
//    @GetMapping("/medication/{medicationId}")
//    public List<AdherenceLogDto> getAdherenceLogsByMedication(
//            @PathVariable Long medicationId,
//            @RequestParam(required = false) LocalDate date) {
//
//        return adherenceLogMapper.toDtoList(
//                date != null
//                        ? adherenceLogService.getAdherenceLogsByMedicationAndDate(medicationId, date)
//                        : adherenceLogService.getAdherenceLogsByMedication(medicationId)
//        );
//    }
//
//    // Calculate adherence rate for a user
//    @GetMapping("/user/{userId}/adherence-rate")
//    public AdherenceRateDto calculateAdherenceRate(
//            @PathVariable Long userId,
//            @RequestParam(required = false) LocalDate startDate,
//            @RequestParam(required = false) LocalDate endDate) {
//
//        return adherenceLogService.calculateAdherenceRate(
//                userId,
//                startDate != null ? startDate : LocalDate.now().minusMonths(1),
//                endDate != null ? endDate : LocalDate.now()
//        );
//    }
//
//    // Update adherence log
//    @PutMapping("/{id}")
//    public AdherenceLogDto updateAdherenceLog(
//            @PathVariable Long id,
//            @Valid @RequestBody UpdateAdherenceLogDto updateDto) {
//
//        return adherenceLogMapper.toDto(
//                adherenceLogService.updateAdherenceLog(id, updateDto)
//        );
//    }
//
//    // Delete adherence log
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteAdherenceLog(@PathVariable Long id) {
//        adherenceLogService.deleteAdherenceLog(id);
//    }
//
//    // Record medication taken
//    @PostMapping("/record")
//    public AdherenceLogDto recordMedicationTaken(
//            @Valid @RequestBody RecordMedicationTakenDto recordDto) {
//
//        return adherenceLogMapper.toDto(
//                adherenceLogService.recordMedicationTaken(
//                        recordDto.getUserId(),
//                        recordDto.getMedicationId(),
//                        recordDto.getTakenTime() != null ? recordDto.getTakenTime() : LocalTime.now()
//                )
//        );
//    }
}