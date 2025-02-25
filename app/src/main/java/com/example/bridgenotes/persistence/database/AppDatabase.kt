package com.example.bridgenotes.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.bridgenotes.persistence.dao.TournamentDao
import com.example.bridgenotes.persistence.entity.DealEntity
import com.example.bridgenotes.persistence.entity.TournamentEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Database(
    entities = [TournamentEntity::class, DealEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tournamentDao(): TournamentDao
}

class Converters {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        return dateString?.let { LocalDateTime.parse(it, formatter) }
    }
}
