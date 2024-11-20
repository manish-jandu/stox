package com.example.stox.data.model

data class HoldingData(
    val data: UserHoldingData
)

data class UserHoldingData(
    val userHolding: List<Holding>
)

data class Holding(
    val symbol: String,
    val quantity: Int,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)
