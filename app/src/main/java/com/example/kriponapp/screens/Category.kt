package com.example.kriponapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kriponapp.models.Categories

@Composable
fun Category(
    modifier: Modifier = Modifier,
    categories: List<Categories>
){
    LazyColumn(
        modifier = modifier
    ) {
        items(categories) { category ->
            CategoryItem(category = category, onClick = {
                // Navigate to category details/history
                //navController.navigate("category/${category.id}")
            })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CategoryItem(
    category: Categories,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFDBE2EF))
        ) {
            Icon(
                imageVector = category.categoryIcon,
                contentDescription = category.categoryTitle,
                modifier = Modifier
                    .size(40.dp)
                    .padding(6.dp)
            )
        }

        Spacer(modifier = Modifier.width(18.dp))

        Text(
            text = category.categoryTitle,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = ropa,
            modifier = Modifier.weight(1f) // take all space except arrow
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Go to ${category.categoryTitle}",
            tint = Color.Gray,
            modifier = Modifier.size(30.dp)
        )
    }
}