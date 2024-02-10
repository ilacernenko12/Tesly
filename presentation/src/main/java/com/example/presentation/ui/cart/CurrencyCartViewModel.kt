package com.example.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.CurrencyRepository
import com.example.presentation.model.CartUiData
import com.example.presentation.util.UIState
import com.example.presentation.util.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrencyCartViewModel @Inject constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    // флоу используемый для обновления данных на UI
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    companion object {
        const val DECEMBER_RATES = "2023/12/31"
        const val RUB_ABBREVIATION = "RUB"
        const val USD_ABBREVIATION = "USD"
        const val CNY_ABBREVIATION = "CNY"
    }

    fun updateData(date: Date) {
        // Показываем прогресс-бар при начале загрузки
        _uiState.value = UIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentRatesDeferred = async { repository.getRatesByDay(simpleDateFormat.format(date)) }
                val currentRates = currentRatesDeferred.await()

                val decemberRatesDeferred = async { repository.getRatesByDay(DECEMBER_RATES) }
                val decemberRates = decemberRatesDeferred.await()

                val yesterdayRatesDeferred = async { repository.getRatesByDay(getPreviousDate(date)) }
                val yesterdayRates = yesterdayRatesDeferred.await()

                val uiData = mutableListOf<CartUiData>()

                currentRates.collect { currentRatesList ->
                    decemberRates.collect { decemberRatesList ->
                        yesterdayRates.collect { yesterdayRatesList ->
                            // Проходим по каждой валюте
                            for (currency in listOf(RUB_ABBREVIATION, USD_ABBREVIATION, CNY_ABBREVIATION)) {
                                // Фильтруем списки по текущей валюте
                                val currentRate = currentRatesList.find { it.abbreviation == currency }
                                val decemberRate = decemberRatesList.find { it.abbreviation == currency }
                                val yesterdayRate = yesterdayRatesList.find { it.abbreviation == currency }

                                // Проверяем, что все данные для текущей валюты есть
                                if (currentRate != null && decemberRate != null && yesterdayRate != null) {
                                    // Вычисляем разницы
                                    val decemberDiff = ((decemberRate.officialRate - currentRate.officialRate) / currentRate.officialRate * 100).roundTo(2).toString()
                                    val yesterdayDiff = ((yesterdayRate.officialRate - currentRate.officialRate) / currentRate.officialRate * 100).roundTo(2).toString()

                                    // Создаем объект CartUiData
                                    val cartUiData = CartUiData(
                                        scale = currentRate.scale,
                                        rateName = currentRate.name,
                                        officialRate = currentRate.officialRate.toString(),
                                        decemberDiff = decemberDiff,
                                        yesterdayDiff = yesterdayDiff
                                    )

                                    uiData.add(cartUiData)
                                }
                            }
                        }
                    }
                }

            _uiState.value = UIState.Success(uiData)

            } catch (e: Exception) {
                // В случае возникновения ошибки устанавливаем состояние ошибки
                _uiState.value = UIState.Error
            }
        }
    }

    private fun getPreviousDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1) // вычитаем один день
        return simpleDateFormat.format(calendar.time)
    }
}