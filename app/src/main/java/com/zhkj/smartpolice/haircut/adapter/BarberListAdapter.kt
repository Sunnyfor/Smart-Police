package com.zhkj.smartpolice.haircut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.haircut.bean.ManageBean
import kotlinx.android.synthetic.main.item_barber_list.view.*

/**
 * 理发师列表（领导）
 */
class BarberListAdapter : BaseRecycleAdapter<ManageBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_barber_list, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        GlideApp.with(context)
            .load(UrlConstant.LOAD_IMAGE_PATH_URL + getData(position).imageId)
            .placeholder(R.drawable.svg_default_head)
            .into(holder.itemView.iv_head)

        holder.itemView.tv_name.text = getData(position).resourceName
    }

}