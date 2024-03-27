package com.example.newstoday.domain.usecases

import com.example.newstoday.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase  @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.getAllUser()
}