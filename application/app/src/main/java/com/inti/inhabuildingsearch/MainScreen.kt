package com.inti.inhabuildingsearch

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    // 아래 3개 변수는 state가 없는 컴포저블에 state를 주입하기 위한 remember 변수들
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    // coroutine state: 힙 메모리 영역에 저장된 코루틴 함수를 다시 재개(resume)하기 위해 필요한 모든 정보
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    // material UI를 사용하여 간단하게 UI를 그리기 위해 Scaffold 컴포저블 사용
    Scaffold(
        // state가 없는 scaffold에 state 주입
        // drawer가 열려있는 상태, 닫혀있는 상태를 저장하기 위함
        scaffoldState = scaffoldState,
        // 앱 상단의 Bar를 어떻게 그릴지
        topBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            when (navBackStackEntry?.destination?.route) {
                NavItem.Splash.route -> {} // 더미 함수
                else -> TopBar(scope = scope, scaffoldState = scaffoldState)
            }
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            when (navBackStackEntry?.destination?.route) {
                NavItem.Splash.route -> {} // 더미 함수
                else -> BottomBar(navController = navController)
            }
        },
        drawerBackgroundColor = colorResource(id = R.color.purple_200),
        // drawer 안에 들어가는 내용
        // 현재 상태를 인자값으로 Drawer 컴포저블 호출
        drawerContent = {
            Drawer(scope = scope, scaffoldState = scaffoldState, navController = navController)
        }
    ) {
        NavGraph(navController = navController)
    }
}


// MainScreen 컴포저블 안에있는 Scaffold의 topBar 매개변수에 넣을 인자의 람다 본문에서 해당 컴포저블을 호출
@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
) {
    TopAppBar(
        title = { Text("") },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },

        backgroundColor = colorResource(id = R.color.purple_200),
        contentColor = Color.White
    )

}

@Composable
fun Drawer(scope: CoroutineScope, scaffoldState: ScaffoldState, navController: NavController) {
    // 각 값들을 표시해주기 위해 리스트를 우선적으로 만들어준다
    val items = listOf(
        NavItem.Home,
        NavItem.BuildingView,
        NavItem.Login,
    )

    Column {
        // 로고 부분(헤더)
//        Image(
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
//            modifier = Modifier
//                .height(100.dp)
//                .fillMaxWidth()
//                .padding(10.dp)
//        )
        // 로고와 리스트 사이 공간 띄우는 부분
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        // 네비게이션 사용을 위해 필수적으로 선언해주어야 하는 부분
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // 네비게이션 아이템들의 리스트를 표시하는 부분
        items.forEach { item ->
            DrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    // 동일한 항목을 다시 선택할 때 동일한 대상의 여러 복사본을 사용하지 않도록 한다.
                    launchSingleTop = true
                    // 이전에 선택한 아이템을 다시 선택할 때 상태를 복원
                    restoreState = true
                }
                // Close drawer
                // 코루틴 스코프
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
        }

        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Developed by int i",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun DrawerItem(item: NavItem, selected: Boolean, onItemClick: (NavItem) -> Unit) {
    val background = if (selected) R.color.purple_200 else android.R.color.transparent
    Row(
        // 아이콘과 글자를 세로 중앙정렬
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            // 아이템을 클릭하면 Drawer에서 만든 onItemClick 람다를 호출
            .clickable(onClick = { onItemClick(item) })
            .height(45.dp)
            .background(colorResource(id = background))
            .padding(start = 10.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
        )
        Spacer(modifier = Modifier.width(7.dp))
        Text(
            text = item.title,
            fontSize = 18.sp,
            color = Color.White
        )
    }
}

// MainScreen 컴포저블 안에있는 Scaffold의 bottomBar 매개변수에 넣을 인자의 람다 본문에서 해당 컴포저블을 호출
@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        NavItem.Home,
        NavItem.BuildingView,
        NavItem.Direction,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .padding(10.dp)
            .clip(
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    topEnd = 10.dp,
                    bottomEnd = 10.dp,
                    bottomStart = 10.dp
                )
            )
    )
    { // 둥근모서리
        screens.forEach { item ->
            AddItem(
                item = item,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    item: NavItem,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = item.title)
        },
        icon = {
            Icon(
                imageVector = item.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(item.route) {
                popUpTo(navController.graph.findStartDestination().id)
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                // 동일한 항목을 다시 선택할 때 동일한 대상의 여러 복사본을 사용하지 않도록 한다.
                launchSingleTop = true
                // 이전에 선택한 아이템을 다시 선택할 때 상태를 복원
                restoreState = true
            }
        }
    )
}