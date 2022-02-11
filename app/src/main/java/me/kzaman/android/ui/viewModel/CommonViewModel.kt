package me.kzaman.android.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.kzaman.android.base.BaseViewModel
import me.kzaman.android.data.response.DefaultResponse
import me.kzaman.android.data.response.MobileMenuResponse
import me.kzaman.android.data.response.ProductResponse
import me.kzaman.android.network.Resource
import me.kzaman.android.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(
    val repository: CommonRepository
) : BaseViewModel(repository) {

    private val _products: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val products: LiveData<Resource<ProductResponse>> = _products

    fun getAllProducts() = viewModelScope.launch {
        _products.value = Resource.Loading
        _products.value = repository.getAllProducts()
    }

    private val _userDetail: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    val userDetail: LiveData<Resource<DefaultResponse>> = _userDetail

    fun getUserDetail(userId: String) = viewModelScope.launch {
        _userDetail.value = Resource.Loading
        _userDetail.value = repository.getUserDetail(userId)
    }

    private val _mobileMenu: MutableLiveData<Resource<MobileMenuResponse>> = MutableLiveData()
    val mobileMenu: LiveData<Resource<MobileMenuResponse>> = _mobileMenu

    fun getMobileMenu() = viewModelScope.launch {
        _mobileMenu.value = Resource.Loading
        _mobileMenu.value = repository.getMobileMenu()
    }
}