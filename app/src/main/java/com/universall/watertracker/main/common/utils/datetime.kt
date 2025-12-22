package com.universall.watertracker.main.common.utils

import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId

fun todayTimestampRange(): Pair<Long, Long> {
    val start = LocalDate.now()
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

    val end = start + Duration.ofDays(1).toMillis()
    return start to end
}
