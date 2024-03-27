@file:OptIn(ExperimentalFoundationApi::class)

package com.example.newstoday.presentation.theme.onboarding_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newstoday.R
import com.example.newstoday.presentation.theme.ui.PurplePrimary

@Composable
fun OnboardingScreen(navigateToCategoryScreen: ()-> Unit){
    val state = rememberPagerState(pageCount = { images.size })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorPage(state)
        DotsIndicator(totalDots = state.pageCount, selectedIndex = state.currentPage )
        Spacer(modifier = Modifier.padding(14.dp))
        Text(text =
            if(state.currentPage == 0){
                firstText
            }
            else{
                " "
            }
        ,
            fontSize = 24.sp,)
        Spacer(modifier = Modifier.padding(14.dp))
        Text(text = allText,
            fontSize = 16.sp,
            color = Color.Gray)
        Spacer(modifier = Modifier.padding(30.dp))
        if(state.currentPage == 2){
            Button(onClick = {
                navigateToCategoryScreen()
            },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = PurplePrimary,
                    contentColor = Color.White,
                    disabledContainerColor = PurplePrimary,
                    disabledContentColor =Color.White)
                ) {
                Text(text = "Get Started",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold)
            }
        }
    }

}

@Composable
fun HorPage(state: PagerState){

    HorizontalPager(state = state) { page ->
        Column(
        ) {
            Spacer(modifier = Modifier.padding(60.dp))
            Box(Modifier.fillMaxWidth(),contentAlignment = Alignment.TopCenter) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(horizontal = 30.dp)
                        .size(280.dp, 360.dp)
                        .clip(RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.padding(15.dp))
        }
    }
}
@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), horizontalArrangement = Arrangement.Center
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(24.dp,10.dp)
                        .clip(CircleShape)
                        .background(color = PurplePrimary)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = Color.LightGray)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingScreenPreeview(){
    OnboardingScreen({})
}
val images = listOf(
    R.drawable.image_text,
    R.drawable.image_text,
    R.drawable.image_text
)
val firstText ="First to know"
val allText = "All news in one place, be\nthe first to know last news"
