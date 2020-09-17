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
import kotlinx.android.synthetic.main.item_meal_order.view.*

class MealOrderAdapter(private var onUpdateListener: OnUpdateListener, list: ArrayList<MealBean>) :
    BaseRecycleAdapter<MealBean>(list) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(R.layout.item_meal_order, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        getData(position).shopGoodsEntity?.let {
            GlideApp.with(context)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it.imageId)
                .placeholder(R.drawable.svg_default_image)
                .into(holder.itemView.iv_icon)

            holder.itemView.tv_title.text = it.goodsName
            holder.itemView.tv_price.text = ("¥ " + it.price)
        }

        holder.itemView.et_count.setText(getData(position).count.toString())

        holder.itemView.iv_cut.setOnClickListener {

            if (getData(position).count == 1) {
                deleteData(position)
            } else {
                getData(position).count -= 1
            }
            notifyDataSetChanged()
            onUpdateListener.onUpdate()
        }

        holder.itemView.iv_add.setOnClickListener {
            getData(position).count += 1
            notifyDataSetChanged()
            onUpdateListener.onUpdate()
        }

        holder.itemView.checkbox.isChecked = getData(position).isChecked

        holder.itemView.checkbox.setOnCheckedChangeListener { _, isChecked ->
            getData(position).isChecked = isChecked
            onUpdateListener.onUpdate()
        }

    }

    interface OnUpdateListener {
        fun onUpdate()
    }
}