package me.kzaman.demo_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import me.kzaman.demo_app.R
import me.kzaman.demo_app.data.model.CustomerModel
import me.kzaman.demo_app.ui.activities.OrdersActivity
import me.kzaman.demo_app.utils.getTintedDrawable
import me.kzaman.demo_app.utils.goToNextFragment
import me.kzaman.demo_app.utils.loadImage
import kotlin.collections.ArrayList

class ChooseCustomerAdapter(
    customers: ArrayList<CustomerModel>,
    mContext: Context,
) : CustomerListAdapter(customers, mContext) {

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

        if (customer.totalCartItem > 0) {
            holder.tvTotalCartSize.visibility = View.VISIBLE
            holder.arrowButton.visibility = View.GONE
            holder.tvTotalCartSize.text = "${customer.totalCartItem}"
        } else {
            holder.arrowButton.visibility = View.VISIBLE
            holder.tvTotalCartSize.visibility = View.GONE
            holder.arrowButton.setImageDrawable(ContextCompat.getDrawable(mContext,
                R.drawable.ic_baseline_shopping_basket_15))
        }

        if (customer.creditFlag == "Y") {
            holder.tvPaymentType.text = "Credit"
            holder.tvPaymentType.background = getTintedDrawable(ContextCompat.getDrawable(mContext,
                R.drawable.bg_order_status)!!,
                ContextCompat.getColor(mContext, R.color.credit_type_customer_bg))
            holder.tvPaymentType.setTextColor(ContextCompat.getColor(mContext,
                R.color.credit_type_customer))

        } else {
            holder.tvPaymentType.text = "Cash"
            holder.tvPaymentType.background =
                getTintedDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.bg_order_status)!!,
                    ContextCompat.getColor(mContext, R.color.cash_type_customer_bg))
            holder.tvPaymentType.setTextColor(ContextCompat.getColor(mContext,
                R.color.cash_type_customer))
        }

        if (customer.photo != null) {
            holder.imageView.loadImage(customer.photo!!)
        }

        holder.itemView.setOnClickListener {
            OrdersActivity.customerModel = customer
            goToNextFragment(R.id.action_customerSelectionFragment_to_productSelectionFragment,
                holder.itemView,
                null)
        }
    }
}