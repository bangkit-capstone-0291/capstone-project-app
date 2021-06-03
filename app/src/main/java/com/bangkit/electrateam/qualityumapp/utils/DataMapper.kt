package com.bangkit.electrateam.qualityumapp.utils

import com.bangkit.electrateam.qualityumapp.model.StockData
import com.bangkit.electrateam.qualityumapp.data.local.entity.StockEntity

object DataMapper {

    fun mapDomainToEntity(input: StockData) = StockEntity(
        id = input.id,
        image = input.image,
        name = input.name,
        category = input.category,
        quantity = input.quantity,
        description = input.description,
        quality = input.quality,
        expDate = input.expDate,
        isFavorite = input.isFavorite
    )

    fun mapEntityToDomain(input: StockEntity) = StockData(
        id = input.id,
        image = input.image,
        name = input.name,
        category = input.category,
        quantity = input.quantity,
        description = input.description,
        quality = input.quality,
        expDate = input.expDate,
        isFavorite = input.isFavorite
    )

    fun mapEntitiesToDomain(input: List<StockEntity>): List<StockData> =
        input.map {
            StockData(
                id = it.id,
                image = it.image,
                name = it.name,
                category = it.category,
                quantity = it.quantity,
                description = it.description,
                quality = it.quality,
                expDate = it.expDate,
                isFavorite = it.isFavorite
            )
        }
}