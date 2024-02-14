package com.example.presentation.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.CurrencyRepository
import com.example.domain.usecase.GetDataFromDatabaseUseCase
import com.example.presentation.mapper.CartUiMapper
import com.example.presentation.model.CartUiData
import com.example.presentation.util.UIState
import com.example.presentation.util.dateStringStartOfYear
import com.example.presentation.util.previousDateAsString
import com.example.presentation.util.roundTo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class CurrencyCartViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val databaseUseCase: GetDataFromDatabaseUseCase,
    private val mapper: CartUiMapper
) : ViewModel() {

    // флоу используемый для обновления данных на UI
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    companion object {
        const val DECEMBER_RATES = "2023/12/31"
        const val RUB_ABBREVIATION = "RUB"
        const val USD_ABBREVIATION = "USD"
        const val CNY_ABBREVIATION = "CNY"
    }

    fun checkCacheAndRefresh(date: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            val isDataAvailable = databaseUseCase.isCartDataAvailable()
            if (isDataAvailable) {
                updateData()
            } else {
                loadData(date)
            }
        }
    }

    fun loadData(date: Date) {
        // Показываем прогресс-бар при начале загрузки
        _uiState.value = UIState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentRatesDeferred =
                    async { repository.getRatesByDay(dateStringStartOfYear(date)) }
                val currentRates = currentRatesDeferred.await()

                val decemberRatesDeferred = async { repository.getRatesByDay(DECEMBER_RATES) }
                val decemberRates = decemberRatesDeferred.await()

                val yesterdayRatesDeferred =
                    async { repository.getRatesByDay(date.previousDateAsString()) }
                val yesterdayRates = yesterdayRatesDeferred.await()

                val uiData = mutableListOf<CartUiData>()

                val rateList = mutableListOf<Double>()
                val decemberDiffList = mutableListOf<Double>()
                val yesterdayDiffList = mutableListOf<Double>()

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
                                    rateList.add(currentRate.officialRate)
                                    decemberDiffList.add(decemberDiff.toDouble())
                                    yesterdayDiffList.add(yesterdayDiff.toDouble())
                                }
                            }
                        }
                    }
                }
                val currencyCart = calculateGeometricMean(uiData)
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

                _uiState.value = UIState.Success(moveLastToFront(uiData))
                saveToCache(uiData)
            } catch (e: Exception) {
                // В случае возникновения ошибки устанавливаем состояние ошибки
                _uiState.value = UIState.Error
            }
        }
    }

    private fun updateData() {
        // Показываем прогресс-бар при начале загрузки
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedData = databaseUseCase.getCartData().map {
                    mapper.mapFromModel(it)
                }
                _uiState.value = UIState.Success(updatedData)
            } catch (e: Exception) {
                // В случае возникновения ошибки устанавливаем состояние ошибки
                _uiState.value = UIState.Error
            }
        }
    }

    // сохранение данных полученных от сервера в БД
    private fun saveToCache(mergedRates: List<CartUiData>){
        viewModelScope.launch(Dispatchers.IO) {
            val models = mergedRates.map { cartUiData ->
                mapper.mapToModel(cartUiData)
            }
            databaseUseCase.insertCartData(models)
        }
    }

    private fun calculateGeometricMean(
        list: List<CartUiData>
    ): List<Double> {

        val resultList = mutableListOf<Double>()

        val column1Value = list.map { powerWithSign(it.officialRate.toDouble(), 0.6) }.reduce { acc, i -> acc * i }
        val column2Value = list.map { powerWithSign(it.decemberDiff.toDouble(),0.3) }.reduce { acc, i -> acc * i }
        val column3Value = list.map { powerWithSign(it.yesterdayDiff.toDouble(),0.1) }.reduce { acc, i -> acc * i }

        resultList.add(powerWithSign(column1Value,1.0 / 3))
        resultList.add(powerWithSign(column2Value,1.0 / 3))
        resultList.add(powerWithSign(column3Value,1.0 / 3))

        return resultList
    }

    private fun powerWithSign(element: Double, power: Double): Double {
        return if (element >= 0) {
            Math.pow(element, power).roundTo(2)
        } else {
            -Math.pow(element.absoluteValue, power).roundTo(2)
        }
    }

    private fun moveLastToFront(list: MutableList<CartUiData>): MutableList<CartUiData> {
        val lastElement = list.removeAt(list.size - 1)

        // Добавляем его в начало списка
        list.add(0, lastElement)
        return list
    }
}