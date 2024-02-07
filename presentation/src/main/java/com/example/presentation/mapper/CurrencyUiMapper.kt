package com.example.presentation.mapper

import android.graphics.Color
import com.example.domain.model.RateModel
import com.example.presentation.model.RateUIModel

class CurrencyUiMapper: Mapper<RateModel, RateUIModel> {
    override fun mapFromModel(type: RateModel): RateUIModel {
        val color = if (type.difference > 0) Color.GREEN else Color.RED
        val differenceSign = if (type.difference > 0) "+" else ""
        val formattedDifference = "$differenceSign${"%.4f".format(type.difference)}"

        return RateUIModel(
            name = type.name,
            officialRate = type.officialRate.toString(),
            difference = formattedDifference,
            color = color
        )
    }


    override fun mapToModel(type: RateUIModel): RateModel {
        TODO("Not yet implemented")
    }
}