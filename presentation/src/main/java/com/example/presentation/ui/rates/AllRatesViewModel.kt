package com.example.presentation.ui.rates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.domain.usecase.GetDataFromDatabaseUseCase
import com.example.domain.usecase.GetFlagUseCase
import com.example.presentation.mapper.CurrencyUiMapper
import com.example.presentation.mapper.FlagUiMapper
import com.example.presentation.mapper.TableUiMapper
import com.example.presentation.model.FlagUIModel
import com.example.presentation.model.TableUIData
import com.example.presentation.util.UIState
import com.example.presentation.util.previousDateAsString
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
class AllRatesViewModel @Inject constructor(
    private val rateUseCase: GetAllRatesUseCase,
    private val flagUseCase: GetFlagUseCase,
    private val databaseUseCase: GetDataFromDatabaseUseCase,
    private val currencyMapper: CurrencyUiMapper,
    private val flagMapper: FlagUiMapper,
    private val tableUiMapper: TableUiMapper
) : ViewModel() {

    // флоу используемый для обновления данных на UI
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    fun checkCacheAndRefresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val isDataAvailable = databaseUseCase.isRatesDataAvailable()
            if (isDataAvailable) {
                refreshData()
            } else {
                loadData()
            }
        }
    }

    private fun refreshData() {
        // Показываем прогресс-бар при начале загрузки
        _uiState.value = UIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val refreshedData = databaseUseCase.getAllRates().map {
                    tableUiMapper.mapFromModel(it)
                }
                _uiState.value = UIState.Success(refreshedData)
            } catch (e: Exception) {
                // В случае возникновения ошибки устанавливаем состояние ошибки
                _uiState.value = UIState.Error
            }
        }
    }
    private fun loadData() {
        // Показываем прогресс-бар при начале загрузки
        _uiState.value = UIState.Loading

        viewModelScope.launch {
            try {
                // Получаем текущие курсы валют асинхронно
                val currentRatesDeferred = async { rateUseCase.getCurrentRates() }
                // Получаем разницу курсов валют за предыдущий день асинхронно
                val differenceRatesDeferred =
                    async { rateUseCase.getRateDifferences(Date().previousDateAsString()) }

                // Ожидаем завершения получения текущих курсов
                val currentRates = currentRatesDeferred.await()
                // Ожидаем завершения получения разницы курсов за предыдущий день
                val differenceRates = differenceRatesDeferred.await()

                // Собираем информацию о курсах валют и разнице в курсах в общий список
                val mergedRates = mutableListOf<TableUIData>()
                currentRates.collect { currentRatesList ->
                    differenceRates.collect { differenceRatesList ->
                        currentRatesList.forEach { currentRate ->
                            // Ищем разницу в курсах для текущей валюты
                            val differenceRate =
                                differenceRatesList.find { it.name == currentRate.name }
                            // Получаем разницу курса, если она есть, или 0
                            val difference = differenceRate?.difference ?: 0.0
                            // Создаем объект с объединенной информацией о курсе и разнице курса
                            val mergedRate = currentRate.copy(difference = difference)

                            // Получаем флаг для текущей валюты (кроме XDR)
                            if (currentRate.abbreviation != "XDR") {
                                flagUseCase.getFlag(currentRate.abbreviation).collect { flagModel ->
                                    mergedRates.add(
                                        TableUIData(
                                            rates = currencyMapper.mapFromModel(mergedRate).copy(),
                                            flags = flagMapper.mapFromModel(flagModel)
                                        )
                                    )
                                }
                            } else {
                                // В случае аббревиатуры "XDR" не отправляем запрос на получение флага
                                mergedRates.add(
                                    TableUIData(
                                        rates = currencyMapper.mapFromModel(mergedRate).copy(),
                                        flags = FlagUIModel(flagUrl = "")
                                    )
                                )
                            }
                        }
                    }
                }
                // Устанавливаем состояние успешного завершения загрузки с объединенными курсами
                _uiState.value = UIState.Success(mergedRates)

                // сразу сохраним в кэш
                saveToCache(mergedRates)
            } catch (e: Exception) {
                // В случае возникновения ошибки устанавливаем состояние ошибки
                _uiState.value = UIState.Error
            }
        }
    }


    // сохранение данных полученных от сервера в БД
    private fun saveToCache(mergedRates: List<TableUIData>){
        viewModelScope.launch(Dispatchers.IO) {
            val models = mergedRates.map { tableUIData ->
                tableUiMapper.mapToModel(tableUIData)
            }
            databaseUseCase.insertRatesData(models)
        }
    }
}