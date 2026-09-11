package com.example.vehicledashboard.domain.repository

import com.example.vehicledashboard.domain.model.Vehicle

interface VehicleRepository {
    suspend fun getVehicles(): List<Vehicle>
    suspend fun getVehicleById(id: String): Vehicle?
}