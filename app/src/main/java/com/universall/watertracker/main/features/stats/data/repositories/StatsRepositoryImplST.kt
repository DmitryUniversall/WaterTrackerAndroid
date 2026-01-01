package com.universall.watertracker.main.features.stats.data.repositories

import com.universall.watertracker.core.SingletonHolder
import com.universall.watertracker.core.asTimestampWithDefaultZone
import com.universall.watertracker.core.enumFromId
import com.universall.watertracker.core.timestampRange
import com.universall.watertracker.main.features.settings.domain.entities.WaterMeasureUnit
import com.universall.watertracker.main.features.stats.data.db.dao.WaterIntakeDAO
import com.universall.watertracker.main.features.stats.data.db.entities.WaterIntakeEntity
import com.universall.watertracker.main.features.stats.domain.entities.WaterIntake
import com.universall.watertracker.main.features.stats.domain.repositories.StatsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class StatsRepositoryImplST private constructor(
    private val dao: WaterIntakeDAO
) : StatsRepository {
    private fun waterIntakeFromEntity(
        entity: WaterIntakeEntity,
        zoneId: ZoneId = ZoneId.systemDefault()
    ): WaterIntake {
        return WaterIntake(
            id = entity.id,
            amount = entity.amount,
            dateTime = Instant.ofEpochMilli(entity.timestamp)
                .atZone(zoneId)
                .toLocalDateTime(),
            waterMeasureUnit = enumFromId<WaterMeasureUnit>(entity.waterMeasureUnitID)!!
        )
    }

    override fun observeWaterIntakesBetween(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<List<WaterIntake>> {
        val (startTimestamp, endTimestamp) = timestampRange(start, end)
        return dao.observeWaterIntakesBetween(startTimestamp, endTimestamp)
            .map { entities ->
                entities.map(this::waterIntakeFromEntity)
            }
    }

    override fun observeAmountTotalBetween(
        start: LocalDateTime,
        end: LocalDateTime
    ): Flow<Int> {
        val (startTimestamp, endTimestamp) = timestampRange(start, end)
        return dao.observeTotalBetween(startTimestamp, endTimestamp)
    }

    override suspend fun createIntake(amount: Int, measureUnit: WaterMeasureUnit) {
        dao.insert(
            entity = WaterIntakeEntity(
                amount = amount,
                waterMeasureUnitID = measureUnit.id.toLong(),
                timestamp = LocalDateTime.now().asTimestampWithDefaultZone()
            )
        )
    }

    companion object : SingletonHolder<StatsRepositoryImplST, WaterIntakeDAO>(::StatsRepositoryImplST)
}
