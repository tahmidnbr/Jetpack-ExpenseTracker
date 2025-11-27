package com.example.kriponapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kriponapp.models.categories
import com.example.kriponapp.screens.Category
import com.example.kriponapp.screens.Home
import com.example.kriponapp.viewmodels.HomeViewmodel
import kotlinx.coroutines.launch

data class DrawItem(
    var title: String,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector
)

val darwItem = listOf(
    DrawItem(
        "Home",
        Icons.Filled.Home,
        Icons.Outlined.Home
    ),
    DrawItem(
        "Categories",
        Icons.Filled.Category,
        Icons.Outlined.Category
    ),
    DrawItem(
        "Budget",
        Icons.Filled.MonetizationOn,
        Icons.Outlined.MonetizationOn
    ),
    DrawItem(
        "Settings",
        Icons.Filled.Settings,
        Icons.Outlined.Settings
    )
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    homeViewModel: HomeViewmodel,
    currentScreen: String
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(currentScreen) {
        selectedIndex = darwItem.indexOfFirst { it.title.lowercase() == currentScreen }
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                darwItem.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected =  selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            navController.navigate("main/${item.title.lowercase()}")
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            containerColor = Color(0xffEEEEEE),
            topBar = {
                TopAppBar(
                    title = {
                        if (currentScreen != "home"){
                            Text(currentScreen.capitalize()) }
                        },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = null)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xffEEEEEE)
                    )
                )
            },
            floatingActionButton = {
                if (currentScreen == "home"){
                    FloatingActionButton(
                        onClick = { navController.navigate("addExpense") }
                    ) {
                        Icon(Icons.Default.Add, null)
                    }
                }
            },
        ) { padding ->
            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                // Your scrollable content here
                when (currentScreen) {
                    "home" -> Home(Modifier.padding(padding), homeViewModel)
                    "categories" -> Category(Modifier.padding(padding), categories= categories)
                    "budget" ->{}
                    "settings" -> {}
                }
            }
        }
    }
}



