package com.universall.watertracker.main.features.stats.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_intake")
data class WaterIntakeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Int,
    val timestamp: Long,
    val waterMeasureUnitID: Long
)
