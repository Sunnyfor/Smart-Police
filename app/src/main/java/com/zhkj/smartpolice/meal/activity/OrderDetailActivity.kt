package com.zhkj.smartpolice.meal.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealOrderDetailAdapter
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import com.zhkj.wallet.activity.PayResultActivity
import com.zhkj.wallet.utils.PayPasswordUtil
import kotlinx.android.synthetic.main.act_order_detail.*

/**
 * 订单详情
 */
class OrderDetailActivity : BaseActivity(), MealContract.IMealRecordDetailView, MealContract.IConfirmReceive {

    private var mealRecordBean: MealRecordBean? = null

    private val payUtil: PayPasswordUtil by lazy {
        PayPasswordUtil(btn_commit, this).apply {
            onPaySuccessResult = {
                PayResultActivity.intent("1")
                finish()
            }
        }
    }

    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    private val adapter = MealOrderDetailAdapter()

    private var ordersId = ""
    private var price = 0f

    override fun setLayout(): Int = R.layout.act_order_detail

    override fun initView() {

        defaultTitle("订单详情")

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        setOnClickListener(btn_commit)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_commit.id -> {
                if (mealRecordBean?.ordersState == "2") {
                    //确认收货
                    presenter.confirmReceive(ordersId)
                } else {
                    //发起支付
                    payUtil.showPayPasswordWindow(payUtil.pay, ordersId, price)
                }
            }
        }

    }

    override fun loadData() {

        mealRecordBean = ZyFrameStore.getData<MealRecordBean>(MealRecordBean::class.java.simpleName, true)

        mealRecordBean?.let {
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
                    btn_commit.text = "支付"
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

            if (it.ordersState == "2") {
                btn_commit.visibility = View.VISIBLE
                btn_commit.text = "确认收货"
            }

            presenter.loadMealRecordDetail(ordersId)
        }
    }

    override fun close() {

    }

    override fun showMealRecordDetail(data: MealRecordBean) {
        adapter.clearData()
        adapter.addData(data.ordersLinkEntityList ?: arrayListOf())
        adapter.notifyDataSetChanged()
    }

    override fun confirmReceive(msg: String) {
        if (msg == "success") {
            ToastUtil.show("收获成功")
            finish()
        } else {
            ToastUtil.show(msg)
        }
    }
}