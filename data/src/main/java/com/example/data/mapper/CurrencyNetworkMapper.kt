package com.example.data.mapper

import com.example.data.remote.model.RateResponse
import com.example.domain.model.RateModel

class CurrencyNetworkMapper: Mapper<RateResponse, RateModel> {
    override fun mapFromEntity(type: RateResponse): RateModel {
        return RateModel(
            abbreviation = type.abbreviation,
            name = type.name,
            officialRate = type.officialRate
        )
    }

    override fun mapToEntity(type: RateModel): RateResponse {
        TODO("Not yet implemented")
    }
}