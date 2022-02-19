package me.kzaman.android.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import me.kzaman.android.adapter.ProductListAdapter
import me.kzaman.android.base.BaseFragment
import me.kzaman.android.databinding.FragmentProductListBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.view.activities.ProductActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.android.data.model.Product
import me.kzaman.android.database.entities.ProductEntities
import me.kzaman.android.ui.viewModel.ProductViewModel
import me.kzaman.android.utils.LoadingUtils
import me.kzaman.android.utils.hideSoftKeyboard
import me.kzaman.android.utils.toastWarning
import me.kzaman.android.utils.visible
import me.kzaman.android.utils.handleNetworkError
import java.util.ArrayList

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>(
    FragmentProductListBinding::inflate
) {

    private val viewModel by viewModels<ProductViewModel>()

    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productListAdapter = ProductListAdapter(arrayListOf(), mContext)
        loadingUtils = LoadingUtils(mContext)

        (activity as ProductActivity).showToolbar(true) //display toolbar
        (activity as ProductActivity).setToolbarTitle("Product List")

        val productListRecyclerView = binding.productList

        productListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productListAdapter
        }

        val etSearch = binding.etSearch
        val ivCancelSearch = binding.ivCancelSearch

        etSearch.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                ivCancelSearch.visible(false)
            } else {
                ivCancelSearch.visible(true)
            }
            productListAdapter.filter.filter(it)
        }

        binding.ivCancelSearch.setOnClickListener {
            etSearch.text = null
            hideSoftKeyboard(mContext, etSearch)
            ivCancelSearch.visible(false)
        }

        getProductList()

        viewModel.products.observe(viewLifecycleOwner) {
            loadingUtils.isLoading(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    val response = it.value
                    if (response.responseCode == 200) {
                        productListAdapter.setProducts(response.productList)
                        saveLocalProduct(response.productList)
                    } else {
                        toastWarning(mActivity, "Product list list not found")
                    }
                }
                is Resource.Failure -> handleNetworkError(it, mActivity) {
                    getProductList()
                }
                else -> Log.d("unknownError", "Unknown Error")
            }
        }

        viewModel.localProducts.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewModel.getRemoteProducts()
            } else {
                Log.d("localProduct", it.toString())
                val products = ArrayList<Product>()
                it.forEach { product ->
                    val item = Product(
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
                productListAdapter.setProducts(products)
            }
        }
    }

    private fun getProductList() {
        viewModel.getLocalProducts()
    }

    private fun saveLocalProduct(products: List<Product>) {
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
            )
            productItems.add(item)
        }
        lifecycleScope.launch {
            viewModel.saveProductToLocal(productItems)
        }
    }
}