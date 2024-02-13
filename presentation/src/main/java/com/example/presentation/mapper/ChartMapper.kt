package com.example.presentation.mapper

import com.example.domain.model.RateModel
import com.example.presentation.model.ChartUiModel

class ChartMapper: Mapper<RateModel, ChartUiModel> {
    override fun mapFromModel(type: RateModel): ChartUiModel {
        return ChartUiModel(
            officialRate = type.officialRate.toString()
        )
    }

    override fun mapToModel(type: ChartUiModel): RateModel {
        TODO("Not yet implemented")
    }
}