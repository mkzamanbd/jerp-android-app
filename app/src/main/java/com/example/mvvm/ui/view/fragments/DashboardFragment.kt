package com.example.mvvm.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm.R
import com.example.mvvm.adapter.MenuParentAdapter
import com.example.mvvm.base.BaseActivity
import com.example.mvvm.base.BaseFragment
import com.example.mvvm.data.response.UserChildMenuModel
import com.example.mvvm.data.response.UserParentMenuModel
import com.example.mvvm.databinding.FragmentDashboardBinding
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
import com.example.mvvm.utils.handleApiError
import com.example.mvvm.utils.menuRouting
import com.example.mvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>(
    FragmentDashboardBinding::inflate
) {

    private val viewModel by viewModels<CommonViewModel>()

    private lateinit var homeMenuParentAdapter: MenuParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireContext()
        mActivity = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as BaseActivity).hideToolbar() //display toolbar

        homeMenuParentAdapter = MenuParentAdapter(arrayListOf(), mContext)

        val homeRecyclerView = binding.rvHomeList

        homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeMenuParentAdapter
        }

        getMobileMenu()

        viewModel.mobileMenu.observe(viewLifecycleOwner) {
            binding.progressBar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    setBottomMenu(it.value.data.bottomParentMenu)
                    homeMenuParentAdapter.setHomePrentMenu(it.value.data.topParentMenu)
                }
                is Resource.Failure -> handleApiError(it) {
                    getMobileMenu()
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
        val fabCenter = binding.bottomCenterMenu
        val firstMenu = binding.bottomFirstMenu
        val secondMenu = binding.bottomSecondMenu
        val thirdMenu = binding.bottomThirdMenu
        val lastMenu = binding.bottomLastMenu

        val ivFirst = binding.ivBottomFirst
        val ivSecond = binding.ivBottomSecond
        val ivThird = binding.ivBottomThird
        val ivLast = binding.ivBottomLast

        if (bottomMenu.size == 5) {
            binding.bottomMenuRoot.visible(true)
            binding.bottomCenterMenu.setOnClickListener {
                menuRouting(mContext, bottomMenu[2].featureId)
                findNavController().navigate(R.id.action_dashboardFragment_to_productFragment)
            }
            binding.lnFirst.setOnClickListener {
                menuRouting(mContext, bottomMenu[0].featureId)
            }

            binding.lnSecond.setOnClickListener {
                menuRouting(mContext, bottomMenu[1].featureId)
            }
            binding.lnThird.setOnClickListener {
                menuRouting(mContext, bottomMenu[3].featureId)
            }
            binding.lnLast.setOnClickListener {
                menuRouting(mContext, bottomMenu[4].featureId)
            }

            for ((count, bottom) in bottomMenu.withIndex()) {
                when (count) {
                    0 -> {
                        firstMenu.text = bottom.menuName
                        ivFirst.setImageDrawable(ContextCompat.getDrawable(mContext,
                            getMenuIcon(bottom.featureId)))
                    }
                    1 -> {
                        secondMenu.text = bottom.menuName
                        ivSecond.setImageDrawable(ContextCompat.getDrawable(mContext,
                            getMenuIcon(bottom.featureId)))

                    }
                    2 -> {
                        when (bottom.featureId) {
                            ORDER -> {
                                fabCenter.setImageDrawable(ContextCompat.getDrawable(mContext,
                                    R.drawable.ic_baseline_shopping_basket_24))
                            }
                            TRACKING -> {
                                fabCenter.setImageDrawable(ContextCompat.getDrawable(mContext,
                                    R.drawable.ic_hm_track))
                            }
                            DELIVERY -> {
                                fabCenter.setImageDrawable(ContextCompat.getDrawable(mContext,
                                    R.drawable.ic_hm_delivery))
                            }
                            else -> {
                                fabCenter.setImageDrawable(ContextCompat.getDrawable(mContext,
                                    R.drawable.ic_no_image_hm))
                            }
                        }
                    }
                    3 -> {
                        thirdMenu.text = bottom.menuName
                        ivThird.setImageDrawable(ContextCompat.getDrawable(mContext,
                            getMenuIcon(bottom.featureId)))
                    }
                    4 -> {
                        lastMenu.text = bottom.menuName
                        ivLast.setImageDrawable(ContextCompat.getDrawable(mContext,
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
}