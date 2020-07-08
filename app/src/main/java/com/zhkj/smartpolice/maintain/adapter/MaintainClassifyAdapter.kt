package com.zhkj.smartpolice.maintain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.bean.MaintainListBean
import kotlinx.android.synthetic.main.item_maintain_classify.view.*


class MaintainClassifyAdapter(info: ArrayList<MaintainListBean>) :
    BaseRecycleAdapter<MaintainListBean>(info) {


    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(
            R.layout.item_maintain_classify, parent, false
        )

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_title.text = getData(position).labelName
    }
}