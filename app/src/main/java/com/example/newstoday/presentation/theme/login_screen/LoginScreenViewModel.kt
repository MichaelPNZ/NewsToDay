package com.example.newstoday.presentation.theme.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstoday.domain.model.User
import com.example.newstoday.domain.usecases.GetAllUsersUseCase
import com.example.newstoday.domain.usecases.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : ViewModel() {

    suspend fun checkUser(email: String, password: String): Boolean {
        return suspendCoroutine { continuation ->
            viewModelScope.launch {
                val user = getAllUsersUseCase()
                val isUserExists = user.find { it.email == email && it.password == password } != null
                continuation.resume(isUserExists)
            }
        }
    }

    fun saveIsLoginStatus(email: String) {
        viewModelScope.launch {
            val userList = getAllUsersUseCase()
            val user = userList.find { it.email == email }
            val otherUsers = userList - user
            if (user != null) {
                saveUserUseCase(user.copy(isLogin = true))
                otherUsers.forEach {
                    if (it != null) {
                        saveUserUseCase(it.copy(isLogin = false))
                    }
                }
            }
        }
    }

    fun saveUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            val userList = getAllUsersUseCase()
            userList.forEach {
                saveUserUseCase(it.copy(isLogin = false))
            }
            saveUserUseCase(
                User(
                    id = if (userList.isEmpty()) 1 else userList.last().id + 1,
                    name = name,
                    email = email,
                    password = password,
                    favoriteCategories = emptyList(),
                    articles = emptyList(),
                    isLogin = true
                ))
        }
    }
}