package com.paxcreation.weatherapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.paxcreation.weatherapp.data.entity.LocationSearchEntity
import io.reactivex.rxjava3.core.Observable

@Dao
interface LocationDao : BaseDao<LocationSearchEntity> {
    @Query("SELECT * From LocationSearch")
    fun getAllData(): Observable<List<LocationSearchEntity>>

    @Query("DELETE FROM LocationSearch WHERE woeid = :woeid")
    fun deleteById(woeid: Int)
    //insert id location weather
}