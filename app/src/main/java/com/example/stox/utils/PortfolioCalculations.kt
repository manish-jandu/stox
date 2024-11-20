package com.example.stox.utils

import com.example.stox.data.model.Holding

fun calculateCurrentValue(holdings: List<Holding>): Double {
    return holdings.sumOf { it.ltp * it.quantity }
}

fun calculateTotalInvestment(holdings: List<Holding>): Double {
    return holdings.sumOf { it.avgPrice * it.quantity }
}

fun calculateTotalPNL(holdings: List<Holding>): Double {
    val currentValue = calculateCurrentValue(holdings)
    val totalInvestment = calculateTotalInvestment(holdings)
    return currentValue - totalInvestment
}

fun calculateTotalPNLPerc(holdings: List<Holding>): Double {
    val currentValue = calculateCurrentValue(holdings)
    val totalInvestment = calculateTotalInvestment(holdings)
    return (currentValue - totalInvestment) / totalInvestment
}

fun Double.roundTo(digits: Int): Double {
    return "%.${digits}f".format(this).toDouble()
}


fun calculateTodaysPNL(holding:Holding): Double {
    return (holding.close - holding.ltp) * holding.quantity
}