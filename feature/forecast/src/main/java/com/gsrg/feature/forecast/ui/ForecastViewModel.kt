package com.gsrg.feature.forecast.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.domain.forecast.model.Forecast
import com.gsrg.domain.forecast.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository,
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.requestForecast(lat = 41.10168755298941, lon = -8.589474043996109)
        }
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
