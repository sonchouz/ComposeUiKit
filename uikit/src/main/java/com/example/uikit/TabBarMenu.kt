package com.example.uikit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.navigation.currentBackStackEntryAsState

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: Int
)

val bottomMenuItems = listOf(
    BottomNavItem("home", "Главная", R.drawable.tab_bar_item),
    BottomNavItem("catalog", "Каталог", R.drawable.tab_bar_item_1_),
    BottomNavItem("projects", "Проекты", R.drawable.tab_bar_item_2_),
    BottomNavItem("profile", "Профиль", R.drawable.tab_bar_item_3_)
)

@Composable
fun BottomBar(navController: NavController){
    val backStateEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStateEntry.value?.destination?.route

    NavigationBar(
        containerColor = Color(0xFFFFFFFF)
    ) {
        bottomMenuItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp)

                    )
                },

                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MyTheme.mycolors.accent,
                    selectedTextColor = MyTheme.mycolors.accent,
                    unselectedIconColor = MyTheme.mycolors.input_icon,
                    unselectedTextColor = MyTheme.mycolors.input_icon,
                    indicatorColor = Color.Transparent
                ))

        }
    }

}
@Composable
fun MainScreen(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {BottomBar(navController)}
    ){
        padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        )
        {
            composable("home"){HomeScreen()}
            composable("catalog"){CatalogScreen()}
            composable("projects"){ProjectsScreen()}
            composable("profile") { ProfileScreen() }
        }
    }
}
@Preview
@Composable
fun menuPreview(){
    MainScreen()
}
@Composable
fun HomeScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Главная")
    }
}

@Composable
fun CatalogScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Каталог")
    }
}

@Composable
fun ProjectsScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Проекты")
    }
}

@Composable
fun ProfileScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Профиль")
    }
}