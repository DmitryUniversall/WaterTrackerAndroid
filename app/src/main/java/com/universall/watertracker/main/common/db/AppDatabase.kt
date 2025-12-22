package com.universall.watertracker.main.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.universall.watertracker.main.features.water_tracker.data.db.dao.WaterIntakeDAO
import com.universall.watertracker.main.features.water_tracker.data.db.entities.WaterIntakeEntity

@Database(
    entities = [WaterIntakeEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun waterIntakeDao(): WaterIntakeDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).fallbackToDestructiveMigration(false)
                    .build()
                    .also { instance = it }
            }
        }
    }
}
