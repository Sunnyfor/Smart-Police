package com.zhkj.smartpolice.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.meal.bean.MealBean
import kotlinx.android.synthetic.main.item_meal_goods.view.*

class MealGoodsAdapter(var onClickListener: View.OnClickListener, var isMeal: Boolean = false) : BaseRecycleAdapter<MealBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_meal_goods, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        getData(position).shopGoodsEntity?.let {
            GlideApp.with(context)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it.imageId)
                .placeholder(R.drawable.svg_default_image)
                .into(holder.itemView.iv_image)

            holder.itemView.tv_title.text = it.goodsName
            holder.itemView.tv_price.text = ("￥${it.price}")
        }


        if (isMeal) {
            holder.itemView.iv_select.tag = getData(position)
            holder.itemView.iv_select.visibility = View.VISIBLE
            holder.itemView.iv_select.setOnClickListener(onClickListener)
        }
    }
}