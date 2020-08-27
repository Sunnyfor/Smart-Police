package com.zhkj.smartpolice.shuttle.adapter

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.merchant.MerchantBean
import kotlinx.android.synthetic.main.item_shuttle_bus.view.*

class ShuttleBusAdapter : BaseRecycleAdapter<MerchantBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_shuttle_bus, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        getData(position).imageId?.let {
            GlideApp.with(context)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it)
                .placeholder(R.drawable.svg_default_image)
                .into(holder.itemView.iv_image)
        }

        holder.itemView.tv_driver.text = ("司 \t 机：${getData(position).userName ?: ""}")
        holder.itemView.tv_time.text = ("发车时间：${getData(position).businessTime?.trim()}")
        holder.itemView.tv_phone.text = ("联系方式：${getData(position).mobilePhone ?: ""}")
        holder.itemView.tv_backup.text = getData(position).remark ?: ""

        val str = "车牌号：${getData(position).shopName}"
        val style = SpannableStringBuilder(str)
        style.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_theme)),
            4, str.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.itemView.tv_plate.text = style
    }
}