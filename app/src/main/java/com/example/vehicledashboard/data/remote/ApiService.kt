package com.example.vehicledashboard.data.remote

import com.example.vehicledashboard.data.model.VehicleDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("vehicles")
    suspend fun getVehicles(): List<VehicleDto>

    @GET("vehicles/{id}")
    suspend fun getVehicleById(@Path("id") id: String): VehicleDto
}