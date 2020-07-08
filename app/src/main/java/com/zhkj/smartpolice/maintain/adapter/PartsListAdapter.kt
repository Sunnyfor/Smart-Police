package com.zhkj.smartpolice.maintain.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.bean.Goods
import kotlinx.android.synthetic.main.item_parts_list.view.*


class PartsListAdapter(goods: ArrayList<Goods>) : BaseRecycleAdapter<Goods>(goods) {


    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(
            R.layout.item_parts_list, parent, false
        )

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
//        holder.itemView.im_article_img
        getData(position).imageId?.let {
            LogUtil.i("图片ID=====${getData(position).imageId}")
            Glide.with(context).load(UrlConstant.ACCESSORY + "?attID=" + getData(position).imageId)
                .placeholder(R.drawable.svg_default_image).into(holder.itemView.im_article_img)
        }
        holder.itemView.tv_article_name.text = getData(position).goodsName
    }

}