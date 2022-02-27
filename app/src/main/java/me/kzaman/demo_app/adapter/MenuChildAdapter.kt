package me.kzaman.demo_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.kzaman.demo_app.R
import me.kzaman.demo_app.data.response.UserChildMenuModel
import me.kzaman.demo_app.utils.menuRouting

class MenuChildAdapter(
    private var menuItems: List<UserChildMenuModel>,
    val mContext: Context,
) : RecyclerView.Adapter<MenuChildAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_title)
        private val ivImage: ImageView = view.findViewById(R.id.iv_image)
        private val lnRoot: LinearLayout = view.findViewById(R.id.ln_root)
        fun bind(menuModel: UserChildMenuModel) {
            tvTitle.text = menuModel.menuName
            ivImage.setImageDrawable(ContextCompat.getDrawable(mContext, menuModel.iconId))

            lnRoot.setOnClickListener {
                menuRouting(mContext, menuModel.featureId)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.model_sub_menu_list, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(menuItems[position])
    }

    override fun getItemCount() = menuItems.size

}