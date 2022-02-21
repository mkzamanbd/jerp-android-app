package me.kzaman.android.ui.view.fragments.customer

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
import me.kzaman.android.R
import me.kzaman.android.adapter.CustomerListAdapter
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.database.entities.CustomerEntities
import me.kzaman.android.databinding.FragmentCustomerListBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.view.activities.CustomerActivity
import me.kzaman.android.ui.viewModel.CommonViewModel
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.handleNetworkError
import me.kzaman.android.utils.toastWarning
import me.kzaman.android.utils.visible
import me.kzaman.android.utils.hideSoftKeyboard
import java.util.ArrayList


@AndroidEntryPoint
class CustomerListFragment : BaseFragment<FragmentCustomerListBinding>(
    FragmentCustomerListBinding::inflate
) {
    private val viewModel by viewModels<CommonViewModel>()
    private lateinit var customerListAdapter: CustomerListAdapter

    private var customerType: String = ""
    private var paymentType: String = ""
    private var isCustomerFilter : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingUtils = LoadingUtils(mContext)

        (activity as CustomerActivity).showToolbar(true) //display toolbar
        (activity as CustomerActivity).setToolbarTitle("Customers List")

        customerListAdapter = CustomerListAdapter(arrayListOf(), mContext)

        binding.rvCustomerList.apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = customerListAdapter
        }

        viewModel.getAllCustomersLocalDb()

        viewModel.localCustomers.observe(viewLifecycleOwner) {
            if (it.isNotEmpty() || isCustomerFilter) {
                val customerModels: ArrayList<CustomerModel> = ArrayList()

                it.forEach { customerEntities ->
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
                    )
                    customerModels.add(item)
                }

                customerListAdapter.setCustomers(customerModels)
            } else {
                getRemoteCustomerList()
            }
        }


        val etCustomerSearch = binding.etCustomerSearch
        val ivSearchClear = binding.ivSearchClear

        etCustomerSearch.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                ivSearchClear.visible(false)
            } else {
                ivSearchClear.visible(true)
            }
            customerListAdapter.filter.filter(it)
        }
        ivSearchClear.setOnClickListener {
            etCustomerSearch.text = null
            hideSoftKeyboard(mContext, etCustomerSearch)
            ivSearchClear.visible(false)
        }

        binding.ivCustomerFilter.setOnClickListener {
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

            customerTypeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
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

            paymentTypeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
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
                viewModel.getFilteredCustomersLocalDb(customerType, paymentType)
                isCustomerFilter = true
                dialog.dismiss()
            }
            closeBtn.setOnClickListener {
                dialog.dismiss()
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