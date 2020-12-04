package com.zhkj.smartpolice.mine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean
import kotlinx.android.synthetic.main.item_reserve_record.view.*

/**
 * 预约记录
 */
class ReverseRecordAdapter : BaseRecycleAdapter<ReserveRecordBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_reserve_record, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_shop_name.text = getData(position).shopName
        holder.itemView.tv_merchant.text = (getData(position).resourceEntity?.resourceName?:"")
        holder.itemView.tv_order_name.text = ("预约人：${getData(position).reserveUserName}")
        holder.itemView.tv_order_time.text = ("预约时间：${getData(position).reserveTime}")
        holder.itemView.tv_order_number.text = ("订单号：${getData(position).reserveNumber ?: ""}")
        holder.itemView.tv_create_time.text = ("创建时间：${getData(position).createTime}")
        holder.itemView.tv_remark.text = (getData(position).remark ?: "")
    }

}