@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newstoday.presentation.theme.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.ContainerBox
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newstoday.R
import com.example.newstoday.presentation.theme.ui.GreyLighter
import com.example.newstoday.presentation.theme.ui.GreyPrimary
import com.example.newstoday.presentation.theme.ui.PurplePrimary


@Composable
fun HomeScreen() {
    val searchText = remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(GreyLighter) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(
            "Browse",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 70.dp, start = 20.dp),
            lineHeight = 32.sp
        )
        Text(
            "Discover things of this world", color = GreyPrimary, fontSize = 16.sp,
            modifier = Modifier.padding(top = 5.dp, start = 20.dp),
            lineHeight = 24.sp
        )

        OutlinedTextField(
            "",
            onValueChange = { searchText.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 40.dp),
            shape = RoundedCornerShape(12.dp),

            placeholder = { Text("Search", color = GreyPrimary) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_search_24), "",
                    tint = GreyPrimary
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = GreyLighter,
                unfocusedContainerColor = GreyLighter,
                disabledContainerColor = GreyLighter,
                errorContainerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,

                )
        )
        val categoryList = listOf("Random", "Apple", "Tesla", "Business", "TechCrunch", "News")
        LazyRow(modifier = Modifier.padding(top = 20.dp)) {
            items(categoryList) { category ->
                Button(
                    onClick = {
                        buttonColor = PurplePrimary
                    }, colors = ButtonDefaults.buttonColors(buttonColor),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp)
                        .padding(end = 8.dp, start = 8.dp),
                    elevation = ButtonDefaults.buttonElevation(5.dp)
                ) {
                    Text(category, color = GreyPrimary, fontSize = 10.sp)
                }


            }
        }
        ScrollNews()
    }
}


@Composable
fun ScrollNews() {
    Column(modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(start = 20.dp)) {
        LazyRow(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .height(256.dp)
        ) {
            items(5) {
                AsyncImage(
                    "", "",
                )
            }

        }
        Spacer(modifier = Modifier.padding(30.dp))
        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                "Recommended for you",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "See All",
                fontSize = 14.sp,
                color = GreyPrimary,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
