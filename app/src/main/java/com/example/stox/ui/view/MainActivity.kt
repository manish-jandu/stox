package com.example.stox.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.stox.ui.theme.StoxTheme
import com.example.stox.ui.view.main_dashboard.MainScreen
import com.example.stox.ui.view_model.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val userViewModel by viewModels<UserViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //enableEdgeToEdge() // todo: recheck for color tint

        setContent {
            StoxTheme {
                val holdings by userViewModel.userHoldingsState.collectAsStateWithLifecycle()
                MainScreen(holdings)
            }
        }

    }
}




