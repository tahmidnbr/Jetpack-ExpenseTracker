package com.example.kriponapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kriponapp.models.Categories
import com.example.kriponapp.models.Expenses
import java.util.Date

class HomeViewmodel: ViewModel() {

    private val _selectedCategory = mutableStateOf<Categories?>(null)
    val selectedCategories : State<Categories?> = _selectedCategory

    private val _allExpenses = mutableStateOf(
        listOf(
            Expenses(1, "Health  Expenses", 2, 100.0, Date()),
            Expenses(2, "Food  Expenses", 1, 50.0, Date()),
            Expenses(3, "Internet  Bill", 4, 30.0, Date())
        )
    )
    val allExpenses: State<List<Expenses>> = _allExpenses

    val expensesForSelectedCategory by derivedStateOf {
        _selectedCategory.value?.let { category ->
            if (category.id == 0) {
                _allExpenses.value          // Show ALL expenses
            } else {
                _allExpenses.value.filter { it.categoryId == category.id }
            }
        } ?: _allExpenses.value
    }


    fun  onCategorySelected(category: Categories){
        _selectedCategory.value = category
    }

    fun addExpense(expenses: Expenses){
        _allExpenses.value = _allExpenses.value + expenses
    }
}