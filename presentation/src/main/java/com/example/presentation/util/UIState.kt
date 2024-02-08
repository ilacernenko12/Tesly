package com.example.presentation.util

import com.example.presentation.model.RateUIModel
import com.example.presentation.model.TableUIData

sealed class UIState {
    object Loading : UIState()
    data class Success(val rates: List<TableUIData>) : UIState()
    object Error : UIState()
}