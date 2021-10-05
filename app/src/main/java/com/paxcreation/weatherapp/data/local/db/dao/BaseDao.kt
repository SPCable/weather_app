package com.paxcreation.weatherapp.data.local.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t:T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(t:List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(t:T)

    @Delete
    fun delete(t:T)
}