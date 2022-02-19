package me.kzaman.android.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.kzaman.android.base.BaseViewModel
import me.kzaman.android.data.response.ProfileResponse
import me.kzaman.android.network.Resource
import me.kzaman.android.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kzaman.android.database.AppDatabase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val appDatabase: AppDatabase
) : BaseViewModel(repository) {

    private val _profile: MutableLiveData<Resource<ProfileResponse>> = MutableLiveData()
    val profile: LiveData<Resource<ProfileResponse>> = _profile

    fun getUserProfile() = viewModelScope.launch {
        _profile.value = Resource.Loading
        _profile.value = repository.getUserProfile()
    }

    fun clearAllTable() = viewModelScope.launch {
        // clear menu table
        appDatabase.menuDao().deleteMenuTable()
        appDatabase.menuDao().deleteSubMenuTable()
        // clear product table
        appDatabase.productDao().deleteProductTable()
        // clear customer table
        appDatabase.customerDao().deleteAllCustomers()
    }
}