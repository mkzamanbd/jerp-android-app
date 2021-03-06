package me.kzaman.demo_app.ui.fragments.customer

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.demo_app.R
import me.kzaman.demo_app.adapter.CustomerListAdapter
import me.kzaman.demo_app.base.BaseFragment
import me.kzaman.demo_app.data.model.CustomerModel
import me.kzaman.demo_app.database.entities.CustomerEntities
import me.kzaman.demo_app.databinding.FragmentCustomerListBinding
import me.kzaman.demo_app.network.Resource
import me.kzaman.demo_app.ui.activities.CustomerActivity
import me.kzaman.demo_app.ui.viewModel.CommonViewModel
import me.kzaman.demo_app.utils.LoadingUtils
import me.kzaman.demo_app.utils.handleNetworkError
import me.kzaman.demo_app.utils.toastWarning
import me.kzaman.demo_app.utils.visible
import me.kzaman.demo_app.utils.hideSoftKeyboard
import me.kzaman.demo_app.utils.countJsonObject
import java.util.ArrayList


@AndroidEntryPoint
open class CustomerListFragment : BaseFragment<FragmentCustomerListBinding>() {
    lateinit var binding: FragmentCustomerListBinding
    private val viewModel by viewModels<CommonViewModel>()
    lateinit var customerListAdapter: CustomerListAdapter

    override val layoutId: Int = R.layout.fragment_customer_list

    private var customerType: String = ""
    private var paymentType: String = ""
    private var isCustomerFilter: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
        loadingUtils = LoadingUtils(mContext)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = viewDataBinding
        binding.lifecycleOwner = viewLifecycleOwner
        initializeApp()

        getCustomersList()

