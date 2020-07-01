package com.zhkj.smartpolice.drugstore.adapter

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.item_drug_goods.view.*

class DrugGoodsAdapter(list: ArrayList<MealGoodsBean>) : BaseRecycleAdapter<MealGoodsBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_drug_goods, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_title.text = getData(position).title
        holder.itemView.tv_price.text = getData(position).price
        holder.itemView.tv_desc.text = getData(position).weight

        val str = "库存${getData(position).count}份"
        val style = SpannableStringBuilder(str)
        style.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.font_orange)),
            2, str.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.itemView.tv_inventory.text = style
    }
}