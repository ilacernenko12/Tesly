package com.example.data.mapper

import com.example.data.remote.model.flags.FlagResponse
import com.example.domain.model.FlagModel

class FlagNetworkMapper: Mapper<FlagResponse, FlagModel> {
    override fun mapFromEntity(type: FlagResponse): FlagModel {
        return FlagModel(
            flagUrl = type.flag.png
        )
    }

    override fun mapToEntity(type: FlagModel): FlagResponse {
        TODO("Not yet implemented")
    }
}