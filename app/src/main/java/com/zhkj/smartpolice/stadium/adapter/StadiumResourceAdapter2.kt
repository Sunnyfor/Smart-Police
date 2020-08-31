package com.zhkj.smartpolice.stadium.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import kotlinx.android.synthetic.main.item_stadium_reserve_resource.view.*


class StadiumResourceAdapter2 : BaseRecycleAdapter<Any>(arrayListOf()) {

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
//        holder.itemView.tv_name.text = getData(position).resourceName
//        holder.itemView.tv_count.text = getData(position).resourceName
    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_stadium_reserve_resource2, parent, false)
    }
}