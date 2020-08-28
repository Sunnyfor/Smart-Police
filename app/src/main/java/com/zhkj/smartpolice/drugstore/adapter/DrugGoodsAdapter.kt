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
import com.sunny.zy.utils.GlideApp
import com.sunny.zy.utils.isStrEmpty
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.drugstore.bean.DrugBean
import kotlinx.android.synthetic.main.item_drug_goods.view.*

class DrugGoodsAdapter : BaseRecycleAdapter<DrugBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_drug_goods, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        getData(position).imageId?.let {
            GlideApp.with(context)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it)
                .placeholder(R.drawable.svg_default_image)
                .into(holder.itemView.iv_image)
        }

        holder.itemView.tv_title.text = getData(position).goodsName
        holder.itemView.tv_price.text = ("￥${getData(position).price}")
        holder.itemView.tv_desc.text = isStrEmpty(getData(position).description, "暂无药品简介")

        val str = "库存${isStrEmpty(getData(position).inventory, "0")}份"
        val style = SpannableStringBuilder(str)
        style.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.font_orange)),
            2, str.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        holder.itemView.tv_inventory.text = style
    }
}