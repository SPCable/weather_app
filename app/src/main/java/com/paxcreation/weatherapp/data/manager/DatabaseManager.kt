package com.paxcreation.weatherapp.data.manager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paxcreation.weatherapp.data.entity.LocationSearchEntity
import com.paxcreation.weatherapp.data.local.db.dao.LocationDao


@Database(
    entities = [LocationSearchEntity::class],
    version = 1,
    exportSchema = false
)


abstract class DatabaseManager : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}
