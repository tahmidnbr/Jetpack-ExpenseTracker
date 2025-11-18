package com.example.kriponapp.graphs

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AddExpense : Screen("add_expense")
    // Add more screens here if needed
}

