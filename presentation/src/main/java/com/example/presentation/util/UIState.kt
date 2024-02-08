package com.example.presentation.util

import com.example.presentation.model.RateUIModel

sealed class UIState {
    object Loading : UIState()
    data class Success(val rates: List<RateUIModel>) : UIState()
    object Error : UIState()
}