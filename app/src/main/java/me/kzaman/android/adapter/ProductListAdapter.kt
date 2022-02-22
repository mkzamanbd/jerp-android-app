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
import me.kzaman.android.R
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.utils.compareDatesWithCurrentDate
import me.kzaman.android.utils.genericNameFromJson
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

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tv_title)
        val tvDetails: TextView = view.findViewById(R.id.tv_details)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = filterList[position]

        val packSize = "<small><b><font color='#696A6A'>(${product.packSize})</font><b/></small>"
        val offerText = "<b><font color='#009C32'>${product.offer}</font><b/>"
        val offerDescription = "<b><font color='#757575'>${product.offerDescription}</font><b/>"

        val genericName: String = genericNameFromJson(product.elements).ifEmpty { "" }

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

        holder.tvTitle.text = HtmlCompat.fromHtml("${product.productName} $packSize",
            HtmlCompat.FROM_HTML_MODE_LEGACY)
        var tvDetailHtml = product.productCode
        if (!product.comPackDesc.isNullOrEmpty()) {
            tvDetailHtml += " | ${product.comPackDesc}"
        }
        tvDetailHtml += "$genericName | $mtp"

        holder.tvDetails.text = HtmlCompat.fromHtml(tvDetailHtml, HtmlCompat.FROM_HTML_MODE_LEGACY)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, product.productId, Toast.LENGTH_SHORT).show()
        }
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
}