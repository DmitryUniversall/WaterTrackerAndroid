package com.universall.watertracker.main.features.stats.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.universall.watertracker.main.features.stats.data.db.entities.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterIntakeDAO {
    @Insert
    suspend fun insert(entity: WaterIntakeEntity)

    @Query(
        """
        SELECT COALESCE(SUM(amount), 0)
        FROM water_intake
        WHERE timestamp BETWEEN :start AND :end
    """
    )
    fun observeTotalBetween(
        start: Long,
        end: Long
    ): Flow<Int>

    @Query(
        """
        SELECT *
        FROM water_intake
        WHERE timestamp BETWEEN :startTimestamp AND :endTimestamp
        ORDER BY timestamp DESC
    """
    )
    fun observeWaterIntakesBetween(
        startTimestamp: Long,
        endTimestamp: Long
    ): Flow<List<WaterIntakeEntity>>
}
