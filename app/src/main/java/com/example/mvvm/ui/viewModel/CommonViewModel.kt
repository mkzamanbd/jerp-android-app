package com.example.mvvm.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.base.BaseViewModel
import com.example.mvvm.data.response.DefaultResponse
import com.example.mvvm.data.response.ProductResponse
import com.example.mvvm.data.response.UserResponse
import com.example.mvvm.network.Resource
import com.example.mvvm.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(
    val repository: CommonRepository
) : BaseViewModel(repository) {

    private val _users: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val users: LiveData<Resource<ProductResponse>> = _users

    private val _userDetail: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    val userDetail: LiveData<Resource<DefaultResponse>> = _userDetail

    fun getUsers() = viewModelScope.launch {
        _users.value = Resource.Loading
        _users.value = repository.getAllUsers()
    }

    fun getUserDetail(userId: String) = viewModelScope.launch {
        _userDetail.value = Resource.Loading
        _userDetail.value = repository.getUserDetail(userId)
    }
}