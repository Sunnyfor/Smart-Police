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
import java.text.SimpleDateFormat

class RepairRecordAdapter : BaseRecycleAdapter<RepairRecordBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_repair_record, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_apply_title.text = getData(position).applyContent
        holder.itemView.tv_apply_name.text = ("申请人：${getData(position).petitioner}（${getData(position).petitionerPhone}）")
        holder.itemView.tv_apply_time.text = ("预约时间：${getDate(isStrEmpty(getData(position).applyDate))}")
        holder.itemView.tv_order_number.text = ("维修状态：${setType(getData(position).applyState?.toInt())}")
        holder.itemView.tv_order_time.text = ("维修日期： ${isStrEmpty(getData(position).repairRecordEntity?.finishDate, "")} ")
        holder.itemView.tv_content.text = isStrEmpty(getData(position).repairRecordEntity?.content, "暂无")
        val repairType = if (getData(position).repairType == "1") "维修" else "换件"
        holder.itemView.tv_repair_type.text = ("维修类型： $repairType")
    }

    private fun setType(type: Int?): String {
        var strType: String? = null
        when (type) {
            1 -> strType = "审核中"

            2 -> strType = "审核通过"

            3 -> strType = "审核驳回"
        }
        return strType ?: ""
    }

    private fun getDate(str: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        var date = formatter.parse(str)
        return SimpleDateFormat("yyyy-MM-dd").format(date)
    }
}