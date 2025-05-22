package com.example.meditime.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiAnalysisResponse {
    private String patterns;
    private String risks;
    private String recommendations;
}