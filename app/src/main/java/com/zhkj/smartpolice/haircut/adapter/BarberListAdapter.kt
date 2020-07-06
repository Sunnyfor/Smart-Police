package com.zhkj.smartpolice.haircut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.item_barber_list.view.*

/**
 * 理发师列表（领导）
 */
class BarberListAdapter(list: ArrayList<MealGoodsBean>) : BaseRecycleAdapter<MealGoodsBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_barber_list, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        /* holder.itemView.iv_head
         holder.itemView.tv_name.text = "getData(position).price"*/
        holder.itemView.btn_check.setOnClickListener {

        }

    }
}