package com.example.presentation.ui.linechart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.presentation.util.UIState
import com.example.presentation.util.dateStringStartOfDay
import com.example.presentation.util.dateStringStartOfYear
import com.example.presentation.util.reverseEntries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class LineChartViewModel @Inject constructor(
    private val rateUseCase: GetAllRatesUseCase
) : ViewModel() {

    private val currentDate = Date()
    private val calendar = Calendar.getInstance()

    // флоу используемый для обновления данных на UI
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    init {
        calendar.time = currentDate
        getDataForChart()
    }


    private fun getDataForChart() {
        // Показываем прогресс-бар при начале загрузки
        _uiState.value = UIState.Loading
        val map = mutableMapOf<String, Double>()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                for (i in 1..7) {
                    val date = calendar.time

                    rateUseCase.getRatesByDay(dateStringStartOfYear(date)).collect { ratesList ->
                        val filteredRates = ratesList.filter { it.abbreviation == "USD" }
                        filteredRates.forEach { rate ->
                            map[dateStringStartOfDay(date).substring(0, 5).replace('-', '.')] =
                                rate.officialRate
                        }
                    }
                    // Уменьшаем дату на одну неделю
                    calendar.add(Calendar.DAY_OF_YEAR, -1)
                }
                _uiState.value = UIState.Success(map.reverseEntries())
            } catch (e: Exception) {
                // В случае возникновения ошибки устанавливаем состояние ошибки
                _uiState.value = UIState.Error
            }
        }
    }
}