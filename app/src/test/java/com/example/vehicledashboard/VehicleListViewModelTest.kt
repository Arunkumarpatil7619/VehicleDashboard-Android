package com.example.vehicledashboard

import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.repository.VehicleRepository
import com.example.vehicledashboard.domain.usecase.GetVehiclesUseCase
import com.example.vehicledashboard.presentation.VehicalList.VehicleListViewModel
import com.example.vehicledashboard.presentation.common.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VehicleListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `emits success state when repository returns vehicles`() = runTest {
        val vehicles = listOf(
            Vehicle(
                id = "v1",
                name = "Swift",
                model = "ZXi",
                batteryPercentage = 73,
                estimatedRangeKm = 287.4,
                currentSpeedKmh = 0.0,
                odometerKm = 18432.7,
                isOnline = true,
                lastUpdatedEpochMs = 1700000000000L
            )
        )
        val repository = FakeVehicleRepository(vehicles = vehicles)
        val viewModel = VehicleListViewModel(GetVehiclesUseCase(repository))

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Success)
        assertEquals(1, (state as UiState.Success).data.size)
        assertEquals("Swift", state.data.first().name)
    }

    @Test
    fun `emits error state when repository fails`() = runTest {
        val repository = FakeVehicleRepository(error = RuntimeException("No network"))
        val viewModel = VehicleListViewModel(GetVehiclesUseCase(repository))

        advanceUntilIdle()

        val state = viewModel.state.value
        assertTrue(state is UiState.Error)
        assertEquals("No network", (state as UiState.Error).message)
    }
}

