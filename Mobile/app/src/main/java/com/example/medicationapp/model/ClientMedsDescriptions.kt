package com.example.medicationapp.model


class ClientMedsDescriptions {
    private var medication: List<String> = emptyList()

    private var recommendations: String =""

    private var interactions: String = ""


    fun getRecommendations(): String{
        return recommendations
    }

    fun getInteractions():String {
        return interactions
    }
}