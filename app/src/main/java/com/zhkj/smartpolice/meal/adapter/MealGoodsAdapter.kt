package com.zhkj.smartpolice.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.item_meal_goods.view.*

class MealGoodsAdapter(var onClickListener: View.OnClickListener) : BaseRecycleAdapter<MealGoodsBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_meal_goods, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        GlideApp.with(context)
            .load(UrlConstant.LOAD_IMAGE_PATH_URL + getData(position).imageId)
            .placeholder(R.drawable.svg_default_image)
            .into(holder.itemView.iv_image)

        holder.itemView.tv_title.text = getData(position).goodsName
        holder.itemView.tv_price.text = ("￥${getData(position).price}")

        holder.itemView.iv_select.tag = getData(position)
        holder.itemView.iv_select.setOnClickListener(onClickListener)

    }
}