package com.example.newstoday.presentation.theme.category_screen_tabBar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newstoday.presentation.theme.ui.BlackPrimary
import com.example.newstoday.presentation.theme.ui.GreyDark
import com.example.newstoday.presentation.theme.ui.GreyLighter
import com.example.newstoday.presentation.theme.ui.GreyPrimary
import com.example.newstoday.presentation.theme.ui.PurplePrimary

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = hiltViewModel(),
) {
    val categories = listOf(
        "Sports",
        "Politics",
        "Life",
        "Gaming",
        "Animals",
        "Nature",
        "Food",
        "Art",
        "History",
        "Fashion",
        "Covid-19",
        "Middle East"
    )

    val emojiList = listOf(
        "\uD83C\uDFC8   ",
        "⚖\uFE0F   ",
        "\uD83C\uDF1E   ",
        "\uD83C\uDFAE   ",
        "\uD83D\uDC3B   ",
        "\uD83C\uDF34   ",
        "\uD83C\uDF54   ",
        "\uD83C\uDFA8   ",
        "\uD83D\uDCDC   ",
        "\uD83D\uDC57   ",
        "\uD83D\uDE37   ",
        "⚔\uFE0F   "
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
    ) {
        Text(
            text = "Categories",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            color = BlackPrimary,
            fontSize = 24.sp,
        )
        Text(
            modifier = Modifier
                .padding(vertical = 8.dp),
            text = "Thousands of articles in each category",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            color = GreyPrimary,
            fontSize = 16.sp,
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(items = categories) { category ->
                CategoryItem(
                    emoji = emojiList[categories.indexOf(category)],
                    category = category,
                    isSelected = viewModel.isSelectCheck(category),
                    onCategoryClicked = { viewModel.toggleCategory(category) }
                )
            }
        }
    }
}

@Composable
fun CategoryItem(
    emoji: String,
    category: String,
    isSelected: Boolean,
    onCategoryClicked: (String) -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = {
            onCategoryClicked(category)
        },
        border = BorderStroke(1.dp, GreyLighter),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(if (isSelected) PurplePrimary else Color.White),
    ) {
        Text(
            text = emoji + category,
            color = if (isSelected) Color.White else GreyDark,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    CategoryScreen()
}
