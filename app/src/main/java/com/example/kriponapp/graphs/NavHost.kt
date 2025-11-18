package com.example.kriponapp.graphs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kriponapp.MainScreen
import com.example.kriponapp.otherscreen.AddExpenseScreen
import com.example.kriponapp.screens.Home
import com.example.kriponapp.screens.ropa
import com.example.kriponapp.viewmodels.HomeViewmodel

// AppNavGraph.kt
@Composable
fun AppNavGraph(
    navController: NavHostController,
    homeViewModel: HomeViewmodel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewmodel = viewModel()
            Home(modifier = modifier, homeViewmodel = viewModel)
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(
                navController = navController,
                homeViewModel = homeViewModel
            )
        }
    }
}
