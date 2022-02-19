package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.android.R
import me.kzaman.android.data.model.CustomerModel
import me.kzaman.android.data.model.Product
import me.kzaman.android.utils.getTintedDrawable
import me.kzaman.android.utils.loadImage
import java.util.Locale
import kotlin.collections.ArrayList

class CustomerListAdapter(
    var customers: ArrayList<CustomerModel>,
    val mContext: Context,
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tv_customer_name)
        private val location: TextView = view.findViewById(R.id.tv_location)
        private val territory: TextView = view.findViewById(R.id.tv_territory)
        private val phone: TextView = view.findViewById(R.id.tv_phone)

        private val tvPaymentType: TextView = view.findViewById(R.id.tv_payment_type)
        private val imageView: ImageView = view.findViewById(R.id.iv_customer)

        @SuppressLint("SetTextI18n")
        fun bind(customer: CustomerModel) {
            name.text = customer.customerName
            location.text = customer.customerAddress
            territory.text = customer.territoryName
            phone.text = customer.phone
            if (customer.creditFlag == "Y") {
                tvPaymentType.text = "Credit"
                tvPaymentType.background = getTintedDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.bg_order_status)!!,
                    ContextCompat.getColor(mContext, R.color.credit_type_customer_bg))
                tvPaymentType.setTextColor(ContextCompat.getColor(mContext,
                    R.color.credit_type_customer))

            } else {
                tvPaymentType.text = "Cash"
                tvPaymentType.background = getTintedDrawable(ContextCompat.getDrawable(mContext,
                    R.drawable.bg_order_status)!!,
                    ContextCompat.getColor(mContext, R.color.cash_type_customer_bg))
                tvPaymentType.setTextColor(ContextCompat.getColor(mContext,
                    R.color.cash_type_customer))
            }

            if (customer.photo != null) {
                imageView.loadImage(customer.photo!!)
            }

            itemView.setOnClickListener {
                Toast.makeText(mContext, customer.customerCode, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.customer_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    override fun getItemCount() = filterList.size

    // Filter customers by customer name , customer codes etc
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Log.d("charSearch", charSearch)
                filterList = if (charSearch.isEmpty()) {
                    customers
                } else {
                    val resultList = ArrayList<CustomerModel>()
                    for (customer in filterList) {
                        if (customer.searchKey.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
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
                notifyDataSetChanged()
            }
        }
    }


}