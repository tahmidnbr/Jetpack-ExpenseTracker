package com.example.kriponapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kriponapp.graphs.AppNavGraph
import com.example.kriponapp.otherscreen.AddExpenseScreen
import com.example.kriponapp.ui.theme.KriponAppTheme
import com.example.kriponapp.viewmodels.HomeViewmodel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KriponAppTheme {
                val navController = rememberNavController()
                val homeViewModel = remember { HomeViewmodel() }

                NavHost(
                    navController = navController,
                    startDestination = "main/home"
                ) {

                    // Main container screen with drawer + top bar
                    composable("main/{screen}") { backStackEntry ->
                        val screen = backStackEntry.arguments?.getString("screen") ?: "home"

                        MainScreen(
                            navController = navController,
                            homeViewModel = homeViewModel,
                            currentScreen = screen
                        )
                    }

                    // Add Expense = separate full screen
                    composable("addExpense") {
                        AddExpenseScreen(
                            navController = navController,
                            homeViewModel = homeViewModel
                        )
                    }
                }
            }
        }

    }
}