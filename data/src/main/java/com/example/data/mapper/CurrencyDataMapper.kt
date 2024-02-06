package com.example.data.mapper

import com.example.data.remote.RateResponse
import com.example.domain.model.RateModel

class CurrencyDataMapper: Mapper<RateResponse, RateModel> {
    override fun mapFromEntity(type: RateResponse): RateModel {
        return RateModel(
            name = type.name,
            officialRate = type.officialRate
        )
    }

    override fun mapToEntity(type: RateModel): RateResponse {
        TODO("Not yet implemented")
    }
}