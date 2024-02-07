package com.example.presentation.mapper

import android.provider.Telephony.Mms.Rate
import com.example.domain.model.RateModel
import com.example.presentation.model.RateUIModel

class CurrencyUiMapper: Mapper<RateModel, RateUIModel> {
    override fun mapFromModel(type: RateModel): RateUIModel {
        return RateUIModel(
            name = type.name,
            officialRate = type.officialRate.toString()
        )
    }

    override fun mapToModel(type: RateUIModel): RateModel {
        TODO("Not yet implemented")
    }
}