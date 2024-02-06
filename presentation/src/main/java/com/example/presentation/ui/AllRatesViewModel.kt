package com.example.presentation.ui

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.GetAllRatesUseCase
import com.example.presentation.model.RateUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class AllRatesViewModel @Inject constructor(
    private val useCase: GetAllRatesUseCase
): ViewModel() {

    val allRates: Flow<List<RateUIModel>> = flow {
        val data = useCase.getCurrentRates()
        emit(data)
    }.flowOn(Dispatchers.IO)
}