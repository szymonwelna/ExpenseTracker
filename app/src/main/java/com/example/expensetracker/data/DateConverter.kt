package com.example.expensetracker.data

import androidx.room.TypeConverter
import com.example.expensetracker.utils.trimToDate
import kotlin.time.Instant

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.trimToDate()?.toEpochMilliseconds()
    }
}