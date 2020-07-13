package com.zhkj.smartpolice.haircut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import kotlinx.android.synthetic.main.item_merchant_time.view.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/13 19:22
 */
class HaircutTimeAdapter() : BaseRecycleAdapter<MerchantTime>(arrayListOf()) {

    var index = 0

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        val number = getData(position).setNumber - getData(position).reserveNumber

        if (number > 0) {
            holder.itemView.tv_status.text = "可预约"
            if (index == position){
                holder.itemView.tv_status.setTextColor(ContextCompat.getColor(context,R.color.font_orange))
                holder.itemView.tv_time.setTextColor(ContextCompat.getColor(context,R.color.font_orange))
                holder.itemView.setBackgroundResource(R.drawable.sel_border_orange)
            }else{
                holder.itemView.tv_status.setTextColor(ContextCompat.getColor(context,R.color.font_black))
                holder.itemView.tv_time.setTextColor(ContextCompat.getColor(context,R.color.font_black))
                holder.itemView.setBackgroundResource(R.drawable.sel_border_black)
            }

        } else {
            holder.itemView.tv_status.text = "已约满"
            holder.itemView.tv_status.setTextColor(ContextCompat.getColor(context,R.color.font_gray))
            holder.itemView.tv_time.setTextColor(ContextCompat.getColor(context,R.color.font_gray))

            holder.itemView.setBackgroundResource(R.drawable.sel_border_gray)
        }

        holder.itemView.tv_time.text = getData(position).manageTime
    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_merchant_time, parent, false)
    }
}