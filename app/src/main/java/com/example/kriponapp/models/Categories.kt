package com.example.kriponapp.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ClearAll
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.NetworkCell
import androidx.compose.material.icons.outlined.Payment
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.ui.graphics.vector.ImageVector

data class Categories(
    val id: Int,
    val categoryTitle: String,
    val categoryIcon: ImageVector
)

val categories = listOf(
    Categories(0, "All", Icons.Outlined.ClearAll),
    Categories(1, "Food", Icons.Outlined.Fastfood),
    Categories(2, "Health", Icons.Outlined.HealthAndSafety),
    Categories(3, "Bills", Icons.Outlined.Payments),
    Categories(4, "Internet", Icons.Outlined.NetworkCell),
    Categories(5, "Transactions", Icons.Outlined.Payment)
)

