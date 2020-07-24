package com.zhkj.smartpolice.maintain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.bean.FindImagePathBean
import kotlinx.android.synthetic.main.item_maintain_photograph.view.*


class MaintainPhotographAdapter(groupId: ArrayList<FindImagePathBean>) : BaseRecycleAdapter<FindImagePathBean>(groupId) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View =
        LayoutInflater.from(context).inflate(R.layout.item_maintain_photograph, parent, false)

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        LogUtil.i("adapter里面的图片========${getData(position)}")
        Glide.with(context)
            .load(UrlConstant.LOAD_IMAGE_PATH_URL + getData(position).id)
            .dontAnimate()
            .placeholder(R.drawable.svg_default_image)
            .into(holder.itemView.iv_maintain_img)
        holder.itemView.iv_maintain_img
    }

}