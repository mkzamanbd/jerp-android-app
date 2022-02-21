package me.kzaman.android.ui.viewModel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import me.kzaman.android.base.BaseViewModel
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    val repository: ProductRepository,
) : BaseViewModel(repository) {

    var mlCustomerName = MutableLiveData<String>()
    var mlCustomerCode = MutableLiveData<String>()
    var mlCustomerBusinessUnit = MutableLiveData<String>()
    var mlCustomerId = MutableLiveData<String>()
    var mlCustomerTblCompositeKey = MutableLiveData<String>()
    var mlCustomerPhone = MutableLiveData<String>()
    var mlCustomerEmail = MutableLiveData<String>()
    var mlCustomerAddress = MutableLiveData<String>()
    var mlDeliveryCustomerAddress = MutableLiveData<String>()
    var mlCreditType = MutableLiveData<String>()
    var mlCustomerImage = MutableLiveData<String>()

    fun displayCustomerInfo(customerModel: CustomerModel?) {
        mlCustomerName.value = customerModel?.customerName
        Log.d("customerModel", customerModel.toString())
    }

    fun customerImageView(view: View) {
        Log.d("Clicked", "customerImageView Clicked")
        Toast.makeText(view.context, "mm", Toast.LENGTH_SHORT).show()
    }

}