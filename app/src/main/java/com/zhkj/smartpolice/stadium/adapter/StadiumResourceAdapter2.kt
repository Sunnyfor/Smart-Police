package com.zhkj.smartpolice.stadium.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.haircut.bean.ManageBean


class StadiumResourceAdapter2(var classifyId: String) : BaseRecycleAdapter<ManageBean>(arrayListOf()) {

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.findViewById<ImageView>(R.id.iv_image)?.let { imageView ->
            getData(position).imageId?.let {
                if (it.isNotEmpty()) {
                    GlideApp.with(context)
                        .load(UrlConstant.LOAD_IMAGE_PATH_URL + it)
                        .into(imageView)
                }
            }
        }

        if (classifyId == "3") {
            holder.itemView.findViewById<TextView>(R.id.tv_time).text = getData(position).resourceContext
        } else {
            holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).resourceName
            holder.itemView.findViewById<TextView>(R.id.tv_hint).text = getData(position).resourceContext
        }
    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(getItemViewType(viewType), parent, false)
    }

    override fun getItemViewType(position: Int): Int {
        if (classifyId == "3") {
            return R.layout.item_bathhouse_resource
        }
        return R.layout.item_stadium_reserve_resource2
    }
}