package com.zhkj.smartpolice.stadium.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.item_bathhouse_resource.view.*

/**
 * Desc 澡堂adapter
 */
class BathhouseResourceAdapter : BaseRecycleAdapter<Any>(arrayListOf()) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_bathhouse_resource, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.iv_image
        holder.itemView.tv_time
    }
}