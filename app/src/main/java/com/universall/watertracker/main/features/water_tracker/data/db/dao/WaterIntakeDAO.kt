package com.universall.watertracker.main.features.water_tracker.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.universall.watertracker.main.features.water_tracker.data.db.entities.WaterIntakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterIntakeDAO {
    @Insert
    suspend fun insert(entity: WaterIntakeEntity)

    @Query("""
        SELECT COALESCE(SUM(amountMl), 0)
        FROM water_intake
        WHERE timestamp BETWEEN :start AND :end
    """)
    fun observeTotalBetween(
        start: Long,
        end: Long
    ): Flow<Int>

    @Query("""
        SELECT *
        FROM water_intake
        WHERE timestamp BETWEEN :start AND :end
        ORDER BY timestamp DESC
    """)
    fun observerEntitiesBetween(
        start: Long,
        end: Long
    ): Flow<List<WaterIntakeEntity>>
}
