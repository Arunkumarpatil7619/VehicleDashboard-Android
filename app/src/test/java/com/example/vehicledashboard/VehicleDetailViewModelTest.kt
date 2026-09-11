package com.example.vehicledashboard

import androidx.lifecycle.SavedStateHandle
import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import com.example.vehicledashboard.domain.usecase.GetVehicleDetailUseCase
import com.example.vehicledashboard.presentation.VehicalDetail.VehicleDetailViewModel
import com.example.vehicledashboard.presentation.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `emits success state with matching vehicle`() = runTest {
        val vehicle = Vehicle(
            id = "v2",
            name = "Nexon",
            model = "XZ+ Lux",
            batteryPercentage = 41,
            estimatedRangeKm = 162.8,
            currentSpeedKmh = 45.0,
            odometerKm = 9211.3,
            isOnline = false,
            lastUpdatedEpochMs = 1700000000000L
        )
        val repository = FakeVehicleRepository(vehicles = listOf(vehicle))
        val savedStateHandle = SavedStateHandle(mapOf("id" to "v2"))
        val viewModel = VehicleDetailViewModel(
            GetVehicleDetailUseCase(repository),
            savedStateHandle
        )

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Success)
        assertEquals("Nexon", (state as UiState.Success).data.name)
    }

    @Test
    fun `emits error state when vehicle is missing`() = runTest {
        val repository = FakeVehicleRepository(vehicles = emptyList())
        val savedStateHandle = SavedStateHandle(mapOf("id" to "v99"))
        val viewModel = VehicleDetailViewModel(
            GetVehicleDetailUseCase(repository),
            savedStateHandle
        )

        advanceUntilIdle()

        assertTrue(viewModel.state.value is UiState.Error)
    }
}

