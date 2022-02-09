package com.example.mvvm.ui.view.activities

import android.os.Bundle
import android.widget.Toast
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.adapter.MenuParentAdapter
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.data.response.UserChildMenuModel
import com.example.mvvm.data.response.UserParentMenuModel
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.network.Resource
import com.example.mvvm.ui.viewModel.CommonViewModel
import com.example.mvvm.utils.Constants.Companion.CUSTOMER_LIST
import com.example.mvvm.utils.Constants.Companion.DCR
import com.example.mvvm.utils.Constants.Companion.DELIVERY
import com.example.mvvm.utils.Constants.Companion.DEPOSIT
import com.example.mvvm.utils.Constants.Companion.NOTICE
import com.example.mvvm.utils.Constants.Companion.OFFER
import com.example.mvvm.utils.Constants.Companion.ORDER
import com.example.mvvm.utils.Constants.Companion.PAYMENT_COLLECTION
import com.example.mvvm.utils.Constants.Companion.PRODUCT_LIST
import com.example.mvvm.utils.Constants.Companion.RETURN
import com.example.mvvm.utils.Constants.Companion.REVIEW_MTP
import com.example.mvvm.utils.Constants.Companion.REVIEW_ORDER
import com.example.mvvm.utils.Constants.Companion.REVIEW_REQUEST
import com.example.mvvm.utils.Constants.Companion.TA_DA
import com.example.mvvm.utils.Constants.Companion.TRACKING
import com.example.mvvm.utils.handleActivityApiError
import com.example.mvvm.utils.menuRouting
import com.example.mvvm.utils.startNewActivity
import com.example.mvvm.utils.visible
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : BaseActivity() {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    private val viewModel by viewModels<CommonViewModel>()
    private lateinit var homeMenuParentAdapter: MenuParentAdapter

    private lateinit var homeRecyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var bottomMenuRoot: CoordinatorLayout
    private lateinit var lnFirst: LinearLayout
    private lateinit var lnSecond: LinearLayout
    private lateinit var lnThird: LinearLayout
    private lateinit var lnLast: LinearLayout

    private lateinit var bottomCenterMenu: FloatingActionButton
    private lateinit var firstMenu: TextView
    private lateinit var secondMenu: TextView
    private lateinit var thirdMenu: TextView
    private lateinit var lastMenu: TextView

    private lateinit var ivFirst: ImageView
    private lateinit var ivSecond: ImageView
    private lateinit var ivThird: ImageView
    private lateinit var ivLast: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        homeRecyclerView = findViewById(R.id.rv_home_list)
        progressBar = findViewById(R.id.progressBar)
        bottomMenuRoot = findViewById(R.id.bottom_menu_root)

        lnFirst = findViewById(R.id.ln_first)
        lnSecond = findViewById(R.id.ln_second)
        lnThird = findViewById(R.id.ln_third)
        lnLast = findViewById(R.id.ln_last)

        bottomCenterMenu = findViewById(R.id.bottom_center_menu)
        firstMenu = findViewById(R.id.bottom_first_menu)
        secondMenu = findViewById(R.id.bottom_second_menu)
        thirdMenu = findViewById(R.id.bottom_third_menu)
        lastMenu = findViewById(R.id.bottom_last_menu)

        ivFirst = findViewById(R.id.iv_bottom_first)
        ivSecond = findViewById(R.id.iv_bottom_second)
        ivThird = findViewById(R.id.iv_bottom_third)
        ivLast = findViewById(R.id.iv_bottom_last)

        homeMenuParentAdapter = MenuParentAdapter(arrayListOf(), this)

        homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeMenuParentAdapter
        }

        getMobileMenu()

        viewModel.mobileMenu.observe(this) {
            progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    setBottomMenu(it.value.data.bottomParentMenu)
                    homeMenuParentAdapter.setHomePrentMenu(it.value.data.topParentMenu)
                }
                is Resource.Failure -> handleActivityApiError(it) {
                    Toast.makeText(this, "Menu Can't loaded", Toast.LENGTH_SHORT).show()
                }
                else -> Log.d("error", "Unknown Error")
            }
        }

    }

    private fun getMobileMenu() {
        viewModel.getMobileMenu()
    }

    private fun setBottomMenu(menuModels: List<UserParentMenuModel>) {
        val bottomMenu: MutableList<UserChildMenuModel> = ArrayList()
        for (bottom in menuModels) {
            if (bottom.menuName == "BOTTOM") {
                bottomMenu.addAll(bottom.menuItems)
            }
        }

        if (bottomMenu.size == 5) {
            bottomMenuRoot.visible(true)
            bottomCenterMenu.setOnClickListener {
                menuRouting(this, bottomMenu[2].featureId)
                // findNavController().navigate(R.id.action_dashboardFragment_to_productFragment)
            }
            lnFirst.setOnClickListener {
                menuRouting(this, bottomMenu[0].featureId)
            }

            lnSecond.setOnClickListener {
                menuRouting(this, bottomMenu[1].featureId)
            }
            lnThird.setOnClickListener {
                menuRouting(this, bottomMenu[3].featureId)
            }
            lnLast.setOnClickListener {
                menuRouting(this, bottomMenu[4].featureId)
            }

            for ((count, bottom) in bottomMenu.withIndex()) {
                when (count) {
                    0 -> {
                        firstMenu.text = bottom.menuName
                        ivFirst.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))
                    }
                    1 -> {
                        secondMenu.text = bottom.menuName
                        ivSecond.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))

                    }
                    2 -> {
                        when (bottom.featureId) {
                            ORDER -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_baseline_shopping_basket_24))
                            }
                            TRACKING -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_hm_track))
                            }
                            DELIVERY -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_hm_delivery))
                            }
                            else -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_no_image_hm))
                            }
                        }
                    }
                    3 -> {
                        thirdMenu.text = bottom.menuName
                        ivThird.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))
                    }
                    4 -> {
                        lastMenu.text = bottom.menuName
                        ivLast.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))
                    }
                }
            }
        }
    }

    private fun getMenuIcon(featureId: String): Int {
        when (featureId) {
            ORDER -> return R.drawable.ic_bt_order_unselect
            DEPOSIT -> return R.drawable.ic_bt_deposit_unselect
            TA_DA -> return R.drawable.ic_bt_ta_da_unselect
            NOTICE -> return R.drawable.ic_bt_warning_unselect
            DCR -> return R.drawable.ic_bt_visit_un_select
            TRACKING -> return R.drawable.ic_bt_tracking_unselect
            RETURN -> return R.drawable.ic_bt_return
            OFFER -> return R.drawable.ic_bt_offers
            PAYMENT_COLLECTION -> return R.drawable.ic_bt_payments
            PRODUCT_LIST -> return R.drawable.ic_bt_product
            CUSTOMER_LIST -> return R.drawable.ic_bt_customer
            REVIEW_ORDER -> return R.drawable.ic_hm_review_order
            REVIEW_REQUEST -> return R.drawable.ic_hm_review
            REVIEW_MTP -> return R.drawable.ic_hm_review_mtp
        }
        return R.drawable.ic_no_image_hm
    }


    override fun init() {}
    override fun setToolbarTitle(title: String) {}

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        prefManager.clearAll()
        startNewActivity(AuthActivity::class.java)
    }
}