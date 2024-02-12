package com.example.presentation.mapper

import com.example.domain.model.CartModel
import com.example.presentation.model.CartUiData
import com.example.presentation.model.RateUIModel

class CartUiMapper: Mapper<CartModel, CartUiData> {
    override fun mapFromModel(type: CartModel): CartUiData {
        return CartUiData (
            scale = type.scale,
            rateName = type.rateName,
            officialRate = type.officialRate.toString(),
            decemberDiff = type.decemberDiff.toString(),
            yesterdayDiff = type.yesterdayDiff.toString(),
            isPositiveDecemberDiff = type.isPositiveDecemberDiff,
            isPositiveYesterdayDiff = type.isPositiveYesterdayDiff
        )
    }

    override fun mapToModel(type: CartUiData): CartModel {
        return CartModel(
            id = 0,
            scale = type.scale,
            rateName = type.rateName,
            officialRate = type.officialRate.toDouble(),
            decemberDiff = type.decemberDiff.toDouble(),
            yesterdayDiff = type.yesterdayDiff.toDouble(),
            isPositiveDecemberDiff = type.isPositiveDecemberDiff,
            isPositiveYesterdayDiff = type.isPositiveYesterdayDiff
        )
    }
}