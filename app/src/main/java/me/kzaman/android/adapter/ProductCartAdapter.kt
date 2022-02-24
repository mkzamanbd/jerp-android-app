package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.android.R
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.cartItemCounter
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.mlDisplayGrandTotal
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.mlDisplaySubTotalPrice
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.mlDisplayTotalVat
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.mlGrandTotal
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.mlSubTotalPrice
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.mlTotalVat
import me.kzaman.android.utils.Constants.Companion.DISCOUNT_OFFER_TYPE
import me.kzaman.android.utils.Constants.Companion.DISCOUNT_PCT_OFFER_TYPE
import me.kzaman.android.utils.doubleValueFormat
import me.kzaman.android.utils.numberFormat

open class ProductCartAdapter(
    var products: ArrayList<ProductInfo>,
    val mContext: Context,
) : RecyclerView.Adapter<ProductCartAdapter.ViewHolder>() {


    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(productList: List<ProductInfo>) {
        products.clear()
        products.addAll(productList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProductName: TextView = view.findViewById(R.id.tv_product_name)
        val tvPrice: TextView = view.findViewById(R.id.tv_unit_price)
        val etQuantity: EditText = view.findViewById(R.id.et_product_qty)
        val ivQtyIncrease: ImageView = view.findViewById(R.id.quantity_increase)
        val ivQtyDecrease: ImageView = view.findViewById(R.id.quantity_decrease)
        val ivRemoveProduct: ImageView = view.findViewById(R.id.cancel_product)
        val tvTotalPrice: TextView = view.findViewById(R.id.tv_total_price)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.model_product_cart, parent, false)
    )

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        product.totalPrice = getUnitPrice(product) * product.quantity
        holder.tvProductName.text = product.productName
        holder.tvPrice.text = "Unit price: ${getUnitPrice(product)}"
        holder.etQuantity.setText(product.quantity.toString())
        holder.tvTotalPrice.text = numberFormat(product.totalPrice)
        calculateGrandTotalPrice()

        holder.ivQtyDecrease.setOnClickListener {
            product.quantity = product.quantity - 1
            product.totalPrice =
                doubleValueFormat(product.quantity * getUnitPrice(products[position])).toDouble()
            notifyDataSetChanged()
            calculateGrandTotalPrice()
        }
        holder.ivQtyIncrease.setOnClickListener {
            product.quantity = product.quantity + 1
            product.totalPrice =
                doubleValueFormat(product.quantity * getUnitPrice(products[position])).toDouble()
            notifyDataSetChanged()
            calculateGrandTotalPrice()
        }

        holder.etQuantity.addTextChangedListener {
            val quantity = it.toString()
            try {
                if (quantity.isNotEmpty()) {
                    //while quantity field is not empty and quantity is not 0
                    if (quantity == "0" || quantity.toInt() <= 0) {
                        holder.etQuantity.setText("1")
                        products[position].quantity = 1
                    } else products[position].quantity = quantity.toInt()
                } else products[position].quantity = 1
            } catch (ex: Exception) {
                products[position].quantity = 1
                holder.etQuantity.setText("1")
            } finally {
                products[position].totalPrice =
                    doubleValueFormat(products[position].quantity * getUnitPrice(products[position])).toDouble()
                holder.tvTotalPrice.text = numberFormat(products[position].totalPrice)
                calculateGrandTotalPrice()
            }
        }

        holder.ivRemoveProduct.setOnClickListener {
            if (products.size > 1) {
                products.removeAt(position)
                selectedProduct.remove(product)
                cartItemCounter.value = products.size.toString()
                notifyDataSetChanged()
                calculateGrandTotalPrice()
            }
        }
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

        return unitPrice
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