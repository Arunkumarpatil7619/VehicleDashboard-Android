package com.example.vehicledashboard.domain.model

data class Vehicle(
    val id: String,
    val name: String,
    val model: String,
    val batteryPercentage: Int,
    val estimatedRangeKm: Double,
    val currentSpeedKmh: Double,
    val odometerKm: Double,
    val isOnline: Boolean,
    val lastUpdatedEpochMs: Long
)