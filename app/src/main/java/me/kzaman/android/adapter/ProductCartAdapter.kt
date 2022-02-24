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
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.android.R
import me.kzaman.android.data.model.ProductInfo
import me.kzaman.android.ui.view.fragments.orders.ProductSelectionFragment.Companion.selectedProduct
import me.kzaman.android.ui.viewModel.OrderViewModel.Companion.cartItemCounter
import me.kzaman.android.utils.Constants.Companion.DISCOUNT_OFFER_TYPE
import me.kzaman.android.utils.Constants.Companion.DISCOUNT_PCT_OFFER_TYPE
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
        holder.tvPrice.text = "Unit price: ${product.baseTp}"
        holder.etQuantity.setText(product.quantity.toString())
        holder.tvTotalPrice.text = numberFormat(product.totalPrice)


        holder.ivQtyDecrease.setOnClickListener {
            if (product.quantity > 1) {
                product.quantity--
                notifyDataSetChanged()
            }
        }
        holder.ivQtyIncrease.setOnClickListener {
            product.quantity++
            notifyDataSetChanged()
        }

        holder.etQuantity.addTextChangedListener {
            val quantity = it.toString()
            if (quantity.isNotEmpty() && quantity.toInt() > 0) {
                product.quantity = quantity.toInt()
            } else {
                product.quantity = 1
                holder.etQuantity.setText("1")
            }
        }
        holder.ivRemoveProduct.setOnClickListener {
            if (products.size > 1) {
                products.removeAt(position)
                selectedProduct.remove(product)
                cartItemCounter.value = products.size.toString()
                notifyDataSetChanged()
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
}