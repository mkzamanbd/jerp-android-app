package me.kzaman.android.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.kzaman.android.base.BaseViewModel
import me.kzaman.android.data.response.MobileMenuResponse
import me.kzaman.android.network.Resource
import me.kzaman.android.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kzaman.android.database.entities.HomeChildMenuEntities
import me.kzaman.android.database.entities.HomeParentMenuEntities
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(
    val repository: CommonRepository,
) : BaseViewModel(repository) {

    private val _mobileMenu: MutableLiveData<Resource<MobileMenuResponse>> = MutableLiveData()
    val mobileMenu: LiveData<Resource<MobileMenuResponse>> = _mobileMenu

    fun getMobileMenu() = viewModelScope.launch {
        _mobileMenu.value = Resource.Loading
        _mobileMenu.value = repository.getMobileMenu()
    }

    fun saveHomeParentMenuToLocalDb(parentMenuEntities: ArrayList<HomeParentMenuEntities>) =
        viewModelScope.launch {
            repository.saveParentMenuLocalDb(parentMenuEntities)
        }

    fun saveHomeChildMenuToLocalDb(homeChildMenuEntities: ArrayList<HomeChildMenuEntities>) =
        viewModelScope.launch {
            repository.saveChildMenuLocalDb(homeChildMenuEntities)
        }

    private val _parentMenu: MutableLiveData<List<HomeParentMenuEntities>> = MutableLiveData()
    val parentMenuLocal: LiveData<List<HomeParentMenuEntities>> = _parentMenu

    private val _childMenu: MutableLiveData<List<HomeChildMenuEntities>> = MutableLiveData()
    val childMenuLocal: LiveData<List<HomeChildMenuEntities>> = _childMenu

    fun getMobileMenuLocalDb() = viewModelScope.launch {
        _parentMenu.value = repository.getParentMenuLocalDb()
        _childMenu.value = repository.getChildMenuLocalDb()
    }

}