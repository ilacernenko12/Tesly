package com.example.data.mapper

interface Mapper<Entity, Model> {
    fun mapFromEntity(type: Entity): Model

    fun mapToEntity(type: Model): Entity
}