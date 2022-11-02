package com.example.android.unscramble.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


enum class GameApp {
    Game,
    Analytics
}

@Composable
fun GameApp(
    navController: NavHostController = rememberNavController()
) {

    GameApp.Game.name
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = GameApp.Analytics.name,
        ) {
            composable(route = GameApp.Game.name) {
                GameScreen(onShowAnalytics = {
                    navController.navigate(GameApp.Analytics.name)
                })
            }
            composable(route = GameApp.Analytics.name) {
                AnalyticsScreen()
            }
        }
    }
}