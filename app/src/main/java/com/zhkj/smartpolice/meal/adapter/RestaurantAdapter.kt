package com.zhkj.smartpolice.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.RestaurantBean
import kotlinx.android.synthetic.main.item_shop_restaurant.view.*

class RestaurantAdapter : BaseRecycleAdapter<RestaurantBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_shop_restaurant, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_name.text = getData(position).shopName
        holder.itemView.tv_remark.text = getData(position).remark
        holder.itemView.tv_desc.text = ("起送￥${getData(position).buffetPrice}，配送￥${getData(position).subsidyPrice}")
    }
}