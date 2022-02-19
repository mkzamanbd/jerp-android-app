package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.android.R
import me.kzaman.android.data.response.UserChildMenuModel
import me.kzaman.android.data.response.UserParentMenuModel
import me.kzaman.android.utils.Constants.Companion.ADVISER_LIST
import me.kzaman.android.utils.Constants.Companion.CUSTOMER_LIST
import me.kzaman.android.utils.Constants.Companion.DAILY_CALL_PLAN
import me.kzaman.android.utils.Constants.Companion.DCR
import me.kzaman.android.utils.Constants.Companion.DELIVERY
import me.kzaman.android.utils.Constants.Companion.DEPOSIT
import me.kzaman.android.utils.Constants.Companion.HISTORY
import me.kzaman.android.utils.Constants.Companion.MONTHLY_TOUR_PLAN
import me.kzaman.android.utils.Constants.Companion.NOTICE
import me.kzaman.android.utils.Constants.Companion.OFFER
import me.kzaman.android.utils.Constants.Companion.ORDER
import me.kzaman.android.utils.Constants.Companion.ORDER_HISTORY
import me.kzaman.android.utils.Constants.Companion.PAYMENT_COLLECTION
import me.kzaman.android.utils.Constants.Companion.PRODUCT_LIST
import me.kzaman.android.utils.Constants.Companion.RETURN
import me.kzaman.android.utils.Constants.Companion.REVIEW_MTP
import me.kzaman.android.utils.Constants.Companion.REVIEW_ORDER
import me.kzaman.android.utils.Constants.Companion.REVIEW_REQUEST
import me.kzaman.android.utils.Constants.Companion.SETTING
import me.kzaman.android.utils.Constants.Companion.TA_DA
import me.kzaman.android.utils.Constants.Companion.TRACKING
import me.kzaman.android.utils.horizontalColumnRecyclerView


class MenuParentAdapter(
    var menuItems: ArrayList<UserParentMenuModel>,
    private val activity: Activity,
) : RecyclerView.Adapter<MenuParentAdapter.ViewHolder>() {

    lateinit var childMenuAdapter: MenuChildAdapter

    @SuppressLint("NotifyDataSetChanged")
    fun setHomePrentMenu(menuModel: List<UserParentMenuModel>) {
        menuItems.clear()
        menuItems.addAll(menuModel)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val rvList: RecyclerView = view.findViewById(R.id.child_menu_list)

        fun bind(menuModel: UserParentMenuModel) {
            tvTitle.text = menuModel.menuName
            childMenuAdapter = MenuChildAdapter(getUserChildMenu(menuModel.menuItems), activity)
            horizontalColumnRecyclerView(activity, rvList, 4).adapter = childMenuAdapter
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_menu_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount() = menuItems.size

    fun getUserChildMenu(menuItems: List<UserChildMenuModel>): List<UserChildMenuModel> {

        for (menu in menuItems) {
            when (menu.featureId) {
                ORDER -> {
                    menu.iconId = R.drawable.ic_hm_order
                }

                DCR -> {
                    menu.iconId = R.drawable.ic_hm_visit
                }

                MONTHLY_TOUR_PLAN -> {
                    menu.iconId = R.drawable.ic_hm_tour_plan
                }

                DAILY_CALL_PLAN -> {
                    menu.iconId = R.drawable.ic_hm_dcr
                }

                TA_DA -> {
                    menu.iconId = R.drawable.ic_ta_da
                }

                CUSTOMER_LIST -> {
                    menu.iconId = R.drawable.ic_baseline_people_36
                }

                ADVISER_LIST -> {
                    menu.iconId = R.drawable.ic_hm_doctors;
                }

                NOTICE -> {
                    menu.iconId = R.drawable.ic_hm_notice
                };

                OFFER -> {
                    menu.iconId = R.drawable.ic_baseline_card_giftcard_36
                }

                SETTING -> {
                    menu.iconId = R.drawable.ic_hm_setting
                }

                TRACKING -> {
                    menu.iconId = R.drawable.ic_hm_track
                }

                DEPOSIT -> {
                    menu.iconId = R.drawable.ic_hm_deposit
                }

                DELIVERY -> {
                    menu.iconId = R.drawable.ic_hm_delivery
                }

                RETURN -> {
                    menu.iconId = R.drawable.ic_hm_return
                }

                PAYMENT_COLLECTION -> {
                    menu.iconId = R.drawable.ic_hm_payments;
                }

                PRODUCT_LIST -> {
                    menu.iconId = R.drawable.ic_product_list
                }

                ORDER_HISTORY -> {
                    menu.iconId = R.drawable.ic_hm_invoices
                }

                HISTORY -> {
                    menu.iconId = R.drawable.ic_hm_history
                }

                "824" -> {
                    menu.iconId = R.drawable.ic_hm_rx_capture
                }

                "825" -> {
                    menu.iconId = R.drawable.ic_hm_report
                }

                "818" -> {
                    menu.iconId = R.drawable.ic_hm_message
                }

                "819" -> {
                    menu.iconId = R.drawable.ic_hm_team
                }

                REVIEW_ORDER -> {
                    menu.iconId = R.drawable.ic_hm_review_order
                }

                REVIEW_REQUEST -> {
                    menu.iconId = R.drawable.ic_hm_review
                }

                REVIEW_MTP -> {
                    menu.iconId = R.drawable.ic_hm_review_mtp
                }
                else -> {
                    menu.iconId = R.drawable.ic_baseline_image_not_supported_36
                }
            }
        }

        return menuItems
    }
}