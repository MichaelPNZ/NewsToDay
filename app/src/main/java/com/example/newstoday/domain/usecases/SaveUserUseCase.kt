package com.example.newstoday.domain.usecases

import com.example.newstoday.domain.model.User
import com.example.newstoday.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(user: User) = repository.saveUser(user)
}