package com.inti.inhabuildingsearch

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem(val route: String, val title: String, val icon: ImageVector) {
    object Splash : NavItem("splash", "스플래시 스크린", Icons.Default.Launch)
    object Home : NavItem("home", "홈", Icons.Default.Home)
    object BuildingView : NavItem("buildingview", "3D 건물뷰", Icons.Default.Business)
    object Direction : NavItem("direction", "건물 안 길 찾기", Icons.Default.NearMe)
    object Login : NavItem("login", "로그인", Icons.Default.Face)
}