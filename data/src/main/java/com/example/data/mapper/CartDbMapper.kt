package com.example.data.mapper

import com.example.data.local.entity.CartEntity
import com.example.domain.model.CartModel

class CartDbMapper: Mapper<CartEntity, CartModel> {
    override fun mapFromEntity(type: CartEntity): CartModel {
        return CartModel(
            id = type.id,
            scale = type.scale,
            rateName = type.rateName,
            officialRate = type.officialRate,
            decemberDiff = type.decemberDiff,
            yesterdayDiff = type.yesterdayDiff,
            isPositiveDecemberDiff = type.isPositiveDecemberDiff,
            isPositiveYesterdayDiff = type.isPositiveYesterdayDiff
        )
    }

    override fun mapToEntity(type: CartModel): CartEntity {
        return CartEntity(
            id = type.id,
            scale = type.scale,
            rateName = type.rateName,
            officialRate = type.officialRate,
            decemberDiff = type.decemberDiff,
            yesterdayDiff = type.yesterdayDiff,
            isPositiveDecemberDiff = type.isPositiveDecemberDiff,
            isPositiveYesterdayDiff = type.isPositiveYesterdayDiff
        )
    }
}