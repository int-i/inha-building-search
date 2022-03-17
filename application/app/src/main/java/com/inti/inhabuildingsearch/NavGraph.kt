package com.inti.inhabuildingsearch

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController) {
    // navController는 현재 어느 위치에 있는지에 대한 상태
    // startDestination은 시작 지점을 어디로 할건지
    NavHost(
        navController = navController,
        startDestination = NavItem.Splash.route
    ) {
        composable(route = NavItem.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = NavItem.Home.route) {
            HomeScreen()
        }
        composable(route = NavItem.BuildingView.route) {
            BuildingViewScreen()
        }
        composable(route = NavItem.Direction.route) {
            DirectionScreen()
        }
        composable(route=NavItem.Login.route) {
            LoginScreen()
        }
    }
}