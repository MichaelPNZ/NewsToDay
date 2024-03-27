package com.example.newstoday.domain.usecases

import com.example.newstoday.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByEmailUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(email: String) = repository.getUserByEmail(email)
}