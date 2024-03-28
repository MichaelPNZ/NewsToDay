package com.example.newstoday.presentation.theme.favorite_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newstoday.R
import com.example.newstoday.navigation.NavigationItem.Account.icon

const val Kolvo = 2

@Preview(showBackground = true)
@Composable
fun FavoriteScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "Bookmarks", style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 32.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 20.dp)
        )
        Text(
            text = "Saved articles to the library", style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                lineHeight = 24.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp, start = 20.dp)
        )
        if (Kolvo > 0) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 130.dp, bottom = 16.dp, start = 32.dp, end = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(Kolvo) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .height(80.dp)
                            .clickable { /* Навигация к новости */ },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.littlebook),
                            contentDescription = "",
                            modifier = Modifier
                                .size(96.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Column(
                            modifier = Modifier.weight(1f), // Используйте вес, чтобы Column занимала оставшееся доступное пространство
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "UI/UX Design", style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W400,
                                    lineHeight = 20.sp,
                                    color = Color.Black
                                ),
                                modifier = Modifier
                                    .padding(top = 5.dp)
                            )
                            Text(
                                text = "A Simple Trick For Creating Color Palettes Quickly",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W600,
                                    lineHeight = 24.sp,
                                    color = Color.Black
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp)
                            )
                        }
                        Icon(
                            painterResource(id = R.drawable.favorite_icon),
                            contentDescription = "",
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .padding(bottom = 40.dp)
                                .clickable {  }
                        )
                    }

                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    drawCircle(
                        color = Color(0xFFEEF0FB),
                        radius = 32.dp.toPx()
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.littlebook),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center)

                )
                Text(
                    text = "You haven't saved any articles yet. Start reading and bookmarking them now",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 24.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(top = 200.dp, start = 60.dp, end = 60.dp)

                )
            }

        }
    }

}
