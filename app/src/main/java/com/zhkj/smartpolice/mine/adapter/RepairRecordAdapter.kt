package com.zhkj.smartpolice.mine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.isStrEmpty
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.mine.bean.RepairRecordBean
import kotlinx.android.synthetic.main.item_repair_record.view.*

class RepairRecordAdapter : BaseRecycleAdapter<RepairRecordBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_repair_record, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_apply_title.text = getData(position).applyContent
        holder.itemView.tv_money.text = ("￥ ${isStrEmpty(getData(position).money, "0")}")
        holder.itemView.tv_apply_name.text = ("申请人：${getData(position).petitioner}（${getData(position).petitionerPhone}）")
        holder.itemView.tv_apply_time.text = ("申请时间：${isStrEmpty(getData(position).createTime, "暂无")}")
        holder.itemView.tv_order_number.text = ("订单号：${getData(position).attachmentGroupId ?: ""}")
        holder.itemView.tv_order_time.text = ("( ${isStrEmpty(getData(position).repairRecordEntity?.createTime, "暂无")} )")
        holder.itemView.tv_content.text = isStrEmpty(getData(position).repairRecordEntity?.content, "暂无")
    }

}