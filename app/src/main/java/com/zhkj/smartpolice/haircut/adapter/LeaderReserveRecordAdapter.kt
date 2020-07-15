package com.zhkj.smartpolice.haircut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean
import kotlinx.android.synthetic.main.item_leader_reserve_record.view.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/15 15:29
 */
class LeaderReserveRecordAdapter : BaseRecycleAdapter<ReserveRecordBean>(arrayListOf()) {
    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_name.text = getData(position).reserveUserName
        holder.itemView.tv_time.text = getData(position).reserveTime
    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
       return LayoutInflater.from(context).inflate(R.layout.item_leader_reserve_record,parent,false)
    }
}