package com.example.presentation.mapper

import com.example.domain.model.RatesStorageModel
import com.example.presentation.model.FlagUIModel
import com.example.presentation.model.RateUIModel
import com.example.presentation.model.TableUIData

class TableUiMapper: Mapper<RatesStorageModel, TableUIData> {
    override fun mapFromModel(type: RatesStorageModel): TableUIData {
        return TableUIData(
            rates = RateUIModel(
                name = type.name,
                officialRate = type.rate.toString(),
                difference = type.difference.toString(),
                color = type.color
            ),
            flags = FlagUIModel(
                type.flagUrl
            )
        )
    }

    override fun mapToModel(type: TableUIData): RatesStorageModel {
        return RatesStorageModel(
            id = 0,
            flagUrl = type.flags.flagUrl,
            name = type.rates.name,
            rate = type.rates.officialRate.toDouble(),
            difference = type.rates.difference.toDouble(),
            color = type.rates.color
        )
    }
}