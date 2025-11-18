package com.example.kriponapp.models

import java.util.Date

data class Expenses(
    val id: Int,
    val title: String,
    val categoryId: Int,
    val amount: Double,
    val date: Date
)
