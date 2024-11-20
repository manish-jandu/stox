package com.example.stox

import com.example.stox.data.model.Holding
import com.example.stox.utils.calculateCurrentValue
import com.example.stox.utils.calculateTodaysPNL
import com.example.stox.utils.calculateTotalInvestment
import com.example.stox.utils.calculateTotalPNL
import com.example.stox.utils.calculateTotalPNLPerc
import com.example.stox.utils.roundTo
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    private val holdings = listOf(
        Holding(symbol = "ASHOKLEY", quantity = 3, ltp = 119.10, avgPrice = 116.0, close = 118.0),
        Holding(symbol = "HDFC", quantity = 7, ltp = 2497.20, avgPrice = 2550.0, close = 2500.0),
        Holding(symbol = "ICICIBANK", quantity = 1, ltp = 624.70, avgPrice = 610.0, close = 620.0),
        Holding(symbol = "IDEA", quantity = 71, ltp = 9.95, avgPrice = 9.85, close = 10.0)
    )

    @Test
    fun `test calculateCurrentValue`() {
        val expected = 3 * 119.10 + 7 * 2497.20 + 1 * 624.70 + 71 * 9.95
        val actual = calculateCurrentValue(holdings)
        assertEquals(expected.roundTo(2), actual.roundTo(2), 0.01)
    }

    @Test
    fun `test calculateTotalInvestment`() {
        val expected = 3 * 116.0 + 7 * 2550.0 + 1 * 610.0 + 71 * 9.85
        val actual = calculateTotalInvestment(holdings)
        assertEquals(expected.roundTo(2), actual.roundTo(2), 0.01)
    }

    @Test
    fun `test calculateTotalPNL`() {
        val currentValue = calculateCurrentValue(holdings)
        val totalInvestment = calculateTotalInvestment(holdings)
        val expected = currentValue - totalInvestment
        val actual = calculateTotalPNL(holdings)
        assertEquals(expected.roundTo(2), actual.roundTo(2), 0.01)
    }

    @Test
    fun `test calculateTotalPNLPerc`() {
        val currentValue = calculateCurrentValue(holdings)
        val totalInvestment = calculateTotalInvestment(holdings)
        val expected = (currentValue - totalInvestment) / totalInvestment
        val actual = calculateTotalPNLPerc(holdings)
        assertEquals(expected.roundTo(4), actual.roundTo(4), 0.0001)
    }

    @Test
    fun `test roundTo function`() {
        val number = 123.456789
        assertEquals(123.46, number.roundTo(2), 0.0)
        assertEquals(123.4568, number.roundTo(4), 0.0)
        assertEquals(123.0, number.roundTo(0), 0.0)
    }

    @Test
    fun `test calculateTodaysPNL`() {
        val holding = Holding(symbol = "ASHOKLEY", quantity = 3, ltp = 119.10, avgPrice = 116.0, close = 118.0)
        val expected = (holding.close - holding.ltp) * holding.quantity
        val actual = calculateTodaysPNL(holding)
        assertEquals(expected.roundTo(2), actual.roundTo(2), 0.01)
    }
}
