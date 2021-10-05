package com.paxcreation.weatherapp.data.mapper

import com.paxcreation.weatherapp.data.entity.LocationSearchEntity
import com.paxcreation.weatherapp.domain.model.LocationSearch
import javax.inject.Inject

class LocationSearchMapper @Inject constructor() : Mapper<LocationSearchEntity, LocationSearch> {
    override fun mapFromEntity(type: LocationSearchEntity): LocationSearch {
        return LocationSearch(
            type.title,
            type.location_type,
            type.latt_long,
            type.woeid,
        )
    }

    override fun mapToEntity(type: LocationSearch): LocationSearchEntity {
        return LocationSearchEntity(
            title = type.title,
            location_type = type.location_type,
            latt_long = type.latt_long,
            woeid = type.woeid
        )
    }
}