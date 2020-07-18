package com.zhkj.smartpolice.meal

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.wallet.activity.PayResultActivity
import com.zhkj.wallet.utils.PayPasswordUtil
import kotlinx.android.synthetic.main.act_order_detail.*

/**
 * 订单详情
 */
class OrderDetailActivity : BaseActivity() {

    private val payUtil: PayPasswordUtil by lazy {
        PayPasswordUtil(btn_commit, this).apply {
            onPaySuccessResult = {
                PayResultActivity.intent(this@OrderDetailActivity, "1")
                finish()
            }
        }
    }

    private var ordersId = ""
    private var price = 0f

    override fun setLayout(): Int = R.layout.act_order_detail

    override fun initView() {

        defaultTitle("订单详情")

        recyclerView.layoutManager = LinearLayoutManager(this)


        setOnClickListener(btn_commit)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_commit.id -> {
                //发起支付
                payUtil.showPayPasswordWindow(payUtil.pay, ordersId, price)
            }
        }

    }

    override fun loadData() {
        ZyFrameStore.getData<MealRecordBean>(MealRecordBean::class.java.simpleName, true)?.let {
            ordersId = it.ordersId.toString()
            tv_shop_name.text = it.shopName
            tv_order_time.text = it.createTime
            tv_order_creator.text = ("${it.createUserName} ${it.mobile}")
            tv_order_number.text = ("订单号：${it.ordersNumber}")
            tv_shopping_fee.text = ("配送费：¥ ${it.shippingFee ?: 0}")
            tv_total_price.text = ("¥ ${it.totalPrice ?: "0"}")
            tv_subsidy_price.text = ("¥ ${it.subsidyPrice ?: "0"}")
            tv_pay_price.text = ("¥ ${it.payPrice ?: "0"}")
            price = (it.payPrice ?: "0").toFloat()

            val payState = when (it.payState) {
                "0" -> {
                    btn_commit.visibility = View.VISIBLE
                    "待支付"
                }
                "1" -> "支付成功"
                "2" -> "关闭"
                "3" -> "撤销"
                "4" -> "待退款"
                "5" -> "退款成功"
                "6" -> "交易结束，不可退款"
                else -> ""
            }
            tv_state.text = payState
        }
    }

    override fun close() {

    }
}