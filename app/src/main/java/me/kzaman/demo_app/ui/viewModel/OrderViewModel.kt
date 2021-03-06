package me.kzaman.demo_app.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import me.kzaman.demo_app.base.BaseViewModel
import me.kzaman.demo_app.data.response.ProductResponse
import me.kzaman.demo_app.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.kzaman.demo_app.data.model.CustomerModel
import me.kzaman.demo_app.data.model.ProductInfo
import me.kzaman.demo_app.data.response.DefaultResponse
import me.kzaman.demo_app.data.response.OrderResponse
import me.kzaman.demo_app.database.entities.CartItemsEntities
import me.kzaman.demo_app.database.entities.ProductEntities
import me.kzaman.demo_app.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val repository: OrderRepository,
) : BaseViewModel(repository) {

    companion object {
        @JvmField
        var cartItemCounter = MutableLiveData<String?>()

        @JvmField
        var mlSubTotalPrice = MutableLiveData<String>()

        @JvmField
        var mlGrandTotal = MutableLiveData<String>()

        @JvmField
        var mlTotalDiscount = MutableLiveData<String>()

        @JvmField
        var mlSpecialDiscountAmt = MutableLiveData<Double>()

        @JvmField
        var mlGrossTotal = MutableLiveData<String>()

        @JvmField
        var mlTotalVat = MutableLiveData<String>()

        @JvmField
        var mlLineTotal = MutableLiveData<Double>()

        @JvmField
        var mlOrderSelected = MutableLiveData<Int>()

        /**
         * ...only display
         * ...display amount with comma
         */
        @JvmField
        var mlDisplaySubTotalPrice = MutableLiveData<String>()

        @JvmField
        var mlDisplayGrandTotal = MutableLiveData<String>()

        @JvmField
        var mlDisplayTotalDiscount = MutableLiveData<String>()

        @JvmField
        var mlDisplaySpecialDiscount = MutableLiveData<String>()

        @JvmField
        var mlDisplayGrossTotal = MutableLiveData<String>()

        @JvmField
        var mlDisplayTotalVat = MutableLiveData<String>()
    }

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

    suspend fun saveCartProducts(cartItemsEntities: CartItemsEntities) = viewModelScope.launch {
        repository.saveCartProducts(cartItemsEntities)
    }

    fun customerCartEmpty(customerId: String) = viewModelScope.launch {
        repository.customerCartEmpty(customerId)
    }

    private val _salesAreaList: MutableLiveData<Resource<DefaultResponse>> = MutableLiveData()
    val salesAreaList: LiveData<Resource<DefaultResponse>> = _salesAreaList
    fun getAreaListByUser(customerId: String) = viewModelScope.launch {
        _salesAreaList.value = Resource.Loading
        _salesAreaList.value = repository.getAreaListByUser(customerId)
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
        mlCustomerCurrentDue.value = if (customerModel?.currentDue != "0") {
            "Due: ${customerModel?.currentDue}"
        } else null
        mlCreditType.value = if (customerModel?.creditFlag == "Y") {
            try {
                if (customerModel.creditLimit == "0" || customerModel.creditLimit == "0.0") {
                    "Payment Type Credit"
                } else {
                    "Credit Limit : ${customerModel.creditLimit}"
                }
            } catch (e: Exception) {
                "Payment Type Credit"
            }
        } else {
            "Payment Type Cash"
        }
        mlCustomerPhone.value = customerModel?.phone.toString()
        Log.d("customerModel", customerModel.toString())
    }

    /**
     * ...get only unselected product list from all product list
     * ...remove all selected product from allProduct list
     * @param selectedProduct product list
     * @param allProducts product list
     */
    fun getOnlyUnselectedProducts(
        allProducts: ArrayList<ProductInfo>,
        selectedProduct: List<ProductInfo>,
    ): List<ProductInfo> {
        for (product in selectedProduct) {
            for (item in allProducts) {
                val position = allProducts.indexOfFirst {
                    it.productId == product.productId
                }
                allProducts[position] = product
            }
        }
        return allProducts
    }

    private val _cartItems: MutableLiveData<CartItemsEntities> = MutableLiveData()
    val cartItems: LiveData<CartItemsEntities> = _cartItems
    fun getCustomerWiseCartItems(customerId: String) = viewModelScope.launch {
        _cartItems.value = repository.getCartItems(customerId)
    }

    private val _createOrderRequest: MutableLiveData<Resource<OrderResponse>> = MutableLiveData()
    val createOrderRequest: LiveData<Resource<OrderResponse>> = _createOrderRequest
    suspend fun createOrder(
        sbuId: String,
        customerId: String,
        salesAreaId: String,
        orderNote: String,
        orderDetail: String,
        deliveryAddress: String,
        orderDate: String,
    ) = viewModelScope.launch {
        _createOrderRequest.value = Resource.Loading
        _createOrderRequest.value = repository.createOrder(
            sbuId,
            customerId,
            salesAreaId,
            orderNote,
            orderDetail,
            deliveryAddress,
            orderDate
        )
    }
}