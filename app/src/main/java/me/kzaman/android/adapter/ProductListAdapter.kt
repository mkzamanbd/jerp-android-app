package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import me.kzaman.android.R
import me.kzaman.android.data.model.ProductElement
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.utils.compareDatesWithCurrentDate
import java.util.Locale
import kotlin.collections.ArrayList

class ProductListAdapter(
    var products: ArrayList<ProductInfo>,
    val context: Context,
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>(), Filterable {

    var filterList = ArrayList<ProductInfo>()

    init {
        filterList = products
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(productList: List<ProductInfo>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val tvDetails: TextView = view.findViewById(R.id.tv_details)

        @SuppressLint("SetTextI18n")
        fun bind(product: ProductInfo) {
            val packSize =
                "<small><b><font color='#696A6A'>(${product.packSize})</font><b/></small>"
            val offerText = "<b><font color='#009C32'>${product.offer}</font><b/>"
            val offerDescription = "<b><font color='#757575'>${product.offerDescription}</font><b/>"

            val genericName: String = genericNameFromJson(product.elements).ifEmpty {
                ""
            }

            val mtp: String = if (product.offerType == "D" || product.offerType == "P") {
                if (!TextUtils.isEmpty(product.startDate) && !TextUtils.isEmpty(product.validUntil)) {
                    if (compareDatesWithCurrentDate(product.validUntil, product.startDate))
                        "<b><font color='#009C32'>MTP: " + product.mtp + "</font><b> | " + offerDescription
                    else "TP: <font color='#757575'>" + (product.baseTp + product.baseVat) + "</font>"
                } else "TP: <font color='#757575'>" + (product.baseTp + product.baseVat) + "</font>"
            } else if (product.offerType == "F" || product.offerType == "B") {
                if (!TextUtils.isEmpty(product.startDate) && !TextUtils.isEmpty(product.validUntil)) {
                    if (compareDatesWithCurrentDate(product.validUntil, product.startDate))
                        "TP: <font color='#757575'>" + product.mtp + " | </font> " + offerText + offerDescription
                    else "TP: <font color='#757575'>" + product.mtp + " | </font> "
                } else "TP: <font color='#757575'>" + product.mtp + " | </font> "
            } else "TP: <font color='#757575'>" + product.mtp + "</font>"

            tvTitle.text = HtmlCompat.fromHtml("${product.productName} $packSize",
                HtmlCompat.FROM_HTML_MODE_LEGACY)
            var tvDetailHtml = product.productCode
            if (!product.comPackDesc.isNullOrEmpty()) {
                tvDetailHtml += " | ${product.comPackDesc}"
            }
            if (genericName.isNotEmpty()) {
                tvDetailHtml += genericName
            }

            if (genericName.isNotEmpty()) {
                tvDetailHtml += " | $mtp"
            }
            tvDetails.text = HtmlCompat.fromHtml(tvDetailHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)

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
                val searchKey = constraint.toString().lowercase(Locale.ROOT)
                Log.d("charSearch", searchKey)
                filterList = if (searchKey.isEmpty()) {
                    products
                } else {
                    val resultList = ArrayList<ProductInfo>()
                    for (product in filterList) {
                        if (product.productName.lowercase(Locale.ROOT).contains(searchKey)) {
                            resultList.add(product)
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
                filterList = results?.values as ArrayList<ProductInfo>
                notifyDataSetChanged()
            }
        }
    }

    private fun genericNameFromJson(json: String): String {
        val gson = GsonBuilder().create()
        var elementName = ""
        if (json == "" || TextUtils.isEmpty(json)) {
            return elementName
        }

        val jsonArray = gson.fromJson(json, Array<ProductElement>::class.java).toList()

        for (item in jsonArray) {
            elementName += " | ${item.elementName}"
        }

        return elementName
    }

}