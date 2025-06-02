

package com.example.medicationapp.model

/** A simple model class to hold descriptive details about a client's medications.
Contains a list of medication names, recommendations, and interaction information.
Provides getter methods for recommendations and interactions but does not expose setters or the medication list.
 **/



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