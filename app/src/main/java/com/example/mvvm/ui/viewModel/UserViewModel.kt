package com.example.mvvm.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.base.BaseViewModel
import com.example.mvvm.data.response.UserResponse
import com.example.mvvm.network.Resource
import com.example.mvvm.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val repository: UserRepository
) : BaseViewModel(repository) {

    private val _users: MutableLiveData<Resource<UserResponse>> = MutableLiveData()
    val users: LiveData<Resource<UserResponse>>
        get() = _users

    fun getUsers() = viewModelScope.launch {
        _users.value = Resource.Loading
        _users.value = repository.getAllUsers()
    }
}