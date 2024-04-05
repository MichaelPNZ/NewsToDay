package com.example.newstoday.presentation.theme.category_screen_first_entry

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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newstoday.R
import com.example.newstoday.presentation.theme.category_screen_tabBar.CategoryItem
import com.example.newstoday.presentation.theme.category_screen_tabBar.CategoryViewModel
import com.example.newstoday.presentation.theme.ui.BlackPrimary
import com.example.newstoday.presentation.theme.ui.GreyPrimary
import com.example.newstoday.presentation.theme.ui.PurplePrimary

@Composable
fun CategoryScreenFirstEntry(
    categoryViewModel: CategoryViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Text(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .fillMaxWidth(),
            text = stringResource(id = R.string.Select_your_favorite_topics),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.SemiBold,
            color = BlackPrimary,
            fontSize = 24.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
            text = stringResource(id = R.string.Select_some),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Normal,
            color = GreyPrimary,
            fontSize = 16.sp,
        )
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(items = categoryViewModel.categoriesList) { category ->
                CategoryItem(
                    emoji = categoryViewModel.emojiList[categoryViewModel.categoriesList.indexOf(category)],
                    category = category,
                    isSelected = categoryViewModel.isSelectCheck(category),
                    onCategoryClicked = { categoryViewModel.toggleCategory(category) }
                )
            }
        }
        Button(onClick = { navigateToHomeScreen() },
            modifier = Modifier
                .height(56.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonColors(
                containerColor = PurplePrimary,
                contentColor = Color.White,
                disabledContainerColor = PurplePrimary,
                disabledContentColor =Color.White)
        ) {
            Text(text = stringResource(id = R.string.Next),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}