package com.zhkj.smartpolice.stadium.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.bean.MerchantTime
import kotlinx.android.synthetic.main.item_stadium_reserve_resource.view.*


class StadiumResourceAdapter : BaseRecycleAdapter<MerchantTime>(arrayListOf()) {

    var index = -1
    var lastCheckBox: CheckBox? = null

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_name.text = getData(position).resourceName
        val count = (getData(position).setNumber - getData(position).reserveNumber)

        if (count > 0) {
            if (index == -1) {
                index = position
                lastCheckBox = holder.itemView.checkbox
            }
            holder.itemView.checkbox.isClickable = true
            holder.itemView.tv_count.setTextColor(ContextCompat.getColor(context, R.color.font_orange))
        } else {
            holder.itemView.checkbox.isClickable = false
            holder.itemView.tv_count.setTextColor(ContextCompat.getColor(context, R.color.font_gray))
        }

        holder.itemView.checkbox.isChecked = index == position

        holder.itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->

            lastCheckBox?.let {
                if (it !=  holder.itemView.checkbox)
                it.isChecked = false
            }

            if (isChecked) {
                lastCheckBox = holder.itemView.checkbox

                index = position
            } else {
                if (index == position){
                    index = -1
                }
            }
        }

        holder.itemView.tv_count.text = count.toString()
    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_stadium_reserve_resource, parent, false)
    }
}