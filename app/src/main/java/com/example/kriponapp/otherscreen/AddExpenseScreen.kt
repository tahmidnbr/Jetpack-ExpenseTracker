package com.example.kriponapp.otherscreen

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.kriponapp.models.Categories
import com.example.kriponapp.models.Expenses
import com.example.kriponapp.models.categories
import com.example.kriponapp.screens.ropa
import com.example.kriponapp.viewmodels.HomeViewmodel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    homeViewModel: HomeViewmodel,
    navController: NavController
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Categories?>(null) }
    var showCategoryDropdown by remember { mutableStateOf(false) }

    var date by remember { mutableStateOf(Date()) }
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() } // remember calendar

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Expense", fontWeight = FontWeight.Bold, fontFamily = ropa) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Title Field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title", fontFamily = ropa) },
                    modifier = Modifier.fillMaxWidth()
                )

                // Amount Field
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount", fontFamily = ropa) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = showCategoryDropdown,
                    onExpandedChange = { showCategoryDropdown = it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCategory?.categoryTitle ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category", fontFamily = ropa) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = showCategoryDropdown
                            )
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = showCategoryDropdown,
                        onDismissRequest = { showCategoryDropdown = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.categoryTitle) },
                                onClick = {
                                    selectedCategory = category
                                    showCategoryDropdown = false
                                },
                                modifier = if (selectedCategory == category)
                                    Modifier.background(Color.LightGray)
                                else Modifier
                            )
                        }
                    }
                }

                // Date Picker Field
                // inside your composable


                OutlinedTextField(
                    value = date.formatToString(),
                    onValueChange = {},
                    enabled = false,
                    label = { Text("Date", fontFamily = ropa) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // Set calendar to current date state
                            calendar.time = date

                            DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendar.set(year, month, dayOfMonth)
                                    date = calendar.time // update state
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            ).show()
                        },
                )


                Spacer(modifier = Modifier.height(24.dp))

                // Save Button
                Button(
                    onClick = {
                        if (title.isNotEmpty() && amount.isNotEmpty() && selectedCategory != null) {
                            homeViewModel.addExpense(
                                Expenses(
                                    id = homeViewModel.allExpenses.value.size + 1,
                                    title = title,
                                    categoryId = selectedCategory!!.id,
                                    amount = amount.toInt(),
                                    date = date
                                )
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Save Expense",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = ropa
                    )
                }
            }
        }
    )
}

// Helper to format Date
fun Date.formatToString(pattern: String = "MMM dd, yyyy", locale: Locale = Locale.getDefault()): String {
    return SimpleDateFormat(pattern, locale).format(this)
}
