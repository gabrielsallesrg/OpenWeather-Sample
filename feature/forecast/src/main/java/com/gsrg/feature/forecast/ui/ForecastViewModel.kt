package com.gsrg.feature.forecast.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.domain.forecast.helper.RequestResult
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.domain.forecast.repository.ForecastRepository
import com.gsrg.feature.forecast.ForecastApiKeyProvider
import com.gsrg.feature.forecast.service.LocationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository,
    private val locationService: LocationService,
    private val forecastApiKeyProvider: ForecastApiKeyProvider,
) : ViewModel() {

    private val _requestStatus = MutableStateFlow<RequestResult<Any>?>(null)
    fun requestStatus(): Flow<RequestResult<Any>?> = _requestStatus

    init {
        viewModelScope.launch {
            locationService.locationFlow().collectLatest { location ->
                if (location != null) {
                    repository.requestForecast(
                        lat = location.latitude,
                        lon = location.longitude,
                        apiKey = forecastApiKeyProvider.apiKey(),
                    ).let {
                        _requestStatus.value = it
                    }
                }
            }
        }
    }

    fun requestForecast() {
        locationService.updateCurrentLocation()
    }

    fun getForecast(): Flow<List<Pair<Int, List<Forecast>>>> {
        return repository.getForecast().map { list ->
            list
                .sortedBy { it.dateTime }
                .groupBy { it.dateTime.dayOfMonth }
                .toList()
        }
    }
}
