package com.zhkj.smartpolice.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.wallet.bean.RecordBean
import kotlinx.android.synthetic.main.item_record.view.*

class RecordAdapter : BaseRecycleAdapter<RecordBean>(arrayListOf()) {
    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.tv_name.text = getData(position).body
        holder.itemView.tv_time.text = getData(position).paySuccessTime

        val moneySb = StringBuilder()
        when (getData(position).isRecharge) {
            "1" -> {
                moneySb.append("+")
                holder.itemView.view_icon.setBackgroundResource(R.drawable.svg_record_income)
                holder.itemView.tv_money.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_theme
                    )
                )
            }
            "2" -> {
                moneySb.append("-")
                holder.itemView.view_icon.setBackgroundResource(R.drawable.svg_record_consumption)
                holder.itemView.tv_money.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.color_consumption
                    )
                )
            }
        }
        moneySb.append(getData(position).amount)
        holder.itemView.tv_money.text = moneySb.toString()

    }

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_record, parent, false)
    }
}