package com.example.stox.ui.view.portfolio_tab

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stox.data.model.Holding
import com.example.stox.utils.calculateTodaysPNL
import com.example.stox.utils.calculateTotalPNL
import com.example.stox.utils.calculateTotalPNLPerc
import com.example.stox.utils.roundTo

@Composable
fun PortfolioContent(modifier: Modifier, holdings: List<Holding>) {
    var isSummaryExpanded by remember { mutableStateOf(false) } // To toggle expanded state
    val animatedHeight by animateDpAsState(if (isSummaryExpanded) 100.dp else 50.dp) // Animate height
    val animatedAlpha by animateFloatAsState(if (isSummaryExpanded) 1f else 0.6f) // Animate transparency

    val totalPNL = calculateTotalPNL(holdings)
    val toalPNLPerc = calculateTotalPNLPerc(holdings)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        LazyColumn(modifier = Modifier
            .padding(16.dp)
            .weight(1f)) {
            items(holdings.size) { index ->
                HoldingRow(holding = holdings[index])
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(animatedHeight)
                .background(
                    Color.Gray.copy(alpha = animatedAlpha),
                )
                .pointerInput(Unit) { // Detect swipe gestures
                    detectVerticalDragGestures(
                        onVerticalDrag = { _, dragAmount ->
                            if (dragAmount < -20) { // Swipe up (dragAmount is negative)
                                isSummaryExpanded = true
                            } else if (dragAmount > 20) { // Swipe down (dragAmount is positive)
                                isSummaryExpanded = false
                            }
                        }
                    )
                }
                .clickable {
                    isSummaryExpanded = !isSummaryExpanded
                },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Profit & Loss*",
                    color = Color.Black.copy(alpha = animatedAlpha),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "₹${totalPNL.roundTo(2)} (${toalPNLPerc.roundTo(2)}%)",
                    color = if (totalPNL >= 0.0) Color.Green.copy(alpha = animatedAlpha) else Color.Red.copy(
                        alpha = animatedAlpha
                    ), // Adjust text alpha
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }

    }
}

@Composable
fun HoldingRow(holding: Holding) {
    val isProfit = holding.ltp - holding.close >= 0
    val profitLossColor = if (isProfit) Color(
        0xFF4CAF50
    ) else Color.Red

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = holding.symbol,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(
                modifier = Modifier.height(
                    4.dp
                )
            )
            Text(
                text = "Net Qty: ${holding.quantity}",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "LTP: ₹${holding.ltp}",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Spacer(
                modifier = Modifier.height(
                    4.dp
                )
            )
            Text(
                text = "P&L: ₹${calculateTodaysPNL(holding).roundTo(2)}",
                color = profitLossColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        }
    }
}