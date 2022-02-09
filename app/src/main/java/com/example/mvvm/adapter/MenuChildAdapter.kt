package com.example.mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.response.UserChildMenuModel
import com.example.mvvm.utils.menuRouting

class MenuChildAdapter(
    private var menuItems: List<UserChildMenuModel>,
    val context: Context,
) : RecyclerView.Adapter<MenuChildAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val ivImage: ImageView = view.findViewById(R.id.iv_image)
        private val lnRoot: LinearLayout = view.findViewById(R.id.ln_root)
        fun bind(menuModel: UserChildMenuModel) {
            tvTitle.text = menuModel.menuName
            ivImage.setImageDrawable(ContextCompat.getDrawable(context, menuModel.iconId))

            lnRoot.setOnClickListener {
                menuRouting(context, menuModel.featureId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.child_menu_list, parent, false)
    )

    override fun onBindViewHolder(holder: MenuChildAdapter.ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount() = menuItems.size

}