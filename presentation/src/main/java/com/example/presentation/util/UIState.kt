package com.example.presentation.util

import com.example.presentation.model.RateUIModel
import com.example.presentation.model.TableUIData

sealed class UIState {
    object Loading : UIState()
    data class Success(val rates: Any) : UIState()
    data class SuccessChart(val map: Map<String, Double>)
    object Error : UIState()

}