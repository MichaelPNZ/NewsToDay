package com.example.newstoday.presentation.theme.personal_account_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newstoday.domain.model.User
import com.example.newstoday.domain.usecases.GetIsLoginUserUseCase
import com.example.newstoday.domain.usecases.SaveUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonalAccountViewModel @Inject constructor(
    private val getIsLoginUserUseCase: GetIsLoginUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val user = getIsLoginUserUseCase()
            user.let { _user.value = it }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            val user = getIsLoginUserUseCase()
            saveUserUseCase(user.copy(isLogin = false))
        }
    }
}