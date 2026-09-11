package com.example.vehicledashboard.presentation.VehicalList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vehicledashboard.domain.model.Vehicle
import com.example.vehicledashboard.domain.usecase.GetVehiclesUseCase
import com.example.vehicledashboard.presentation.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
    private val getVehicles: GetVehiclesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<List<Vehicle>>>(UiState.Loading)
    val state: StateFlow<UiState<List<Vehicle>>> = _state.asStateFlow()

    init {
        load()
    }

    fun load() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            val result = getVehicles()
            _state.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Failed to load vehicles") }
            )
        }
    }
}