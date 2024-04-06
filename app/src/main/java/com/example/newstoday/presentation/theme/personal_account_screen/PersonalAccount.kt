@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newstoday.presentation.theme.personal_account_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.newstoday.R
import com.example.newstoday.presentation.theme.auth.UserData
import com.example.newstoday.presentation.theme.ui.BlackPrimary
import com.example.newstoday.presentation.theme.ui.GreyDark
import com.example.newstoday.presentation.theme.ui.GreyLighter
import com.example.newstoday.presentation.theme.ui.GreyPrimary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalAccountScreen(
    personalAccountViewModel: PersonalAccountViewModel = hiltViewModel(),
    userData: UserData?,
    onSignOutClick: () -> Unit,
    navigateToLanguageScreen: () ->Unit,
    navigateToLoginScreen: ()->Unit
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    var bottomSheetText by remember { mutableStateOf("") }

    val viewModel = personalAccountViewModel.user.value ?: return

    BottomSheetScaffold(
        sheetContent = {
            Text(
                text = bottomSheetText,
                modifier = Modifier
                    .fillMaxSize(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 50.dp, start = 15.dp, end = 15.dp), fontSize = 19.sp
            )

        },
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContentColor = GreyPrimary,
        sheetContainerColor =Color.White
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.Profile),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.padding(16.dp))
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxWidth(),
            ) {

                if (userData?.profilePictureUrl != null) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Log.i("!!!", "AsyncImage ${userData.profilePictureUrl}")
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.cat_image),
                        contentDescription = "Иконка пользователя",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(72.dp)
                    )
                }
                if (userData?.userName != null) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = userData.userName,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlackPrimary
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = viewModel.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = BlackPrimary
                        )
                        Text(
                            text = viewModel.email,
                            fontSize = 14.sp,
                            color = GreyPrimary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.padding(30.dp))
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ButtonGrayWithIcon(text = stringResource(id = R.string.lang),
                    icon = Icons.Default.KeyboardArrowRight,
                    onClick = navigateToLanguageScreen)
                Column {
                    ButtonGrayWithIcon(
                        text = stringResource(id = R.string.Terms_Conditions),
                        icon = Icons.Default.KeyboardArrowRight,
                        onClick = {
                            bottomSheetText = termsText
                            scope.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    )
                    ButtonGrayWithIcon(text = stringResource(id = R.string.Sign_Out),
                        icon = Icons.Default.ExitToApp
                    ) {
                        personalAccountViewModel.signOut()
                        onSignOutClick()
                        navigateToLoginScreen()
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}

@Composable
fun ButtonGrayWithIcon(text:String,
                       icon: ImageVector,
                       onClick: () ->Unit){
    Button(
        onClick = {onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = ButtonColors(
            GreyLighter,
            GreyDark,
            GreyLighter,
            GreyDark
        ),
        shape = RoundedCornerShape(12.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = text,
                fontSize = 16.sp)
            Icon(imageVector = icon,
                contentDescription = null,
                modifier = Modifier.wrapContentSize())
        }

    }

}

const val termsText = "Acceptance of Terms\n" +
        "By downloading, accessing, or using the news application, you agree to be bound by these Terms and Conditions.\n" +
        "\n" +
        "User Conduct\n" +
        "You agree not to use the news application for any unlawful or harmful purposes, including but not limited to spreading hate speech, promoting violence, or infringing copyright laws.\n" +
        "\n" +
        "Accuracy of Information\n" +
        "The news application provides news articles from various sources and strives to ensure the accuracy and credibility of the information. However, we do not guarantee the accuracy of all content and do not take responsibility for any misinformation.\n" +
        "\n" +
        "Intellectual Property\n" +
        "All trademarks, logos, and content on the news application are the property of their respective owners. You agree not to reproduce, modify, or distribute any content without prior consent.\n" +
        "\n" +
        "Privacy Policy\n" +
        "We respect your privacy and are committed to protecting your personal information. Please refer to our Privacy Policy for more details on how we collect, use, and disclose your data.\n" +
        "\n" +
        "Changes to Terms\n" +
        "We reserve the right to modify or update these Terms and Conditions at any time. It is your responsibility to review these changes periodically.\n" +
        "\n" +
        "Disclaimer\n" +
        "The news application is provided \"as is\" and we make no warranties or guarantees regarding its availability, accuracy, or performance. We are not liable for any damages or losses incurred from the use of the application.\n" +
        "\n" +
        "Contact Information\n" +
        "If you have any questions or concerns about these Terms and Conditions, please contact us at [email protected]"