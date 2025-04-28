package com.example.medicationapp.database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {

    // Formatter for LocalTime (HH:mm)
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // Formatter for LocalDate (yyyy-MM-dd)
    private val dateFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    // Formatter for LocalDateTime (ISO format)
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    // Convert LocalDateTime to String
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDateTimeToString(localDateTime: LocalDateTime?): String? {
        return localDateTime?.format(dateTimeFormatter)
    }

    // Convert String to LocalDateTime
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromStringToLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }

    // Convert LocalTime to String
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? {
        return value?.format(timeFormatter)
    }

    // Convert String to LocalTime
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, timeFormatter) }
    }

    // Convert LocalDate to String
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDateToString(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }

    // Convert String to LocalDate
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromStringToLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, dateFormatter) }
    }

    // Convert List<LocalDateTime> to String (for saving in the database)
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromListOfLocalDateTimes(value: List<LocalDateTime>?): String? {
        return value?.joinToString(",") { it.format(dateTimeFormatter) }
    }

    // Convert String to List<LocalDateTime> (for reading from the database)
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toListOfLocalDateTimes(value: String?): List<LocalDateTime> {
        return value?.split(",")?.map { LocalDateTime.parse(it, dateTimeFormatter) } ?: emptyList()
    }
}
