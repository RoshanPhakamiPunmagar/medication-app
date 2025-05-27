package com.example.meditime.model;

import java.util.List;

/**
 * Immutable data record representing the results of an adherence analysis.
 *
 * This record encapsulates a summary of medication adherence,
 * a list of medication IDs flagged as problematic,
 * observed time-based adherence patterns,
 * and tailored recommendations for improving adherence.
 *
 * Fields:
 * - adherenceSummary: A textual summary describing overall adherence behavior.
 * - problematicMedications: A list of medication IDs identified as having adherence issues.
 * - timePatterns: Descriptions of temporal patterns in adherence (e.g., missed doses at specific times).
 * - recommendations: Suggested actions or advice to improve medication adherence.
 */
public record AdherenceAnalysis(
        String adherenceSummary,
        List<Long> problematicMedications,
        String timePatterns,
        String recommendations
) {}
