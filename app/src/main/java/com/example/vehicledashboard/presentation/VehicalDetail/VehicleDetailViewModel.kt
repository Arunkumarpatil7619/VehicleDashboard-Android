package com.example.vehicledashboard.presentation.VehicalDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.usecase.GetVehicleDetailUseCase
import com.example.vehicledashboard.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleDetailViewModel @Inject constructor(
    private val getVehicleDetail: GetVehicleDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val vehicleId: String = savedStateHandle.get<String>("id").orEmpty()

    private val _state = MutableStateFlow<UiState<Vehicle>>(UiState.Loading)
    val state: StateFlow<UiState<Vehicle>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        if (vehicleId.isBlank()) {
            _state.value = UiState.Error("Invalid vehicle id")
            return
        }
        _state.value = UiState.Loading
        viewModelScope.launch {
            val result = getVehicleDetail(vehicleId)
            _state.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Failed to load vehicle") }
            )
        }
    }
}