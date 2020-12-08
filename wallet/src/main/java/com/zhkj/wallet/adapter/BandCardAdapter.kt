package com.zhkj.wallet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.wallet.R
import com.zhkj.wallet.bean.BandCardBean
import kotlinx.android.synthetic.main.item_band_card.view.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/12/7 11:38
 */
class BandCardAdapter : BaseRecycleAdapter<BandCardBean>(arrayListOf()) {
    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_band_card, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        if (getData(position).isEmpty()) {
            holder.itemView.tv_bank_name.text = ""
            holder.itemView.tv_card_number.text = "添加银行卡"
            holder.itemView.radioButton.visibility = View.GONE
        } else {
            holder.itemView.tv_bank_name.text = getData(position).bankName
            holder.itemView.tv_card_number.text = getData(position).bandCard
            holder.itemView.radioButton.visibility = View.VISIBLE
        }

    }
}