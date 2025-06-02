package com.example.medicationapp.model

/**
 * AiAnalysisResponse.kt
 *
 * Data class representing the response from the AI analysis engine.
 * This model holds textual data related to the analysis of a client's
 * medication adherence patterns, potential risks, and recommendations.
 **/

 class AiAnalysisResponse {
    internal var patterns: String = ""
    internal var risks: String = ""
    internal var recommendations: String = ""
}