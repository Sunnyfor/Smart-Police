package com.zhkj.smartpolice.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import kotlinx.android.synthetic.main.item_meal_menu.view.*

class MealMenuAdapter(list: ArrayList<MealMenuBean>) : BaseRecycleAdapter<MealMenuBean>(list) {

    var index = 0

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_meal_menu, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_title.text = getData(position).title
        if (getData(position).icon == 0) {
            holder.itemView.iv_icon.visibility = View.GONE
        } else {
            holder.itemView.iv_icon.visibility = View.VISIBLE
        }

        if (position == index){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white))
        }else{
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.color_transparent))
        }
    }
}