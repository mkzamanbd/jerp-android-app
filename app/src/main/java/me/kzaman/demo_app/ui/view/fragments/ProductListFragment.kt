package me.kzaman.demo_app.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import me.kzaman.demo_app.adapter.ProductListAdapter
import me.kzaman.demo_app.base.BaseFragment
import me.kzaman.demo_app.databinding.FragmentProductListBinding
import me.kzaman.demo_app.network.Resource
import me.kzaman.demo_app.ui.view.activities.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.demo_app.utils.visible
import me.kzaman.demo_app.R
import me.kzaman.demo_app.data.model.ProductInfo
import me.kzaman.demo_app.database.entities.ProductEntities
import me.kzaman.demo_app.ui.viewModel.OrderViewModel
import me.kzaman.demo_app.utils.LoadingUtils
import me.kzaman.demo_app.utils.hideSoftKeyboard
import me.kzaman.demo_app.utils.toastWarning
import me.kzaman.demo_app.utils.handleNetworkError
import java.util.ArrayList

@AndroidEntryPoint
open class ProductListFragment : BaseFragment<FragmentProductListBinding>() {
    lateinit var binding: FragmentProductListBinding

    protected val viewModel by viewModels<OrderViewModel>()
    override val layoutId: Int = R.layout.fragment_product_list

    protected lateinit var productListAdapter: ProductListAdapter

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
        binding.orderViewModel = viewModel
        initializeApp()

        binding.etSearch.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                binding.ivCancelSearch.visible(false)
            } else {
                binding.ivCancelSearch.visible(true)
            }
            productListAdapter.filter.filter(it)
        }

        binding.ivCancelSearch.setOnClickListener {
            binding.etSearch.text = null
            hideSoftKeyboard(mContext, binding.etSearch)
            it.visible(false)
        }
        /*
        * get local product
        */
        getLocalProducts()

    }

    override fun initializeApp() {
        (activity as ProductActivity).showToolbar(true) //display toolbar
        (activity as ProductActivity).setToolbarTitle("Product List")
        binding.cvCustomerInfo.visibility = View.GONE

        productListAdapter = ProductListAdapter(arrayListOf(), mContext)
        binding.productList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }
    }

    private fun getLocalProducts() {
        viewModel.getLocalProducts()
        viewModel.localProducts.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                getRemoteProductList()
            } else {
                Log.d("localProduct", it.toString())
                val products = ArrayList<ProductInfo>()
                it.forEach { product ->
                    val item = ProductInfo(
                        id = product.id,
                        productId = product.productId,
                        productClass = product.productClass,
                        baseTp = product.baseTp,
                        baseQty = product.baseQty,
                        baseVat = product.baseVat,
                        productName = product.productName,
                        displayName = product.displayName ?: "",
                        productCode = product.productCode,
                        displayCode = product.displayCode ?: "",
                        searchKey = product.searchKey ?: "",
                        packSize = product.packSize ?: "",
                        comPackDesc = product.comPackDesc,
                        offerDescription = product.offerDescription,
                        mtp = product.mtp,
                        offer = product.offer,
                        offerType = product.offerType ?: "",
                        startDate = product.startDate ?: "",
                        validUntil = product.validUntil,
                        minimumQty = product.minimumQty,
                        elements = product.elementInfo ?: ""
                    )
                    products.add(item)
                }
                displayProductList(products)
            }
        }
    }

    private fun getRemoteProductList() {
        viewModel.getRemoteProducts()
        viewModel.products.observe(viewLifecycleOwner) {
            loadingUtils.isLoading(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    val response = it.value
                    if (response.responseCode == 200) {
                        val productList = ArrayList<ProductInfo>()
                        response.productList.forEach { product ->
                            val gson = GsonBuilder().create()
                            val elementInfo = if (!product.elementInfo.isNullOrEmpty()) {
                                gson.toJson(product.elementInfo)
                            } else ""
                            product.elements = if (!elementInfo.isNullOrEmpty()) {
                                elementInfo
                            } else ""
                            productList.add(product)
                        }
                        saveLocalProduct(productList)
                        displayProductList(productList)
                    } else {
                        toastWarning(mActivity, "Product list list not found")
                    }
                }
                is Resource.Failure -> handleNetworkError(it, mActivity) {
                    getLocalProducts()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }
    }

    private fun saveLocalProduct(products: List<ProductInfo>) {
        val productItems: ArrayList<ProductEntities> = ArrayList()
        products.forEach { product ->
            val item = ProductEntities(
                id = product.id,
                productId = product.productId,
                productClass = product.productClass,
                baseTp = product.baseTp,
                baseQty = product.baseQty,
                baseVat = product.baseVat,
                productName = product.productName,
                displayName = product.displayName,
                productCode = product.productCode,
                displayCode = product.displayCode,
                searchKey = product.searchKey,
                packSize = product.packSize,
                comPackDesc = product.comPackDesc,
                offerDescription = product.offerDescription,
                mtp = product.mtp,
                offer = product.offer,
                offerType = product.offerType,
                startDate = product.startDate,
                validUntil = product.validUntil,
                minimumQty = product.minimumQty,
                elementInfo = product.elements
            )
            productItems.add(item)
        }
        lifecycleScope.launch {
            viewModel.saveProductToLocal(productItems)
        }
    }

    open fun displayProductList(products: List<ProductInfo>) {
        productListAdapter.setProducts(products)
    }
}