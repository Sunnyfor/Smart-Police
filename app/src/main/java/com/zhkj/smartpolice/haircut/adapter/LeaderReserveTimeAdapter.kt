package com.zhkj.smartpolice.haircut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import kotlinx.android.synthetic.main.item_leader_reserve_time.view.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/14 18:30
 */
class LeaderReserveTimeAdapter : BaseRecycleAdapter<MerchantTime>(arrayListOf()) {

    var index = 0

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        if (index == position) {
            holder.itemView.tv_title.setTextColor(ContextCompat.getColor(context, R.color.color_theme))
            holder.itemView.setBackgroundResource(R.drawable.sel_border_theme)
        } else {
            holder.itemView.tv_title.setTextColor(ContextCompat.getColor(context, R.color.font_black))
            holder.itemView.setBackgroundResource(R.drawable.sel_border_black)
        }

        holder.itemView.tv_title.text = getData(position).manageTime

    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_leader_reserve_time, parent, false)
    }

}