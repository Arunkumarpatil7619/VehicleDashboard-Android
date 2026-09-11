package com.example.vehicledashboard.data.repository

import com.example.vehicledashboard.data.model.toDomain
import com.example.vehicledashboard.data.remote.ApiService
import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VehicleRepositoryImpl @Inject constructor(
    private val api: ApiService
) : VehicleRepository {

    override suspend fun getVehicles(): List<Vehicle> =
        api.getVehicles().map { it.toDomain() }

    override suspend fun getVehicleById(id: String): Vehicle? =
        runCatching { api.getVehicleById(id).toDomain() }.getOrNull()
}