package com.universall.watertracker.core

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

data class TimeRange(
    val start: LocalTime,
    val end: LocalTime
)

fun LocalDateTime.asTimestampWithDefaultZone(): Long {
    val zone = ZoneId.systemDefault()
    return this.atZone(zone).toInstant().toEpochMilli()
}

fun timestampRange(start: LocalDateTime, end: LocalDateTime): Pair<Long, Long> {
    return start.asTimestampWithDefaultZone() to end.asTimestampWithDefaultZone();
}


fun LocalDate.dayBounds(): Pair<LocalDateTime, LocalDateTime> {
    val start = this.atStartOfDay()
    val end = this.atTime(LocalTime.MAX)
    return start to end
}

fun LocalDate.timestampBounds(): Pair<Long, Long> {
    val (start, end) = this.dayBounds()
    return timestampRange(start, end)
}

fun LocalDate.weekBounds(
    firstDayOfWeek: DayOfWeek = DayOfWeek.MONDAY
): Pair<LocalDateTime, LocalDateTime> {
    val start = this.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)).atStartOfDay()
    val end = this.with(TemporalAdjusters.previousOrSame(firstDayOfWeek.minus(1))).atTime(LocalTime.MAX)
    return start to end
}

fun LocalDate.timestampWeekBounds(): Pair<Long, Long> {
    val (start, end) = this.weekBounds()
    return timestampRange(start, end)
}

fun LocalTime?.toHHMM(): String {
    return this?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "00:00"
}
