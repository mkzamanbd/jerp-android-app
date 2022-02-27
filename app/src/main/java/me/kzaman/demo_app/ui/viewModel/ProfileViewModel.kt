package me.kzaman.demo_app.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.kzaman.demo_app.base.BaseViewModel
import me.kzaman.demo_app.data.response.ProfileResponse
import me.kzaman.demo_app.network.Resource
import me.kzaman.demo_app.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kzaman.demo_app.database.AppDatabase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
    private val appDatabase: AppDatabase,
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
        // clear order table
        appDatabase.orderDao().deleteProductTable()
        // delete cart table
        appDatabase.orderDao().deleteCartsTable()
        // clear customer table
        appDatabase.customerDao().deleteAllCustomers()
    }
}