        binding.etCustomerSearch.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                binding.ivSearchClear.visible(false)
            } else {
                binding.ivSearchClear.visible(true)
            }
            customerListAdapter.filter.filter(it)
        }
        binding.ivSearchClear.setOnClickListener {
            binding.etCustomerSearch.text = null
            hideSoftKeyboard(mContext, binding.etCustomerSearch)
            it.visible(false)
        }

        binding.ivCustomerFilter.setOnClickListener {
            customerFilter()
        }
    }

    override fun initializeApp() {
        (activity as CustomerActivity).showToolbar(true) //display toolbar
        (activity as CustomerActivity).setToolbarTitle("Customers List")
        customerListAdapter = CustomerListAdapter(arrayListOf(), mContext)
        binding.rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }
    }

    private fun customerFilter() {
        val dialog = Dialog(mContext, R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        dialog.setContentView(R.layout.dialog_customer_filter)
        dialog.setCancelable(true)

        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.show()

        val closeBtn = dialog.findViewById<ImageView>(R.id.imageView)
        val filterBtn = dialog.findViewById<AppCompatButton>(R.id.button_filter)

        val customerTypeRadioGroup = dialog.findViewById<RadioGroup>(R.id.customer_type_group)
        val paymentTypeRadioGroup = dialog.findViewById<RadioGroup>(R.id.payment_type_group)

        when (customerType) {
            "422" -> {
                customerTypeRadioGroup.check(R.id.customer_type_chemist)
            }
            "424" -> {
                customerTypeRadioGroup.check(R.id.customer_type_institution)
            }
            else -> {
                customerTypeRadioGroup.check(R.id.customer_type_all)
            }
        }

        when (paymentType) {
            "Y" -> {
                paymentTypeRadioGroup.check(R.id.payment_method_credit)
            }
            "N" -> {
                paymentTypeRadioGroup.check(R.id.payment_method_cash)
            }
            else -> {
                paymentTypeRadioGroup.check(R.id.payment_method_all)
            }
        }

        customerTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.customer_type_all -> {
                    customerType = ""
                }
                R.id.customer_type_chemist -> {
                    customerType = "422"
                }
                R.id.customer_type_institution -> {
                    customerType = "424"
                }
            }
        }

        paymentTypeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.payment_method_all -> {
                    paymentType = ""
                }
                R.id.payment_method_credit -> {
                    paymentType = "Y"
                }
                R.id.payment_method_cash -> {
                    paymentType = "N"
                }
            }
        }

        filterBtn.setOnClickListener {
            viewModel.getAllCustomersLocalDb(customerType, paymentType)
            isCustomerFilter = true
            dialog.dismiss()
        }
        closeBtn.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun getCustomersList() {
        viewModel.getAllCustomersLocalDb(customerType, paymentType)
        viewModel.localCustomers.observe(viewLifecycleOwner) {
            if (it.isNotEmpty() || isCustomerFilter) {
                val customerModels: ArrayList<CustomerModel> = ArrayList()

                it.forEach { customerEntities ->
                    val totalCartCount = if (customerEntities.cartJson != null) {
                        countJsonObject(customerEntities.cartJson!!)
                    } else 0
                    
                    val item = CustomerModel(
                        sbuId = customerEntities.sbuId,
                        compositeKey = customerEntities.compositeKey,
                        customerId = customerEntities.customerId,
                        dsId = customerEntities.dsId ?: "",
                        salesAreaId = customerEntities.salesAreaId,
                        areaLevel = customerEntities.areaLevel,
                        customerType = customerEntities.customerType,
                        customerCode = customerEntities.customerCode,
                        displayCode = customerEntities.displayCode,
                        customerName = customerEntities.customerName,
                        creditFlag = customerEntities.creditFlag,
                        displayName = customerEntities.displayName ?: "",
                        creditLimit = customerEntities.creditLimit,
                        searchKey = customerEntities.searchKey,
                        vatChallaFlag = customerEntities.vatChallaFlag ?: "",
                        status = customerEntities.status,
                        currentDue = customerEntities.currentDue,
                        currentAdvance = customerEntities.currentAdvance,
                        phone = customerEntities.phone,
                        email = customerEntities.email,
                        customerAddress = customerEntities.customerAddress,
                        photo = customerEntities.photo,
                        territoryCode = customerEntities.territoryCode,
                        territoryName = customerEntities.territoryName,
                        paymentMode = customerEntities.paymentMode ?: "",
                        cashDueAmt = customerEntities.cashDueAmt,
                        activateVerifyDate = customerEntities.activateVerifyDate,
                        activateVerifyBy = customerEntities.activateVerifyBy,
                        hqType = customerEntities.hqType,
                        totalCartItem = totalCartCount
                    )
                    customerModels.add(item)
                }
                customerListAdapter.setCustomers(customerModels)
            } else {
                getRemoteCustomerList()
            }
        }
    }

    private fun getRemoteCustomerList() {
        viewModel.getAllRemoteCustomer()
        viewModel.customers.observe(viewLifecycleOwner) {
            loadingUtils.isLoading(it is Resource.Loading)

            when (it) {
                is Resource.Success -> {
                    val response = it.value
                    if (response.responseCode == 200) {
                        customerListAdapter.setCustomers(response.customerList)
                        saveCustomerLocalDb(response.customerList)
                    } else {
                        toastWarning(mActivity, "No customer found!")
                    }
                }
                is Resource.Failure -> handleNetworkError(it, mActivity)
                else -> Log.d("unknownError", "Unknown Error")
            }
        }
    }

    private fun saveCustomerLocalDb(customerList: List<CustomerModel>) {
        val customerItems: ArrayList<CustomerEntities> = ArrayList()

        customerList.forEach { customerModel ->
            val item = CustomerEntities(
                sbuId = customerModel.sbuId,
                compositeKey = customerModel.compositeKey,
                customerId = customerModel.customerId,
                dsId = customerModel.dsId,
                salesAreaId = customerModel.salesAreaId,
                areaLevel = customerModel.areaLevel,
                customerType = customerModel.customerType,
                customerCode = customerModel.customerCode,
                displayCode = customerModel.displayCode,
                customerName = customerModel.customerName,
                creditFlag = customerModel.creditFlag,
                displayName = customerModel.displayName,
                creditLimit = customerModel.creditLimit,
                searchKey = customerModel.searchKey,
                vatChallaFlag = customerModel.vatChallaFlag,
                status = customerModel.status,
                currentDue = customerModel.currentDue,
                currentAdvance = customerModel.currentAdvance,
                phone = customerModel.phone,
                email = customerModel.email,
                customerAddress = customerModel.customerAddress,
                photo = customerModel.photo,
                territoryCode = customerModel.territoryCode,
                territoryName = customerModel.territoryName,
                paymentMode = customerModel.paymentMode,
                cashDueAmt = customerModel.cashDueAmt,
                activateVerifyDate = customerModel.activateVerifyDate,
                activateVerifyBy = customerModel.activateVerifyBy,
                hqType = customerModel.hqType,
            )
            customerItems.add(item)
        }

        lifecycleScope.launch {
            viewModel.saveCustomersLocalDb(customerItems)
        }

    }
}