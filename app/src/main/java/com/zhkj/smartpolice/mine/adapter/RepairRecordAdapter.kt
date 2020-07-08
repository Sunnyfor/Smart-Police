package com.zhkj.smartpolice.mine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.mine.bean.RepairRecordBean
import kotlinx.android.synthetic.main.item_repair_record.view.*

class RepairRecordAdapter : BaseRecycleAdapter<RepairRecordBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_reserve_record, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_apply_title.text = getData(position).applyContent
        holder.itemView.tv_money.text = ("￥ ${getData(position).money}")
        holder.itemView.tv_apply_name.text = ("申请人：${getData(position).petitioner}（${getData(position).petitionerPhone}）")
        holder.itemView.tv_apply_time.text = ("申请时间：${getData(position).createTime}")
        holder.itemView.tv_order_number.text = ("订单号：${getData(position).attachmentGroupId ?: ""}")
        holder.itemView.tv_create_time.text = ("创建时间：${getData(position).repairRecordEntity?.createTime}")
        holder.itemView.tv_content.text = ("维修内容：${getData(position).repairRecordEntity?.content ?: ""}")
    }

}