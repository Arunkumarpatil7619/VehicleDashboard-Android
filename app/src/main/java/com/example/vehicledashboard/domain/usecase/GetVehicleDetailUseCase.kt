package com.example.vehicledashboard.domain.usecase

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import javax.inject.Inject

class GetVehicleDetailUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    suspend operator fun invoke(id: String): Result<Vehicle> = runCatching {
        repository.getVehicleById(id) ?: error("Vehicle not found")
    }
}