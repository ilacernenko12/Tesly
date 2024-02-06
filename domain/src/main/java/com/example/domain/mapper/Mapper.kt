package com.example.domain.mapper

interface Mapper<Model, UiObject> {
    fun mapFromModel(type: Model): UiObject

    fun mapToModel(type: UiObject): Model
}