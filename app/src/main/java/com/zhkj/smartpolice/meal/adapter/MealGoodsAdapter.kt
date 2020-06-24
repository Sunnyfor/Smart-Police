package com.zhkj.smartpolice.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.item_meal_goods.view.*

class MealGoodsAdapter(list: ArrayList<MealGoodsBean>) : BaseRecycleAdapter<MealGoodsBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_meal_goods, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_title.text = getData(position).title

    }
}