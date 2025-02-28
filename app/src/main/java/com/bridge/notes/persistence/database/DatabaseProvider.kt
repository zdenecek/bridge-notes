package com.bridge.notes.persistence.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "bridge__database"
            )
                .fallbackToDestructiveMigration() // Use during development only
                .build()
            INSTANCE = instance
            instance
        }
    }
}
