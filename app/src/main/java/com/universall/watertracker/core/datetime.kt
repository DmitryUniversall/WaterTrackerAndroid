package com.universall.watertracker.core

import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class TimeRange(
    val start: LocalTime,
    val end: LocalTime
)

fun todayTimestampRange(): Pair<Long, Long> {
    val start = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val end = start + Duration.ofDays(1).toMillis()
    return start to end
}

fun LocalTime?.toHHMM(): String = this?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "00:00"
