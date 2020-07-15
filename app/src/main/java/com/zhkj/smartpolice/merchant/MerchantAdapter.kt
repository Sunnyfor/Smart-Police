package com.zhkj.smartpolice.merchant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant

class MerchantAdapter(val shopType: String) : BaseRecycleAdapter<MerchantBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        val layoutId = when (shopType) {
            MerchantListActivity.TYPE_RESTAURANT -> R.layout.item_shop_restaurant
            MerchantListActivity.TYPE_HAIRCUT -> R.layout.item_barber_list
            MerchantListActivity.TYPE_DRUGSTORE -> R.layout.item_shop_drugstore
            MerchantListActivity.TYPE_STADIUM -> R.layout.item_shop_stadium
            else -> R.layout.item_shop_restaurant
        }
        return LayoutInflater.from(context).inflate(layoutId, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        val shopImgView = holder.itemView.findViewById<ImageView>(R.id.iv_image)
        when (shopType) {
            /**
             * 餐厅
             */
            MerchantListActivity.TYPE_RESTAURANT -> {
                GlideApp.with(context)
                    .load("${UrlConstant.LOAD_IMAGE_PATH_URL}${getData(position).imageId}")
                    .placeholder(R.drawable.svg_default_image)
                    .into(shopImgView)
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
                holder.itemView.findViewById<TextView>(R.id.tv_remark).text = ("${getData(position).remark}，经营${getData(position).scope}")
                holder.itemView.findViewById<TextView>(R.id.tv_time).text = ("营业时间：${getData(position).businessTime}")
                holder.itemView.findViewById<TextView>(R.id.tv_desc).text =
                    ("起送￥${getData(position).buffetPrice}，配送￥${getData(position).subsidyPrice}")
            }
            /**
             * 理发店
             */
            MerchantListActivity.TYPE_HAIRCUT -> {
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
            }
            /**
             * 药店
             */
            MerchantListActivity.TYPE_DRUGSTORE -> {
                GlideApp.with(context)
                    .load("${UrlConstant.LOAD_IMAGE_PATH_URL}${getData(position).imageId}")
                    .placeholder(R.drawable.svg_default_image)
                    .into(shopImgView)
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
                holder.itemView.findViewById<TextView>(R.id.tv_phone).text = ("联系电话：${getData(position).mobilePhone}（${getData(position).userName}）")
                holder.itemView.findViewById<TextView>(R.id.tv_remark).text = getData(position).scope
            }
            /**
             * 运动场
             */
            MerchantListActivity.TYPE_STADIUM -> {
                GlideApp.with(context)
                    .load("${UrlConstant.LOAD_IMAGE_PATH_URL}${getData(position).imageId}")
                    .placeholder(R.drawable.svg_default_head)
                    .into(shopImgView)
                holder.itemView.findViewById<TextView>(R.id.tv_name).text = getData(position).shopName
                holder.itemView.findViewById<TextView>(R.id.tv_count).text = ("经营范围：${getData(position).scope}")
                holder.itemView.findViewById<TextView>(R.id.tv_phone).text = ("联系电话：${getData(position).mobilePhone}（${getData(position).userName}）")
                holder.itemView.findViewById<TextView>(R.id.tv_address).text = ("场地地址：${getData(position).shopAddress}")
                holder.itemView.findViewById<TextView>(R.id.tv_time).text = ("营业时间：${getData(position).businessTime}")

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