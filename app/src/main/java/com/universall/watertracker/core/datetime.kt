package com.universall.watertracker.core

import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAdjusters
import java.util.Locale

data class TimeRange(
    val start: LocalTime,
    val end: LocalTime
)

fun Long.toLocalDateTime(
    zone: ZoneId = ZoneId.systemDefault()
): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zone)

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
    val weekStart = this.with(TemporalAdjusters.previousOrSame(firstDayOfWeek)).atStartOfDay()
    val weekEnd = weekStart.plusDays(6).toLocalDate().atTime(LocalTime.MAX)
    return weekStart to weekEnd
}

fun LocalDate.timestampWeekBounds(): Pair<Long, Long> {
    val (start, end) = this.weekBounds()
    return timestampRange(start, end)
}


fun DayOfWeek.twoLetterWeekDay(): String {
    return this.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).substring(0, 2)
}

fun LocalDate.twoLetterWeekDay(): String {
    return this.dayOfWeek.twoLetterWeekDay()
}

fun LocalTime?.toHHMM(): String {
    return this?.format(DateTimeFormatter.ofPattern("HH:mm")) ?: "00:00"
}

fun LocalDate.datesUntilInclusive(endDate: LocalDate): Sequence<LocalDate> =
    generateSequence(this) { date ->
        val next = date.plusDays(1)
        if (next.isAfter(endDate)) null else next
    }
