package com.paxcreation.weatherapp.domain.usecase

import com.paxcreation.weatherapp.domain.repo.UserRepo
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor(
       val userRepo: UserRepo
) {
}