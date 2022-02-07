package com.example.mvvm.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.model.Product

class ProductListAdapter(
    var products: ArrayList<Product>,
    private val onItemClickListener: OnItemClickListener,
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(product: List<Product>) {
        products.clear()
        products.addAll(product)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private val name: TextView = view.findViewById(R.id.prod_name)
        private val code: TextView = view.findViewById(R.id.code)
        private val packSize: TextView = view.findViewById(R.id.pack_size)

        @SuppressLint("SetTextI18n")
        fun bind(product: Product) {
            name.text = product.productName
            code.text = product.productCode
            packSize.text = "(${product.packSize})"
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.product_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ProductListAdapter.ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount() = products.size

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }


}