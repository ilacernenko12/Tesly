package com.example.domain.mapper

import com.example.domain.model.RateModel
import com.example.presentation.model.RateUIModel

class CurrencyDomainMapper: Mapper<RateModel, RateUIModel> {
    override fun mapFromModel(type: RateModel): RateUIModel {
        return RateUIModel(
            name = type.name,
            officialRate = type.officialRate
        )
    }

    override fun mapToModel(type: RateUIModel): RateModel {
        TODO("Not yet implemented")
    }
}