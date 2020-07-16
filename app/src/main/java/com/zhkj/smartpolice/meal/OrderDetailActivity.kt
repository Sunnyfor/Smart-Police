package com.zhkj.smartpolice.meal

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.isStrEmpty
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.wallet.utils.PayPasswordUtil
import kotlinx.android.synthetic.main.act_order_detail.*

class OrderDetailActivity : BaseActivity() {

    private val payUtil = PayPasswordUtil(btn_commit, this)

    private var ordersId = ""

    override fun setLayout(): Int = R.layout.act_order_detail

    override fun initView() {

        defaultTitle("订单详情")

        recyclerView.layoutManager = LinearLayoutManager(this)


        setOnClickListener(btn_commit)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_commit.id -> {
//                payUtil.isSettingPayPassword(ordersId,)
            }
        }

    }

    override fun loadData() {
        ZyFrameStore.getData<MealRecordBean>(MealRecordBean::class.java.simpleName, true)?.let {
            ordersId = it.ordersId.toString()
            tv_shop_name.text = it.shopName
            tv_order_time.text = it.ordersNumber
            tv_order_creator.text = ("${it.createUserName} ${it.mobile}")
            tv_order_number.text = ("订单号：${it.ordersNumber}")
            tv_shopping_fee.text = ("配送费：${it.shippingFee}")
            tv_total_price.text = isStrEmpty(it.totalPrice, "")
            tv_subsidy_price.text = isStrEmpty(it.subsidyPrice, "")
            tv_pay_price.text = isStrEmpty(it.payPrice, "")
        }
    }

    override fun close() {

    }
}