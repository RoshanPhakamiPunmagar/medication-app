package com.example.meditime.model;

import java.util.List;

public record AdherenceAnalysis(
        String adherenceSummary,
        List<Long> problematicMedications,
        String timePatterns,
        String recommendations
) {}