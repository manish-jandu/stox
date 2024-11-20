package com.example.stox.ui.view.portfolio_tab

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.stox.data.NetworkUtils.Resource
import com.example.stox.data.model.Holding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PortfolioTabContent(modifier: Modifier, holdings: Resource<List<Holding>>) {
    val pagerState = rememberPagerState()
    val pages = listOf("Position", "Holdings")
    val scope = rememberCoroutineScope()

    Column(modifier = modifier) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.White,
            contentColor = Color.Black
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page == 0) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = pages[page], style = MaterialTheme.typography.h4)
                }
            } else {
                if (holdings is Resource.Success) {
                    PortfolioContent(modifier = Modifier, holdings = holdings?.data ?: emptyList())
                } else {
                    //todo: shimmer layout or error
                }
            }
        }
    }
}

