package com.assignment.weatherreport.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

/**
 * Creation of ROOM database to save weather data locally,Database name is "synchronoss_db"
 *
 */
@Database(entities = [ServiceResponseEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun serviceResponseDao(): ServiceResponseDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()
        val DATABASE_NAME = "synchronoss_db"

        operator fun invoke(context: Context)= instance
            ?: synchronized(LOCK){
            instance
                ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .build()

    }

}