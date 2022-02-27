package me.kzaman.demo_app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.demo_app.R
import me.kzaman.demo_app.data.model.ProductInfo
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.mlDisplayGrandTotal
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.mlDisplaySubTotalPrice
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.mlDisplayTotalVat
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.mlGrandTotal
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.mlSubTotalPrice
import me.kzaman.demo_app.ui.viewModel.OrderViewModel.Companion.mlTotalVat
import me.kzaman.demo_app.utils.Constants.Companion.DISCOUNT_OFFER_TYPE
import me.kzaman.demo_app.utils.Constants.Companion.DISCOUNT_PCT_OFFER_TYPE
import me.kzaman.demo_app.utils.doubleValueFormat
import me.kzaman.demo_app.utils.numberFormat

open class CartConfirmationAdapter(
    var products: ArrayList<ProductInfo>,
    val mContext: Context,
) : RecyclerView.Adapter<CartConfirmationAdapter.ViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(productList: List<ProductInfo>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName: TextView = view.findViewById(R.id.tv_product_name)
        val tvPrice: TextView = view.findViewById(R.id.tv_unit_price)
        val tvQuantity: TextView = view.findViewById(R.id.tv_product_qty)
        val tvTotalPrice: TextView = view.findViewById(R.id.tv_total_price)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.model_selected_cart_item, parent, false)
    )

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        product.totalPrice = getUnitPrice(product) * product.quantity
        holder.tvProductName.text = product.productName
        holder.tvPrice.text = "Unit price: ${getUnitPrice(product)}"
        holder.tvQuantity.text = product.quantity.toString()
        holder.tvTotalPrice.text = numberFormat(product.totalPrice)
        calculateGrandTotalPrice()
    }

    override fun getItemCount() = products.size

    private fun getUnitPrice(model: ProductInfo): Double {
        var unitPrice = model.baseTp
        try {
            if (model.offerType == DISCOUNT_PCT_OFFER_TYPE || model.offerType == DISCOUNT_OFFER_TYPE) {
                if (!TextUtils.isEmpty(model.startDate) && !TextUtils.isEmpty(model.validUntil) && model.quantity >= model.minimumQty) {
                    unitPrice = model.mtp - model.baseVat
                }
            }
        } catch (ex: Exception) {
            Log.e("exception", ex.toString())
        }

        return numberFormat(unitPrice).toDouble()
    }

    /**
     * ...calculate sub total of selected product list
     * ...calculate total based on quantity * base price
     * ...set calculated price in mutable live data
     */
    private fun calculateGrandTotalPrice() {
        var subTotal = 0.0
        var totalVat = 0.0
        products.forEach { item ->
            subTotal += item.totalPrice
            totalVat += (item.baseVat * item.quantity)
        }
        mlSubTotalPrice.value = doubleValueFormat(subTotal)
        mlTotalVat.value = doubleValueFormat(totalVat)
        mlGrandTotal.value = doubleValueFormat(subTotal + totalVat)

        mlDisplaySubTotalPrice.value = numberFormat(subTotal)
        mlDisplayTotalVat.value = numberFormat(totalVat)
        mlDisplayGrandTotal.value = numberFormat(subTotal + totalVat)
    }
}