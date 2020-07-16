package com.zhkj.smartpolice.meal

import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.isStrEmpty
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_order_detail.*

class OrderDetailActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_order_detail

    override fun initView() {

        defaultTitle("订单详情")

/*
        tv_shop_name.text = bean.shopName
        tv_order_time.text = bean.ordersNumber
        tv_order_creator.text = ("${bean.createUserName} ${bean.mobile}")
        tv_order_number.text = ("订单号：${bean.ordersNumber}")
        tv_shopping_fee.text = ("配送费：${bean.shippingFee}")
        tv_total_price.text = isStrEmpty(bean.totalPrice, "")
        tv_subsidy_price.text = isStrEmpty(bean.subsidyPrice, "")
        tv_pay_price.text = isStrEmpty(bean.payPrice, "")
*/


        setOnClickListener(btn_commit)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_commit.id -> {
            }
        }

    }

    override fun loadData() {

    }

    override fun close() {

    }
}