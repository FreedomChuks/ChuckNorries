package com.example.chucknorries.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import java.time.LocalDate

class ListConverter {
    @TypeConverter
    fun fromString(stringListString: String): List<String> {
        return stringListString.split(",").map { it }
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString(separator = ",")
    }
}