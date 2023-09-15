package data.remote.mapper

import data.remote.model.OrderDto
import domain.entity.Order

fun OrderDto.toEntity(): Order = Order(
    id = id ?: "",
    passengerName = passengerName ?: "",
    address = address ?: "",
    lat = lat ?: "",
    lng = lng ?: "",
)