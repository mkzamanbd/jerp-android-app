package me.kzaman.demo_app.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.demo_app.R
import me.kzaman.demo_app.base.BaseActivity
import me.kzaman.demo_app.data.model.CustomerModel
import me.kzaman.demo_app.data.model.ProductInfo
import me.kzaman.demo_app.database.entities.CartItemsEntities
import me.kzaman.demo_app.ui.fragments.orders.ProductSelectionFragment
import me.kzaman.demo_app.ui.viewModel.OrderViewModel
import me.kzaman.demo_app.utils.visible
import org.json.JSONArray
import org.json.JSONObject

@AndroidEntryPoint
class OrdersActivity : BaseActivity() {

    companion object {
        var customerModel: CustomerModel? = null
    }

    private val viewModel by viewModels<OrderViewModel>()
    private lateinit var rlToolbar: RelativeLayout
    private lateinit var tvTitle: TextView
    private lateinit var ivBackButton: ImageView

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_fragments)
        initializeApp()
    }

    override fun initializeApp() {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragment_view) as NavHostFragment
        navController = navHost.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(R.id.customerSelectionFragment)
        navController.graph = navGraph

        rlToolbar = findViewById(R.id.toolbar_root)
        tvTitle = findViewById(R.id.tv_toolbar_title)
        ivBackButton = findViewById(R.id.iv_back_button)

        ivBackButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun setToolbarTitle(title: String) {
        setToolbarTitle(title, tvTitle)
    }

    override fun hideToolbar() {
        rlToolbar.visibility = View.GONE
    }

    override fun showToolbar(isBackButton: Boolean) {
        ivBackButton.visible(isBackButton)
        rlToolbar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        doOnBackPressed()
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            Log.d("onBackPressed", "backStackEntryCount")
        } else {
            super.onBackPressed()
            Log.d("onBackPressed", "super.onBackPressed")
            overridePendingTransition(0, R.anim.animation_slide_out_right)
        }
    }

    private fun doOnBackPressed() {
        if (navController.currentDestination?.id == R.id.customerSelectionFragment) {
            if (ProductSelectionFragment.selectedProduct.size > 0) {
                storeProductCartItem(ProductSelectionFragment.selectedProduct)
                Log.d("saveCart", "Cart Saved Customer Selection")
            }
        } else if (navController.currentDestination?.id == R.id.productSelectionFragment) {
            if (ProductSelectionFragment.selectedProduct.size > 0) {
                storeProductCartItem(ProductSelectionFragment.selectedProduct)
                Log.d("saveCart", "Cart Saved Product Selection")
            }
        }
    }

    fun storeProductCartItem(products: List<ProductInfo>) {
        try {
            val productJson = JSONArray()
            products.forEach { product ->
                val jsonObject = JSONObject()
                jsonObject.put("id", product.productId)
                jsonObject.put("qty", product.quantity)
                productJson.put(jsonObject)
            }
            val cartItemsEntities = customerModel?.let {
                CartItemsEntities(
                    customerId = it.compositeKey,
                    cartJson = productJson.toString()
                )
            }
            lifecycleScope.launch {
                viewModel.saveCartProducts(cartItemsEntities!!)
                ProductSelectionFragment.selectedProduct.clear()
                Log.d("storeJsonCart", cartItemsEntities.toString())
            }

        } catch (e: Exception) {
            Log.d("orderJsonException", e.toString())
        }
    }
}