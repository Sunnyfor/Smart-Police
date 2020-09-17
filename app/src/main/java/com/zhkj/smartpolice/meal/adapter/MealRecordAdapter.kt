package com.zhkj.smartpolice.meal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import kotlinx.android.synthetic.main.item_meal_record.view.*

/**
 * 订餐记录
 */
class MealRecordAdapter : BaseRecycleAdapter<MealRecordBean>(arrayListOf()) {

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_meal_record, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
        holder.itemView.tv_name.text = getData(position).shopName
        holder.itemView.tv_order_state.text = (getPayState(getData(position).payState))
        holder.itemView.tv_price.text = ("￥${getData(position).payPrice}")
        holder.itemView.tv_order_number.text = ("订单号：${getData(position).ordersNumber}")
        holder.itemView.tv_order_time.text = ("下单时间：${getData(position).createTime}")
        holder.itemView.tv_pay_type.text = ("付款方式：${getPayWay(getData(position).payMethod)}")
    }

    private fun getPayState(type: String?): String {
        return when (type?.toInt()) {
            0 -> "待支付"
            1 -> "支付成功"
            2 -> "已关闭"
            3 -> "已撤销"
            4 -> "待退款"
            5 -> "退款成功"
            6 -> "交易结束，不可退款"
            else -> ""
        }
    }

    private fun getPayWay(type: String?): String {
        return when (type?.toInt()) {
            1 -> "支付宝"
            2 -> "微信"
            3 -> "网银"
            4 -> "钱包"
            5 -> "现金"
            6 -> "货到付款"
            else -> ""
        }
    }
}