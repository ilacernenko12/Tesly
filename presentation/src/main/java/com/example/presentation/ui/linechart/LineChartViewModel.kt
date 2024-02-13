package com.example.presentation.ui.linechart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.presentation.mapper.ChartMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@HiltViewModel
class LineChartViewModel(
    private val rateUseCase: GetAllRatesUseCase,
    private val mapper: ChartMapper
): ViewModel() {

    private val currentDate = Date()
    private val calendar = Calendar.getInstance()

    init {
        calendar.time = currentDate
    }


    fun getDataForChart() {
        // Создаем пустую мапу для хранения данных
        val map = mutableMapOf<String, Double>()
        viewModelScope.launch(Dispatchers.IO) {
            for (i in 1..7) {
                // Получаем копию текущей даты
                val date = calendar.time

                rateUseCase.getRatesByDay(formatDateToString(date)).collect { ratesList ->
                    val filteredRates = ratesList.filter { it.abbreviation == "USD" }
                    filteredRates.forEach { rate ->
                        // Добавляем данные в мапу
                        map[formatDateForMap(date).substring(0,4).replace('/', '.')] = rate.officialRate
                    }
                }

                // Уменьшаем дату на одну неделю
                calendar.add(Calendar.WEEK_OF_YEAR, -1)
            }
        }
    }

    private fun formatDateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun formatDateForMap(date: Date): String {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return dateFormat.format(date)
    }
}