package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.android.R
import me.kzaman.android.data.model.Product
import java.util.Locale
import kotlin.collections.ArrayList

class ProductListAdapter(
    var products: ArrayList<Product>,
    val context: Context,
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>(), Filterable {

    var filterList = ArrayList<Product>()

    init {
        filterList = products
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(productList: List<Product>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.prod_name)
        private val code: TextView = view.findViewById(R.id.code)
        private val packSize: TextView = view.findViewById(R.id.pack_size)

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            name.text = product.productName
            code.text = product.productCode
            packSize.text = "(${product.packSize})"

            itemView.setOnClickListener {
                Toast.makeText(context, product.productId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filterList[position])
    }

    override fun getItemCount() = filterList.size

    // Filter products by product name , product codes, generic name etc
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Log.d("charSearch", charSearch)
                if (charSearch.isEmpty()) {
                    filterList = products
                } else {
                    val resultList = ArrayList<Product>()
                    for (product in filterList) {
                        if (product.productName.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT)) || product.productCode.lowercase(
                                Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.add(product)
                        }
                    }
                    filterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<Product>
                notifyDataSetChanged()
            }
        }
    }


}