package com.zhkj.smartpolice.maintain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.bean.MaintainAccompListBean
import kotlinx.android.synthetic.main.item_maintain_audit.view.*


class MaintainAccompListAdapter(info: ArrayList<MaintainAccompListBean>,var isType: Boolean) :
    BaseRecycleAdapter<MaintainAccompListBean>(info) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(R.layout.item_maintain_audit, parent,false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_apply_date.text = getData(position).createTime
        getData(position).applyEntity?.let {
            holder.itemView.tv_dept_name.text = it.deptName
            holder.itemView.tv_goods.text = it.shopGoodsName
            holder.itemView.tv_style_font_black_small.text = it.applyContent
        }
        if (isType){
            holder.itemView.tv_audit_status.text = "已处理"
            holder.itemView.tv_audit_status.setBackgroundResource(R.drawable.sel_maintain_audit_type_background_bn)
        } else {
            holder.itemView.tv_audit_status.text = "待审核"
            holder.itemView.tv_audit_status.setBackgroundResource(R.drawable.sel_maintain_audit_type_background)
        }
    }

}