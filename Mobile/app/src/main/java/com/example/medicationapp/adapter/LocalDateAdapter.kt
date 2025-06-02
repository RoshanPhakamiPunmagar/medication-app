
package com.example.medicationapp.adapter


    import com.google.gson.*
    import java.lang.reflect.Type
    import java.time.LocalDate
    import java.time.LocalTime
    import java.time.format.DateTimeFormatter

/**
 * Gson adapters for serializing and deserializing Java 8 LocalDate and LocalTime objects.
 * LocalDateAdapter converts LocalDate to and from ISO-8601 formatted strings for JSON.
 * LocalTimeAdapter converts LocalTime to and from ISO-8601 formatted strings for JSON.
 * These adapters enable seamless JSON parsing and generation when working with date and time types.
 */


    class LocalDateAdapter : JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

        override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(src?.format(formatter))
        }

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate {
            return LocalDate.parse(json?.asString, formatter)
        }
    }

    class LocalTimeAdapter : JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
        private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

        override fun serialize(src: LocalTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(src?.format(formatter))
        }

        override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalTime {
            return LocalTime.parse(json?.asString, formatter)
        }
    }

