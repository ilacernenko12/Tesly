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
import kotlin.math.absoluteValue
import kotlin.math.pow

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
                val currentRatesDeferred =
                    async { repository.getRatesByDay(simpleDateFormat.format(date)) }
                val currentRates = currentRatesDeferred.await()

                val decemberRatesDeferred = async { repository.getRatesByDay(DECEMBER_RATES) }
                val decemberRates = decemberRatesDeferred.await()

                val yesterdayRatesDeferred =
                    async { repository.getRatesByDay(getPreviousDate(date)) }
                val yesterdayRates = yesterdayRatesDeferred.await()

                val uiData = mutableListOf<CartUiData>()

                val listOne = mutableListOf<Double>()
                val listTwo = mutableListOf<Double>()
                val listThree = mutableListOf<Double>()

                currentRates.collect { currentRatesList ->
                    decemberRates.collect { decemberRatesList ->
                        yesterdayRates.collect { yesterdayRatesList ->
                            // Проходим по каждой валюте
                            for (currency in listOf(
                                RUB_ABBREVIATION,
                                USD_ABBREVIATION,
                                CNY_ABBREVIATION
                            )) {
                                // Фильтруем списки по текущей валюте
                                val currentRate =
                                    currentRatesList.find { it.abbreviation == currency }
                                val decemberRate =
                                    decemberRatesList.find { it.abbreviation == currency }
                                val yesterdayRate =
                                    yesterdayRatesList.find { it.abbreviation == currency }

                                // Проверяем, что все данные для текущей валюты есть
                                if (currentRate != null && decemberRate != null && yesterdayRate != null) {
                                    // Вычисляем разницы
                                    val decemberDiff =
                                        ((decemberRate.officialRate - currentRate.officialRate) / currentRate.officialRate * 100).roundTo(
                                            2
                                        ).toString()
                                    val yesterdayDiff =
                                        ((yesterdayRate.officialRate - currentRate.officialRate) / currentRate.officialRate * 100).roundTo(
                                            2
                                        ).toString()

                                    // Создаем объект CartUiData
                                    val cartUiData = CartUiData(
                                        scale = currentRate.scale.toString(),
                                        rateName = currentRate.name,
                                        officialRate = currentRate.officialRate.toString(),
                                        decemberDiff = decemberDiff,
                                        yesterdayDiff = yesterdayDiff,
                                        isPositiveDecemberDiff = decemberDiff.toDouble() > 0.0,
                                        isPositiveYesterdayDiff = yesterdayDiff.toDouble() > 0.0
                                    )
                                    uiData.add(cartUiData)
                                    listOne.add(currentRate.officialRate)
                                    listTwo.add(decemberDiff.toDouble())
                                    listThree.add(yesterdayDiff.toDouble())
                                }
                            }
                        }
                    }
                }
                val currencyCart = calculateGeometricMean(listOne, listTwo, listThree)
                uiData.add(
                    CartUiData(
                        rateName = "Корзина валют",
                        officialRate = currencyCart[0].toString(),
                        decemberDiff = currencyCart[1].toString(),
                        yesterdayDiff = currencyCart[2].toString(),
                        isPositiveDecemberDiff = currencyCart[1] > 0.0,
                        isPositiveYesterdayDiff = currencyCart[2] > 0.0
                    )
                )
                _uiState.value = UIState.Success(uiData)

            } catch (e: Exception) {
                // В случае возникновения ошибки устанавливаем состояние ошибки
                _uiState.value = UIState.Error
            }
        }
    }

    private fun calculateGeometricMean(
        listOne: List<Double>,
        listTwo: List<Double>,
        listThree: List<Double>
    ): List<Double> {
        val resultList = mutableListOf<Double>()

        // Проверяем, что размеры всех списков равны
        require(listOne.size == listTwo.size && listTwo.size == listThree.size) { "Sizes of all lists must be equal" }

        // Проходим по всем индексам списков
        for (i in listOne.indices) {
            // Получаем текущие элементы из каждого списка
            val elementOne = listOne[i]
            val elementTwo = listTwo[i]
            val elementThree = listThree[i]

            // Возводим каждый элемент в указанную степень
            val poweredElementOne = if (elementOne >= 0) {
                Math.pow(elementOne, 0.6).roundTo(2)
            } else {
                -Math.pow(elementOne.absoluteValue, 0.6).roundTo(2)
            }

            val poweredElementTwo = if (elementTwo >= 0) {
                Math.pow(elementTwo, 0.3).roundTo(2)
            } else {
                -Math.pow(elementTwo.absoluteValue, 0.3).roundTo(2)
            }

            val poweredElementThree = if (elementThree >= 0) {
                Math.pow(elementThree, 0.1).roundTo(2)
            } else {
                -Math.pow(elementThree.absoluteValue, 0.1).roundTo(2)
            }


            // Находим произведение всех элементов
            val product = poweredElementOne * poweredElementTwo * poweredElementThree
            val geometricMean = if (product >=0) {
                product.pow(1.0 / 3).roundTo(2)
            } else {
                -Math.pow(product.absoluteValue, 1.0 / 3).roundTo(2)
            }
            // Добавляем результат в список
            resultList.add(geometricMean)
        }

        return resultList
    }

    private fun getPreviousDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, -1) // вычитаем один день
        return simpleDateFormat.format(calendar.time)
    }
}