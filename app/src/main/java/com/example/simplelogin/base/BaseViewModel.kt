package com.example.simplelogin.base

import androidx.lifecycle.ViewModel
import com.example.simplelogin.network.UserApi

abstract class BaseViewModel(
    private val repository: BaseRepository
) :ViewModel() {

    suspend fun logout(api: UserApi) = repository.logout(api)
}