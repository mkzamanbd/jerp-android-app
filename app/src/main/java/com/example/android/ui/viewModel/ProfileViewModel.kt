package com.example.android.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.base.BaseViewModel
import com.example.android.data.response.ProfileResponse
import com.example.android.network.Resource
import com.example.android.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
) : BaseViewModel(repository) {

    private val _profile: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()
    val profile: LiveData<Resource<ProfileResponse>>
        get() = _profile

    fun getUser() = viewModelScope.launch {
        _profile.value = Resource.Loading
        _profile.value = repository.getUserProfile()
    }
}