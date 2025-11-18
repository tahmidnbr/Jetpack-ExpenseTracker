package com.example.kriponapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kriponapp.R
import com.example.kriponapp.models.Categories
import com.example.kriponapp.models.categories
import androidx.compose.foundation.lazy.items
import com.example.kriponapp.models.Expenses
import com.example.kriponapp.viewmodels.HomeViewmodel
import java.text.SimpleDateFormat
import java.util.Locale

val ropa = FontFamily(Font(R.font.ropasans_regular))

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    homeViewmodel: HomeViewmodel
) {
    val selectedCategory = homeViewmodel.selectedCategories.value
    val expenses = homeViewmodel.expensesForSelectedCategory
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
            item {
                ExpenseCard(expenses = expenses)
            }
            item {
                Category(homeViewmodel = homeViewmodel)
            }
            item {
                Expenses(expenses = expenses)
            }
    }
}

@Composable
fun ExpenseCard(expenses: List<Expenses>) {

    val total = expenses.sumOf { it.amount }  // Sum all expenses
    val budget = 250.0 // dynamic if needed

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFFDBE2EF)),
        colors = CardDefaults.cardColors(containerColor = Color(0xff3F72AF))// optional shadow
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 20.dp)
                .fillMaxWidth()
        ) {
            Text(
                "This Month's Spending",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ropa
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "November",
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = ropa

            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "$${total}",
                    fontSize = 50.sp,
                    fontFamily = ropa,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF9F7F7)
                )

                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "/ $${budget} budget",
                    fontSize = 15.sp,
                    fontFamily = ropa,
                    color = Color.LightGray,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 4.dp,
                color = Color(0x40C4BABA)
            )

        }

    }
}

@Composable
fun Category(homeViewmodel: HomeViewmodel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),

    ) {
        Text(
            "Categories",
            modifier = Modifier
                .fillMaxWidth(),
            fontFamily = ropa,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { selectedCategory ->
                        homeViewmodel.onCategorySelected((selectedCategory))
                    }
                )
            }
        }
    }
}

@Composable
fun Expenses(expenses: List<Expenses>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(
            "Recent Expenses",
            fontFamily = ropa,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth(),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        expenses.forEach { expenses ->
            ExpenseItem(expenses)
        }
    }
}

@Composable
fun CategoryCard(
    category: Categories,
    onClick: (Categories) -> Unit) {
    Card(
        modifier = Modifier
            .clickable{onClick(category)},
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDBE2EF)),
        shape = RoundedCornerShape(22.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category.categoryIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(22.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(
                category.categoryTitle,
                fontFamily = ropa,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

}

@Composable
fun ExpenseItem(expenses: Expenses) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(expenses.date)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDBE2EF))
        ) {
            Icon(
                imageVector = expenses.categoryId.let { id ->
                    categories.firstOrNull{ it.id == id}?.categoryIcon
                        ?: Icons.Outlined.FavoriteBorder
                },
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.width(18.dp))
        Column{
            Text(
                expenses.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = ropa
            )

            Spacer(modifier = Modifier.height(2.dp))
            Text(
                formattedDate,
                fontSize = 16.sp,
                fontFamily = ropa
            )
        }

        Spacer(modifier = Modifier.width(18.dp))
        Text(
            "$${expenses.amount}",
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.End,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = ropa
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Home(
        homeViewmodel = HomeViewmodel(),
    )
}
