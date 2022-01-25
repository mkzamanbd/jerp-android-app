package com.example.simplelogin.ui.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simplelogin.base.BaseViewModel
import com.example.simplelogin.data.response.LoginResponse
import com.example.simplelogin.network.Resource
import com.example.simplelogin.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
) : BaseViewModel(repository) {


    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse


    fun userLogin(
        email: String,
        password: String,
    ) = viewModelScope.launch {
        _loginResponse.value = repository.login(email, password)
    }

    suspend fun saveAuthToken(token: String){
        repository.saveAuthToken(token)
    }
}