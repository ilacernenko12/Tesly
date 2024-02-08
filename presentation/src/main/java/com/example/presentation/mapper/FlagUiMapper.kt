package com.example.presentation.mapper

import com.example.domain.model.FlagModel
import com.example.presentation.model.FlagUIModel

class FlagUiMapper: Mapper<FlagModel, FlagUIModel> {
    override fun mapFromModel(type: FlagModel): FlagUIModel {
        return FlagUIModel(
            flagUrl = type.flagUrl
        )
    }

    override fun mapToModel(type: FlagUIModel): FlagModel {
        TODO("Not yet implemented")
    }
}