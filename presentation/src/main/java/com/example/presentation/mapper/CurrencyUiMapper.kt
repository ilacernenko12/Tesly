package com.example.presentation.mapper

import android.graphics.Color
import com.example.domain.model.RateModel
import com.example.presentation.model.RateUIModel
import com.example.presentation.util.FlagUrlProvider
import javax.inject.Inject

class CurrencyUiMapper@Inject constructor(private val flagUrlProvider: FlagUrlProvider): Mapper<RateModel, RateUIModel> {
    override fun mapFromModel(type: RateModel): RateUIModel {
        val color = if (type.difference > 0) Color.GREEN else Color.RED
        val differenceSign = if (type.difference > 0) "+" else ""
        val formattedDifference = "$differenceSign${"%.4f".format(type.difference)}"
        val flagUrl = type.abbreviation.lowercase()

        return RateUIModel(
            flag = flagUrl,
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