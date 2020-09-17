package com.zhkj.smartpolice.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import kotlinx.android.synthetic.main.item_meal_order_detail.view.*

class MealOrderDetailAdapter() :
    BaseRecycleAdapter<MealRecordBean.OrdersLinkEntityList>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(R.layout.item_meal_order_detail, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        getData(position).let {
            GlideApp.with(context)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it.goodsImgId)
                .placeholder(R.drawable.svg_default_image)
                .into(holder.itemView.iv_icon)

            holder.itemView.tv_title.text = it.goodsName
            holder.itemView.tv_price.text = ("¥ " + it.price)
            holder.itemView.tv_count.text = ("x " + it.piece)

        }


    }
}