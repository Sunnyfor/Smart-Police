package com.zhkj.smartpolice.merchant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R

class MerchantAdapter(val shopType: String) : BaseRecycleAdapter<MerchantBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        val layoutId = when (shopType) {
            MerchantListActivity.TYPE_RESTAURANT -> R.layout.item_restaurant
            MerchantListActivity.TYPE_HAIRCUT -> R.layout.item_barber_list
            MerchantListActivity.TYPE_STADIUM -> R.layout.item_stadium_list
            else -> R.layout.item_restaurant
        }
        return LayoutInflater.from(context).inflate(layoutId, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        when (shopType) {
            MerchantListActivity.TYPE_RESTAURANT -> {
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
                holder.itemView.findViewById<TextView>(R.id.tv_remark).text = getData(position).remark
                holder.itemView.findViewById<TextView>(R.id.tv_desc).text =
                    ("起送￥${getData(position).buffetPrice}，配送￥${getData(position).subsidyPrice}")
            }
            MerchantListActivity.TYPE_HAIRCUT -> {
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
            }

            MerchantListActivity.TYPE_STADIUM -> {
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
                holder.itemView.findViewById<TextView>(R.id.tv_count).text = "0"
            }

            else -> {
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
                holder.itemView.findViewById<TextView>(R.id.tv_remark).text = getData(position).remark
                holder.itemView.findViewById<TextView>(R.id.tv_desc).text =
                    ("起送￥${getData(position).buffetPrice}，配送￥${getData(position).subsidyPrice}")
            }
        }

    }
}