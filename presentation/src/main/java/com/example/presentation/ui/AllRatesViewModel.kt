package com.example.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.presentation.mapper.CurrencyUiMapper
import com.example.presentation.model.RateUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val useCase: GetAllRatesUseCase,
    private val mapper: CurrencyUiMapper
) : ViewModel() {

    private val _ratesData = MutableStateFlow<List<RateUIModel>>(emptyList())
    val ratesData: StateFlow<List<RateUIModel>> = _ratesData

    private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    init {
        viewModelScope.launch {
            val currentRates = useCase.getCurrentRates()
            val differenceRates = useCase.getRateDifferences(getYesterdayDate())

            currentRates.collect { currentRatesList ->
                differenceRates.collect { differenceRatesList ->
                    val mergedRates = mutableListOf<RateUIModel>()

                    currentRatesList.forEach { currentRate ->
                        val differenceRate = differenceRatesList.find { it.name == currentRate.name }
                        val difference = differenceRate?.difference ?: 0.0
                        val mergedRate = currentRate.copy(difference = difference)
                        mergedRates.add(mapper.mapFromModel(mergedRate))
                    }

                    _ratesData.value = mergedRates
                }
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