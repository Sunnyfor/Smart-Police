package com.zhkj.smartpolice.haircut.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.haircut.bean.WeekDayBean
import kotlinx.android.synthetic.main.item_haircut_week_day.view.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/13 18:21
 */
class HaircutWeekAdapter(var currentDay: Int, list: ArrayList<WeekDayBean>) : BaseRecycleAdapter<WeekDayBean>(list) {

    var index = 0

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        if (currentDay == getData(position).day) {
            index = position
            holder.itemView.tv_week.setTextColor(ContextCompat.getColor(context, R.color.color_theme))
            holder.itemView.tv_day.setTextColor(ContextCompat.getColor(context, R.color.color_theme))
        } else {
            holder.itemView.tv_week.setTextColor(ContextCompat.getColor(context, R.color.font_gray))
            holder.itemView.tv_day.setTextColor(ContextCompat.getColor(context, R.color.font_gray))
        }
        holder.itemView.tv_week.text = getData(position).week
        holder.itemView.tv_day.text = getData(position).day.toString()

    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_haircut_week_day, parent, false)
    }
}