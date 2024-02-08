package com.example.presentation.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.RateModel
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.presentation.mapper.CurrencyUiMapper
import com.example.presentation.model.RateUIModel
import com.example.presentation.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val rateUseCase: GetAllRatesUseCase,
    private val mapper: CurrencyUiMapper
) : ViewModel() {

    // состояние экрана
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    init {
        _uiState.value = UIState.Loading // Показываем прогресс-бар при начале загрузки

        viewModelScope.launch {
            try {
                val currentRatesDeferred = async { rateUseCase.getCurrentRates() }
                val differenceRatesDeferred = async { rateUseCase.getRateDifferences(getYesterdayDate()) }

                val currentRates = currentRatesDeferred.await()
                val differenceRates = differenceRatesDeferred.await()

                currentRates.collect { currentRatesList ->
                    differenceRates.collect { differenceRatesList ->
                        val mergedRates = mutableListOf<RateUIModel>()

                        currentRatesList.forEach { currentRate ->
                            val differenceRate = differenceRatesList.find { it.name == currentRate.name }
                            val difference = differenceRate?.difference ?: 0.0
                            val mergedRate = currentRate.copy(difference = difference)
                            mergedRates.add(mapper.mapFromModel(mergedRate).copy())
                        }

                        _uiState.value = UIState.Success(mergedRates)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error
            }
        }
    }

    // Дата вчерашнего дня
    private fun getYesterdayDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        return simpleDateFormat.format(calendar.time)
    }
}