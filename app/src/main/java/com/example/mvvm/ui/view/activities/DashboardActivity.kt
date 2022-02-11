package com.example.mvvm.ui.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.adapter.MenuParentAdapter
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.data.response.UserChildMenuModel
import com.example.mvvm.data.response.UserParentMenuModel
import com.example.mvvm.database.SharedPreferenceManager
import com.example.mvvm.databinding.ActivityDashboardBinding
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
import com.example.mvvm.utils.LoadingUtils
import com.example.mvvm.utils.handleActivityApiError
import com.example.mvvm.utils.startNewActivity
import com.example.mvvm.utils.visible
import com.example.mvvm.utils.menuRouting
import com.example.mvvm.utils.startAlphaAnimation
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : BaseActivity(), AppBarLayout.OnOffsetChangedListener {
    @Inject
    lateinit var prefManager: SharedPreferenceManager
    private val viewModel by viewModels<CommonViewModel>()
    private lateinit var homeMenuParentAdapter: MenuParentAdapter
    private lateinit var binding: ActivityDashboardBinding

    private var scrollRange = -1
    private var mIsTheTitleVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.appBarLayout.addOnOffsetChangedListener(this)
        loadingUtils = LoadingUtils(this)

        init()

    }


    override fun init() {
        homeMenuParentAdapter = MenuParentAdapter(arrayListOf(), this)

        binding.rvHomeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeMenuParentAdapter
        }

        getMobileMenu()

        viewModel.mobileMenu.observe(this) {
            loadingUtils.isLoading(it is Resource.Loading)

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

    override fun setToolbarTitle(title: String) {}

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
            binding.bottomMenuRoot.visible(true)
            val bottomCenterMenu = binding.bottomCenterMenu

            bottomCenterMenu.setOnClickListener {
                menuRouting(this, bottomMenu[2].featureId)
            }
            binding.lnFirst.setOnClickListener {
                menuRouting(this, bottomMenu[0].featureId)
            }

            binding.lnSecond.setOnClickListener {
                menuRouting(this, bottomMenu[1].featureId)
            }
            binding.lnThird.setOnClickListener {
                menuRouting(this, bottomMenu[3].featureId)
            }
            binding.lnLast.setOnClickListener {
                menuRouting(this, bottomMenu[4].featureId)
            }

            val firstMenu = binding.bottomFirstMenu
            val secondMenu = binding.bottomSecondMenu
            val thirdMenu = binding.bottomThirdMenu
            val lastMenu = binding.bottomLastMenu

            val ivFirst = binding.ivBottomFirst
            val ivSecond = binding.ivBottomSecond
            val ivThird = binding.ivBottomThird
            val ivLast = binding.ivBottomLast

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
            ORDER -> {
                return R.drawable.ic_bt_order_unselect
            }
            DEPOSIT -> {
                return R.drawable.ic_bt_deposit_unselect
            }
            TA_DA -> {
                return R.drawable.ic_bt_ta_da_unselect
            }
            NOTICE -> {
                return R.drawable.ic_bt_warning_unselect
            }
            DCR -> {
                return R.drawable.ic_bt_visit_un_select
            }
            TRACKING -> {
                return R.drawable.ic_bt_tracking_unselect
            }
            RETURN -> {
                return R.drawable.ic_bt_return
            }
            OFFER -> {
                return R.drawable.ic_bt_offers
            }
            PAYMENT_COLLECTION -> {
                return R.drawable.ic_bt_payments
            }
            PRODUCT_LIST -> {
                return R.drawable.ic_bt_product
            }
            CUSTOMER_LIST -> {
                return R.drawable.ic_bt_customer
            }
            REVIEW_ORDER -> {
                return R.drawable.ic_hm_review_order
            }
            REVIEW_REQUEST -> {
                return R.drawable.ic_hm_review
            }
            REVIEW_MTP -> {
                return R.drawable.ic_hm_review_mtp
            }
        }
        return R.drawable.ic_no_image_hm
    }

    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        prefManager.clearAll()
        startNewActivity(AuthActivity::class.java)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack();
        } else {
            super.onBackPressed()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout.totalScrollRange
        }
        if (scrollRange + verticalOffset == 0) {
            startAlphaAnimation(binding.toolbar, 0, View.VISIBLE)
            binding.clCollapsing.visibility = View.INVISIBLE
            binding.clCollapsing.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorPrimary))
            mIsTheTitleVisible = true
        } else if (mIsTheTitleVisible) {
            startAlphaAnimation(binding.toolbar, 0, View.INVISIBLE)
            binding.clCollapsing.visibility = View.VISIBLE
            mIsTheTitleVisible = false
            binding.toolbar.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorPrimary))
            binding.clCollapsing.background =
                ContextCompat.getDrawable(this, R.drawable.bg_home_profile)
        }
    }
}