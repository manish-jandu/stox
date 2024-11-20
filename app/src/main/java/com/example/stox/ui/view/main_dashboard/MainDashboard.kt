package com.example.stox.ui.view.main_dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stox.data.NetworkUtils.Resource
import com.example.stox.data.model.Holding
import com.example.stox.ui.theme.SelectedTab
import com.example.stox.ui.theme.TabBackground
import com.example.stox.ui.theme.Teal
import com.example.stox.ui.theme.UnSelectedTab
import com.example.stox.ui.view.portfolio_tab.PortfolioTabContent

@Composable
fun MainScreen(holdings: Resource<List<Holding>>) {
    val tabs = listOf("Watchlist", "Orders", "Portfolio", "Funds", "Invest")
    val icons = listOf(
        Icons.Default.List,
        Icons.Default.Refresh,
        Icons.Default.AccountCircle,
        Icons.Default.Favorite,
        Icons.Default.Build
    )
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = { ActionBar(selectedTab = selectedTab, titles = tabs) },
        bottomBar = {
            BottomNavigationUi(
                selectedTab = selectedTab,
                tabs = tabs,
                icons = icons,
                updateSelectedTab = { selectedTab = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                2 -> PortfolioTabContent(modifier = Modifier, holdings = holdings)
                else -> Text(
                    text = "Tab: ${tabs[selectedTab]}",
                    modifier = Modifier.fillMaxSize(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ActionBar(
    selectedTab: Int,
    titles: List<String>
) {
    val bgColor = Teal
    val tintColor = Color.White
    TopAppBar(
        modifier = Modifier
            .background(bgColor)
            .statusBarsPadding(),
        title = { Text(titles[selectedTab], color = tintColor) },
        backgroundColor = bgColor,
        elevation = 0.dp,
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_myplaces),
                    contentDescription = "User Icon",
                    tint = tintColor
                )
            }
        },
        actions = {
            if (selectedTab == 2) {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
                        contentDescription = "Sort Icon",
                        tint = tintColor
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_search),
                        contentDescription = "Search Icon",
                        tint = tintColor
                    )
                }
            }
        }
    )
}

@Composable
fun BottomNavigationUi(
    selectedTab: Int,
    tabs: List<String>,
    icons: List<ImageVector>,
    updateSelectedTab: (Int) -> Unit
) {

    BottomNavigation(backgroundColor = TabBackground) {
        tabs.forEachIndexed { index, title ->
            val color = if(index == selectedTab) SelectedTab else UnSelectedTab

            BottomNavigationItem(
                modifier = Modifier,
                icon = {
                    Icon(
                        icons[index],
                        contentDescription = title,
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .size(28.dp),
                        tint = color
                    )
                },
                label = {
                    Text(
                        title,
                        color = color,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 16.dp,top = 4.dp)
                    )
                },
                selected = selectedTab == index,
                onClick = { updateSelectedTab(index) },
            )
        }
    }
}

