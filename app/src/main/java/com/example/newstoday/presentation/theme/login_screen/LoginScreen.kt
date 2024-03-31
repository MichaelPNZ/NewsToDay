package com.example.newstoday.presentation.theme.login_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstoday.R
import com.example.newstoday.domain.model.Article
import com.example.newstoday.presentation.theme.ui.GreyDark
import com.example.newstoday.presentation.theme.ui.GreyLight
import com.example.newstoday.presentation.theme.ui.GreyLighter
import com.example.newstoday.presentation.theme.ui.GreyPrimary
import com.example.newstoday.presentation.theme.ui.Pink40
import com.example.newstoday.presentation.theme.ui.PurpleDark
import com.example.newstoday.presentation.theme.ui.PurpleLight
import com.example.newstoday.presentation.theme.ui.PurplePrimary
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
    navigateToHome: () -> Unit,
    navigateToOnboarding: () ->Unit
) {

    var isLogin by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PurpleDark)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(top = 150.dp)
                .offset(x = (-50).dp),
            painter = painterResource(id = R.drawable.news),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .padding(top = 20.dp)
                .offset(x = 30.dp),
            painter = painterResource(id = R.drawable.to_day),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.padding(30.dp))

        if (isLogin) {
            Login(
                loginScreenViewModel = loginScreenViewModel,
                navigateToHome = navigateToHome
            ) { isLogin = false }
        } else {
            Register(
                loginScreenViewModel = loginScreenViewModel,
                navigateToHome = navigateToHome,
                navigateToOnboarding = navigateToOnboarding
            )
        }
    }
}

@Composable
fun Login(
    loginScreenViewModel: LoginScreenViewModel,
    navigateToHome: () -> Unit,
    onCreateAccountClick: () -> Unit,
) {
    var loginQuery by rememberSaveable { mutableStateOf("") }
    var passwordQuery by rememberSaveable { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(PurpleDark, RoundedCornerShape(16.dp))
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = loginQuery,
            onValueChange = {loginQuery = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Login", color = GreyPrimary) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.person_icon), "",
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

        OutlinedTextField(
            value = passwordQuery,
            onValueChange = { passwordQuery = it},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Password", color = GreyPrimary) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icons8_key), "",
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
    }
    Spacer(modifier = Modifier.padding(15.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 16.dp),
        onClick = {
            loginScreenViewModel.viewModelScope.launch {
                if (loginScreenViewModel.checkUser(loginQuery, passwordQuery)) {
                    navigateToHome()
                } else {
                    showErrorDialog = true
                }
            }

        },
        border = BorderStroke(1.dp, GreyLighter),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(PurplePrimary),
    ) {
        Text(
            text = "Login",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = "Don't have an account? ",
            color = GreyLighter,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Text(
            modifier = Modifier.clickable { onCreateAccountClick() },
            text = "Create here",
            color = PurpleLight,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }

    if (showErrorDialog) {
        MinimalDialog("The username or password\n" +
                    " is entered incorrectly,\n" +
                    " or there is no such user"
        ) {
            showErrorDialog = false // Закрываем диалог при нажатии на кнопку
        }
    }
}

@Composable
fun Register(
    loginScreenViewModel: LoginScreenViewModel,
    navigateToHome: () -> Unit,
    navigateToOnboarding: () -> Unit
) {
    var nameQuery by rememberSaveable { mutableStateOf("") }
    var loginQuery by rememberSaveable { mutableStateOf("") }
    var passwordQuery by rememberSaveable { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(PurpleDark, RoundedCornerShape(16.dp))
            .padding(vertical = 20.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        OutlinedTextField(
            value = nameQuery,
            onValueChange = { nameQuery = it},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Name", color = GreyPrimary) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.person_icon), "",
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

        OutlinedTextField(
            value = loginQuery,
            onValueChange = { loginQuery = it},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Email", color = GreyPrimary) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icons8_mailbox), "",
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

        OutlinedTextField(
            value = passwordQuery,
            onValueChange = { passwordQuery = it},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            placeholder = { Text("Password", color = GreyPrimary) },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.icons8_key), "",
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
    }
    Spacer(modifier = Modifier.padding(15.dp))
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .padding(horizontal = 16.dp),
        onClick = {
            loginScreenViewModel.viewModelScope.launch {
                if (!loginScreenViewModel.checkUser(loginQuery, passwordQuery)) {
                    loginScreenViewModel.saveUser(nameQuery, loginQuery, passwordQuery)
                    navigateToOnboarding()
                } else {
                    showErrorDialog = true
                }
            }
        },
        border = BorderStroke(1.dp, GreyLighter),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(PurplePrimary),
    ) {
        Text(
            text = "Register",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }

    if (showErrorDialog) {
        MinimalDialog("Such a user already exists!"
        ) {
            showErrorDialog = false // Закрываем диалог при нажатии на кнопку
        }
    }
}

@Composable
fun MinimalDialog(
    text: String,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            colors = CardColors(
                containerColor = GreyLighter,
                contentColor = Pink40,
                disabledContainerColor = GreyLighter,
                disabledContentColor = Color.White),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
    }
}
