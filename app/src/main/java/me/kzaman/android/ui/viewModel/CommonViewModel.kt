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
import me.kzaman.android.database.entities.SubMenuEntities
import me.kzaman.android.database.entities.MenuEntities
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

    fun saveParentMenuToLocalDb(parentMenuEntities: ArrayList<MenuEntities>) =
        viewModelScope.launch {
            repository.saveParentMenuLocalDb(parentMenuEntities)
        }

    fun saveSubMenuToLocalDb(subMenuEntities: ArrayList<SubMenuEntities>) =
        viewModelScope.launch {
            repository.saveSubMenuLocalDb(subMenuEntities)
        }

    private val _parentMenu: MutableLiveData<List<MenuEntities>> = MutableLiveData()
    val parentMenuLocal: LiveData<List<MenuEntities>> = _parentMenu

    private val _subMenu: MutableLiveData<List<SubMenuEntities>> = MutableLiveData()
    val subMenuLocal: LiveData<List<SubMenuEntities>> = _subMenu

    fun getMenuLocalDb() = viewModelScope.launch {
        _parentMenu.value = repository.getParentMenuLocalDb()
        _subMenu.value = repository.getSubMenuLocalDb()
    }

}