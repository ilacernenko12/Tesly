package com.example.data.mapper

import com.example.data.local.entity.RatesEntity
import com.example.domain.model.RatesStorageModel

class DatabaseMapper: Mapper<RatesEntity, RatesStorageModel> {
    override fun mapFromEntity(type: RatesEntity): RatesStorageModel {
        return RatesStorageModel(
            id = type.id,
            flagUrl = type.flagUrl,
            name = type.name,
            rate =  type.rate,
            difference = type.difference,
            color = type.color
        )
    }

    override fun mapToEntity(type: RatesStorageModel): RatesEntity {
        return RatesEntity(
            id = type.id,
            flagUrl = type.flagUrl,
            name = type.name,
            rate =  type.rate,
            difference = type.difference,
            color = type.color
        )
    }
}