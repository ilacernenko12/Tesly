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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
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

    // состояние экрана
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.value = UIState.Loading // Показываем прогресс-бар при начале загрузки

        viewModelScope.launch {
            try {
                val currentRatesDeferred = async { rateUseCase.getCurrentRates() }
                val differenceRatesDeferred =
                    async { rateUseCase.getRateDifferences(getYesterdayDate()) }

                val currentRates = currentRatesDeferred.await()
                val differenceRates = differenceRatesDeferred.await()

                currentRates.collect { currentRatesList ->
                    differenceRates.collect { differenceRatesList ->
                        val mergedRates = mutableListOf<TableUIData>()

                        currentRatesList.forEach { currentRate ->
                            val differenceRate =
                                differenceRatesList.find { it.name == currentRate.name }
                            val difference = differenceRate?.difference ?: 0.0
                            val mergedRate = currentRate.copy(difference = difference)
                            if (currentRate.abbreviation != "XDR") { // Проверка на аббревиатуру "XDR"
                                flagUseCase.getFlag(currentRate.abbreviation).collect { flagModel ->
                                    val mergedRate = currentRate.copy(
                                        difference = differenceRate?.difference
                                            ?: 0.0
                                    )
                                    mergedRates.add(
                                        TableUIData(
                                            rates = currencyMapper.mapFromModel(mergedRate).copy(),
                                            flags = flagMapper.mapFromModel(flagModel)
                                        )
                                    )

                                    databaseUseCase.insertRatesData(
                                        tableUiMapper.mapToModel(
                                            TableUIData(
                                                rates = currencyMapper.mapFromModel(mergedRate)
                                                    .copy(),
                                                flags = flagMapper.mapFromModel(flagModel)
                                            )
                                        )
                                    )
                                }
                            } else {
                                // В случае, если аббревиатура "XDR", не отправляем запрос
                                val mergedRate = currentRate.copy(
                                    difference = differenceRate?.difference
                                        ?: 0.0
                                )
                                mergedRates.add(
                                    TableUIData(
                                        rates = currencyMapper.mapFromModel(mergedRate).copy(),
                                        flags = FlagUIModel(flagUrl = "")
                                    )
                                )
                                databaseUseCase.insertRatesData(
                                    tableUiMapper.mapToModel(
                                        TableUIData(
                                            rates = currencyMapper.mapFromModel(mergedRate)
                                                .copy(),
                                            flags = FlagUIModel(flagUrl = "")
                                        )
                                    )
                                )
                            }
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