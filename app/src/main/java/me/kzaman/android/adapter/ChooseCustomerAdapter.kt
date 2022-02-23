package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.app.Activity
import androidx.core.content.ContextCompat
import me.kzaman.android.R
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.ui.view.activities.OrdersActivity
import me.kzaman.android.utils.getTintedDrawable
import me.kzaman.android.utils.goToNextFragment
import me.kzaman.android.utils.loadImage
import kotlin.collections.ArrayList

class ChooseCustomerAdapter(
    customers: ArrayList<CustomerModel>,
    mActivity: Activity,
) : CustomerListAdapter(customers, mActivity) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val customer = filterList[position]

        holder.name.text = customer.customerName
        holder.location.text = customer.customerAddress
        holder.territory.text = customer.territoryName
        holder.phone.text = customer.phone
        if (customer.currentDue.toFloat() > 0) {
            holder.currentDue.text = "Due: ${customer.currentDue}"
        }

        holder.arrowButton.setImageDrawable(ContextCompat.getDrawable(mActivity,
            R.drawable.ic_baseline_shopping_basket_24))

        if (customer.creditFlag == "Y") {
            holder.tvPaymentType.text = "Credit"
            holder.tvPaymentType.background = getTintedDrawable(ContextCompat.getDrawable(mActivity,
                R.drawable.bg_order_status)!!,
                ContextCompat.getColor(mActivity, R.color.credit_type_customer_bg))
            holder.tvPaymentType.setTextColor(ContextCompat.getColor(mActivity,
                R.color.credit_type_customer))

        } else {
            holder.tvPaymentType.text = "Cash"
            holder.tvPaymentType.background =
                getTintedDrawable(ContextCompat.getDrawable(mActivity,
                    R.drawable.bg_order_status)!!,
                    ContextCompat.getColor(mActivity, R.color.cash_type_customer_bg))
            holder.tvPaymentType.setTextColor(ContextCompat.getColor(mActivity,
                R.color.cash_type_customer))
        }

        if (customer.photo != null) {
            holder.imageView.loadImage(customer.photo!!)
        }

        holder.itemView.setOnClickListener {
            OrdersActivity.customerModel = customer
            goToNextFragment(R.id.action_customerSelectionFragment_to_productSelectionFragment, holder.itemView, null)
        }
    }
}