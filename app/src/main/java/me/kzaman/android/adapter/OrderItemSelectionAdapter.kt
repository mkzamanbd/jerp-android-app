package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.ui.view.fragments.orders.CustomerSelectionFragment.Companion.isCartItemsChanged
import me.kzaman.android.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.cartItemCounter
import me.kzaman.android.utils.compareDatesWithCurrentDate
import me.kzaman.android.utils.genericNameFromJson
import me.kzaman.android.utils.isProductAlreadyExist
import kotlin.collections.ArrayList

class OrderItemSelectionAdapter(
    products: ArrayList<ProductInfo>,
    mContext: Context,
) : ProductListAdapter(products, mContext) {

    @SuppressLint("NotifyDataSetChanged")
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

        holder.addToCart.visibility = View.VISIBLE
        if (product.isProductSelected) {
            holder.tvQuantity.visibility = View.VISIBLE
            holder.tvQuantity.text = "${product.quantity}"
        } else {
            holder.tvQuantity.visibility = View.GONE
        }
        holder.itemView.setOnClickListener {
            if (selectedProduct.isProductAlreadyExist(product.productId)) {
                product.quantity += 1
                notifyDataSetChanged()
            } else {
                product.quantity = 1
                selectedProduct.add(product)
                product.isProductSelected = true
                isCartItemsChanged = true
                cartItemCounter.value = selectedProduct.size.toString()
                notifyDataSetChanged()
            }

        }
    }
}