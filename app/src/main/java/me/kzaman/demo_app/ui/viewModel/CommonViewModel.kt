package me.kzaman.demo_app.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.sqlite.db.SimpleSQLiteQuery
import me.kzaman.demo_app.base.BaseViewModel
import me.kzaman.demo_app.data.response.MobileMenuResponse
import me.kzaman.demo_app.network.Resource
import me.kzaman.demo_app.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kzaman.demo_app.data.response.DefaultResponse
import me.kzaman.demo_app.database.entities.CustomerCartEntities
import me.kzaman.demo_app.database.entities.CustomerEntities
import me.kzaman.demo_app.database.entities.SubMenuEntities
import me.kzaman.demo_app.database.entities.MenuEntities
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor(
    val repository: CommonRepository,
) : BaseViewModel(repository) {

    /*
    * get mobile menu
    */
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

    /*
    * all customer
    */

    private val _customers: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    val customers: LiveData<Resource<DefaultResponse>> = _customers

    fun getAllRemoteCustomer() = viewModelScope.launch {
        _customers.value = Resource.Loading
        _customers.value = repository.getAllRemoteCustomer()
    }

    suspend fun saveCustomersLocalDb(customerEntities: ArrayList<CustomerEntities>) =
        viewModelScope.launch {
            repository.saveCustomersLocalDb(customerEntities)
        }

    private val _mlLocalCustomers: MutableLiveData<List<CustomerEntities>> = MutableLiveData()
    val mlLocalCustomers: LiveData<List<CustomerEntities>> = _mlLocalCustomers

    fun getCustomersLocalDb() = viewModelScope.launch {
        _mlLocalCustomers.value = repository.getAllCustomersLocalDb()
    }

    private val _localCustomers: MutableLiveData<List<CustomerCartEntities>> = MutableLiveData()
    val localCustomers: LiveData<List<CustomerCartEntities>> = _localCustomers
    fun getAllCustomersLocalDb(customerType: String? = null, paymentType: String? = null) =
        viewModelScope.launch {
            var whereCondition = ""
            if (customerType == "422" || customerType == "424") {
                whereCondition = "WHERE customer_type = '$customerType'"
            }

            if (paymentType != "") {
                whereCondition = if (whereCondition != "") {
                    "$whereCondition AND credit_flag = '$paymentType'"
                } else {
                    "WHERE credit_flag = '$paymentType'"
                }
            }

            val sqlQuery =
                "SELECT customer.*, cart.cart_json FROM customers customer LEFT JOIN carts cart ON cart.customer_id = customer.composite_key $whereCondition"
            val query = SimpleSQLiteQuery(sqlQuery)
            _localCustomers.value = repository.getAllCustomersLocalDb(query)
        }

}