package com.example.vehicledashboard

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository

class FakeVehicleRepository(
    private val vehicles: List<Vehicle> = emptyList(),
    private val error: Throwable? = null
) : VehicleRepository {

    override suspend fun getVehicles(): List<Vehicle> {
        error?.let { throw it }
        return vehicles
    }

    override suspend fun getVehicleById(id: String): Vehicle? {
        error?.let { throw it }
        return vehicles.firstOrNull { it.id == id }
    }
}