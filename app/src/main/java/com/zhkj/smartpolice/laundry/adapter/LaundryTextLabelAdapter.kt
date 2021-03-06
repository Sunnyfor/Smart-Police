package com.zhkj.smartpolice.laundry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/9/3 17:24
 */
class LaundryTextLabelAdapter : BaseRecycleAdapter<String>(arrayListOf()) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(R.layout.item_label_text_white, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        (holder.itemView as TextView).text = getData(position)
    }
}