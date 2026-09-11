package com.example.vehicledashboard.domain.usecase

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import javax.inject.Inject

class GetVehiclesUseCase @Inject constructor(
    private val repository: VehicleRepository
) {
    suspend operator fun invoke(): Result<List<Vehicle>> = runCatching {
        repository.getVehicles()
    }
}