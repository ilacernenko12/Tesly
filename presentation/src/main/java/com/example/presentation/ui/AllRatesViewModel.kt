package com.example.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.RateModel
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.presentation.mapper.CurrencyUiMapper
import com.example.presentation.model.RateUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val useCase: GetAllRatesUseCase,
    private val mapper: CurrencyUiMapper
): ViewModel() {

    private val _allRates = MutableLiveData<List<RateUIModel>>()
    val allRates: LiveData<List<RateUIModel>> = _allRates

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val rateUiModels = useCase.getCurrentRates().map { rateModel ->
                mapper.mapFromModel(rateModel)
            }
            _allRates.postValue(rateUiModels)
        }
    }
}