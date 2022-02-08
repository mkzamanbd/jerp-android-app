package com.example.mvvm.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm.R
import com.example.mvvm.data.response.UserParentMenuModel


class MenuParentAdapter(
    var menuItems: ArrayList<UserParentMenuModel>,
    private val context: Context,
) : RecyclerView.Adapter<MenuParentAdapter.ViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setHomePrentMenu(menuModel: List<UserParentMenuModel>) {
        menuItems.clear()
        menuItems.addAll(menuModel)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val rvList: RecyclerView = view.findViewById(R.id.child_menu_list)


        fun bind(menuModel: UserParentMenuModel) {
            tvTitle.text = menuModel.menuName
            val childMenuAdapter = MenuChildAdapter(menuModel.menuItems)
            horizontalColumnRecyclerView(context, rvList, 4).adapter = childMenuAdapter
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.user_menu_item, parent, false)
    )

    override fun onBindViewHolder(holder: MenuParentAdapter.ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount() = menuItems.size

    fun horizontalColumnRecyclerView(
        context: Context?,
        recyclerView: RecyclerView,
        column: Int,
    ): RecyclerView {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, column)
        recyclerView.isNestedScrollingEnabled = false
        return recyclerView
    }
}