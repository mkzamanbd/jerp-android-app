package me.kzaman.android.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.android.R
import me.kzaman.android.data.model.ProductInfo

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
        val tvQty: TextView = view.findViewById(R.id.tv_product_qty)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.model_product_cart, parent, false)
    )

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.tvProductName.text = product.productName
        holder.tvPrice.text = "Unit price: ${product.baseTp}"
        holder.tvQty.text = product.quantity.toString()

        holder.itemView.setOnClickListener {
            Toast.makeText(mContext, product.productId, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = products.size
}