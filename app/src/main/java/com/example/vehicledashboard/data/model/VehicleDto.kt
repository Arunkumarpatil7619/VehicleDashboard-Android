package com.example.vehicledashboard.data.model

import com.example.vehicledashboard.domain.model.Vehicle
import com.google.gson.annotations.SerializedName

data class VehicleDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("model") val model: String,
    @SerializedName("battery") val battery: Int,
    @SerializedName("range_km") val rangeKm: Double,
    @SerializedName("speed_kmh") val speedKmh: Double,
    @SerializedName("odometer_km") val odometerKm: Double,
    @SerializedName("online") val online: Boolean,
    @SerializedName("last_updated") val lastUpdated: Long
)

fun VehicleDto.toDomain(): Vehicle = Vehicle(
    id = id,
    name = name,
    model = model,
    batteryPercentage = battery,
    estimatedRangeKm = rangeKm,
    currentSpeedKmh = speedKmh,
    odometerKm = odometerKm,
    isOnline = online,
    lastUpdatedEpochMs = lastUpdated
)