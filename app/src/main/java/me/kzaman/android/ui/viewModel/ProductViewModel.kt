package me.kzaman.android.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.kzaman.android.base.BaseViewModel
import me.kzaman.android.data.response.ProductResponse
import me.kzaman.android.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kzaman.android.database.entities.ProductEntities
import me.kzaman.android.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val repository: ProductRepository,
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

    fun saveProductToLocal(products: ArrayList<ProductEntities>) = viewModelScope.launch {
        repository.saveLocalProducts(products)
    }
}