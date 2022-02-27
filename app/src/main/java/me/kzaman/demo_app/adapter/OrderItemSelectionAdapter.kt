package me.kzaman.demo_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.View
import androidx.core.text.HtmlCompat
import me.kzaman.demo_app.data.model.ProductInfo
import me.kzaman.demo_app.ui.view.fragments.orders.CustomerSelectionFragment.Companion.isCartItemsChanged
import me.kzaman.demo_app.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.cartItemCounter
import me.kzaman.demo_app.utils.Constants.Companion.BONUS_OFFER_TYPE
import me.kzaman.demo_app.utils.Constants.Companion.DISCOUNT_OFFER_TYPE
import me.kzaman.demo_app.utils.Constants.Companion.DISCOUNT_PCT_OFFER_TYPE
import me.kzaman.demo_app.utils.Constants.Companion.FREE_OFFER_TYPE
import me.kzaman.demo_app.utils.compareDatesWithCurrentDate
import me.kzaman.demo_app.utils.genericNameFromJson
import me.kzaman.demo_app.utils.isProductAlreadyExist
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

        val mtp: String =
            if (product.offerType == DISCOUNT_OFFER_TYPE || product.offerType == DISCOUNT_PCT_OFFER_TYPE) {
                if (!TextUtils.isEmpty(product.startDate) && !TextUtils.isEmpty(product.validUntil)) {
                    if (compareDatesWithCurrentDate(product.validUntil, product.startDate))
                        "<b><font color='#009C32'>MTP: " + product.mtp + "</font><b> | " + offerDescription
                    else "TP: <font color='#757575'>" + (product.baseTp + product.baseVat) + "</font>"
                } else "TP: <font color='#757575'>" + (product.baseTp + product.baseVat) + "</font>"
            } else if (product.offerType == FREE_OFFER_TYPE || product.offerType == BONUS_OFFER_TYPE) {
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
                product.quantity++
            } else {
                product.quantity = 1
                selectedProduct.add(product)
                product.isProductSelected = true
                isCartItemsChanged = true
                cartItemCounter.value = selectedProduct.size.toString()
            }
            notifyDataSetChanged()
        }
    }
}