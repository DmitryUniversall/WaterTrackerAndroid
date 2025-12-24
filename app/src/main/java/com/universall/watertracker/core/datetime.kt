package com.universall.watertracker.core

import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

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
