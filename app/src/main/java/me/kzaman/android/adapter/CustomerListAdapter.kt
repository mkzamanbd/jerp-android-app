package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.android.R
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.utils.getTintedDrawable
import me.kzaman.android.utils.loadImage
import java.util.Locale
import kotlin.collections.ArrayList

open class CustomerListAdapter(
    val customers: ArrayList<CustomerModel>,
    val mActivity: Activity,
) : RecyclerView.Adapter<CustomerListAdapter.ViewHolder>(), Filterable {

    var filterList = ArrayList<CustomerModel>()

    init {
        filterList = customers
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCustomers(customerList: List<CustomerModel>) {
        customers.clear()
        customers.addAll(customerList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tv_customer_name)
        val location: TextView = view.findViewById(R.id.tv_location)
        val territory: TextView = view.findViewById(R.id.tv_territory)
        val phone: TextView = view.findViewById(R.id.tv_phone)
        val currentDue: TextView = view.findViewById(R.id.tv_due)
        val tvPaymentType: TextView = view.findViewById(R.id.tv_payment_type)
        val imageView: ImageView = view.findViewById(R.id.iv_customer)
        val arrowButton: ImageView = view.findViewById(R.id.customer_arrow_button)
        val tvTotalCartSize: TextView = view.findViewById(R.id.tv_total_cart_size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.model_customer_list, parent, false)
    )

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


        if (customer.creditFlag == "Y") {
            holder.tvPaymentType.text = "Credit"
            holder.tvPaymentType.background =
                getTintedDrawable(ContextCompat.getDrawable(mActivity,
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
            Toast.makeText(mActivity, customer.customerCode, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = filterList.size

    // Filter customers by customer name , customer codes etc
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchKey = constraint.toString().lowercase(Locale.ROOT)
                filterList = if (searchKey.isEmpty()) {
                    customers
                } else {
                    val resultList = ArrayList<CustomerModel>()
                    for (customer in filterList) {
                        if (customer.searchKey.lowercase(Locale.ROOT).contains(searchKey)) {
                            resultList.add(customer)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<CustomerModel>
                Log.d("charSearch", "Published")
                notifyDataSetChanged()
            }
        }
    }


}