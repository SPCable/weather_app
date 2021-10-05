package com.paxcreation.weatherapp.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "LocationSearch")
@Keep
data class LocationSearchEntity(
    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "location_type")
    var location_type: String,

    @ColumnInfo(name = "latt_long")
    var latt_long: String,

    @PrimaryKey
    @ColumnInfo(name = "woeid")
    var woeid: Int,

    ) : Parcelable
