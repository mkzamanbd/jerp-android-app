package me.kzaman.android.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.kzaman.android.base.BaseViewModel
import me.kzaman.android.data.response.ProductResponse
import me.kzaman.android.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.database.entities.ProductEntities
import me.kzaman.android.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val repository: OrderRepository,
) : BaseViewModel(repository) {

    private val _products: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val products: LiveData<Resource<ProductResponse>> = _products

    fun getRemoteProducts() = viewModelScope.launch {
        _products.value = Resource.Loading
        _products.value = repository.getRemoteProducts()
    }

    private val _localProducts: MutableLiveData<List<ProductEntities>> = MutableLiveData()
    val localProducts: LiveData<List<ProductEntities>> = _localProducts

    fun getLocalProducts() = viewModelScope.launch {
        _localProducts.value = repository.getLocalProducts()
    }

    suspend fun saveProductToLocal(products: ArrayList<ProductEntities>) = viewModelScope.launch {
        repository.saveLocalProducts(products)
    }

    var mlCustomerName = MutableLiveData<String>()
    var mlCustomerCode = MutableLiveData<String>()
    var mlCustomerBusinessUnit = MutableLiveData<String>()
    var mlCustomerId = MutableLiveData<String>()
    var mlCustomerTblCompositeKey = MutableLiveData<String>()
    var mlCustomerPhone = MutableLiveData<String>()
    var mlCustomerEmail = MutableLiveData<String>()
    var mlCustomerAddress = MutableLiveData<String?>()
    var mlDeliveryCustomerAddress = MutableLiveData<String>()
    var mlCreditType = MutableLiveData<String>()
    var mlCustomerImage = MutableLiveData<String>()
    var mlCustomerCurrentDue = MutableLiveData<String>()

    fun displayCustomerInfo(customerModel: CustomerModel?) {
        mlCustomerName.value = customerModel?.customerName
        mlCustomerAddress.value = customerModel?.customerAddress
        mlCustomerCurrentDue.value = if(customerModel?.currentDue != "0"){
            "Due: ${customerModel?.currentDue}"
        } else null
        Log.d("customerModel", customerModel.toString())
    }
}