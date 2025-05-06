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

    // Formatter for LocalDateTime (yyyy-MM-dd'T'HH:mm:ss)
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDateTimeToString(dateTime: LocalDateTime?): String? {
        return dateTime?.format(dateTimeFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromStringToLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, dateTimeFormatter) }
    }


    // --- List<LocalTime> converters ---
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalTimeListToString(list: List<LocalTime>?): String? {
        return list?.joinToString(",") { it.format(timeFormatter) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromStringToLocalTimeList(value: String?): List<LocalTime>? {
        return value?.split(",")?.map { LocalTime.parse(it, timeFormatter) }
    }

    // --- Single LocalTime converters ---
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalTimeToString(value: LocalTime?): String? {
        return value?.format(timeFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromStringToLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, timeFormatter) }
    }

    // --- LocalDate converters ---
    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromLocalDateToString(date: LocalDate?): String? {
        return date?.format(dateFormatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromStringToLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, dateFormatter) }
    }
}
