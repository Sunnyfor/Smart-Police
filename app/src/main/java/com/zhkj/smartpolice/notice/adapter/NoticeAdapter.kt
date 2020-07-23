package com.zhkj.smartpolice.notice.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.notice.bean.NoticeBean
import kotlinx.android.synthetic.main.item_notice.view.*

class NoticeAdapter : BaseRecycleAdapter<NoticeBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {

        return LayoutInflater.from(context).inflate(R.layout.item_notice, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_name.text = getData(position).noticeTitle
        holder.itemView.tv_desc.text = getData(position).noticeValue
        holder.itemView.tv_time.text = getData(position).createDate

        if (getData(position).isRead == "1") {

            holder.itemView.view_point.visibility = View.GONE

        } else if (getData(position).isRead == "2") {

            holder.itemView.view_point.visibility = View.VISIBLE

        }
    }

}