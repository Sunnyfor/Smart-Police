package com.zhkj.smartpolice.maintain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.bean.MaintainerListBean
import kotlinx.android.synthetic.main.item_maintainer_list.view.*

class MaintainerListAdapter(info: ArrayList<MaintainerListBean>) : BaseRecycleAdapter<MaintainerListBean>(info) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(R.layout.item_maintainer_list, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_maintainer_name.text = getData(position).nickName

        if (getData(position).isCheck) {
            holder.itemView.rb_select_button.setImageResource(R.drawable.svg_check)
        } else {
            holder.itemView.rb_select_button.setImageResource(R.drawable.svg_check_nor)
        }
    }
}
