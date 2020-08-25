package com.zhkj.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.chinaums.pppay.unify.UnifyPayPlugin
import com.chinaums.pppay.unify.UnifyPayRequest
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.ToastUtil
import com.zhkj.wallet.R
import com.zhkj.wallet.bean.UnifyPayBean
import com.zhkj.wallet.contract.UnifyPayContract
import com.zhkj.wallet.presenter.UnifyPayPresenter
import com.zhkj.wallet.utils.MoneyInputFilter
import kotlinx.android.synthetic.main.act_recharge.*

/**
 * 充值页面
 */
@Route(path = RouterManager.RECHARGE_ACTIVITY)
class RechargeActivity : BaseActivity(), UnifyPayContract.IView {

    var wxPay = 2
    var aliPay = 1

    var payType = wxPay

    val presenter: UnifyPayContract.Presenter by lazy {
        UnifyPayPresenter(this)
    }

    override fun setLayout(): Int = R.layout.act_recharge

    override fun initView() {
        defaultTitle("充值")
        edt_money.filters = arrayOf(MoneyInputFilter())
        UnifyPayPlugin.getInstance(this).setListener { resultCode, resultInfo ->
            if ("0000" == resultCode) {
                //支付成功
                ToastUtil.show("结果code：$resultCode  结果信息:$resultInfo")
            } else {
                ToastUtil.show("结果code：$resultCode  结果信息:$resultInfo")
            }
        }
    }

    override fun loadData() {
        setOnClickListener(
            view_wx_parent,
            rbtn_wx,
            view_ali_parent,
            rbtn_ali,
            btn_recharge
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            view_wx_parent.id, rbtn_wx.id -> {
                payType = wxPay
                singleSelect()
            }
            view_ali_parent.id, rbtn_ali.id -> {
                payType = aliPay
                singleSelect()
            }
            btn_recharge.id -> {
                presenter.unifyPay(payType, edt_money.text.toString().toFloat())
            }
        }
    }


    private fun singleSelect() {
        if (payType == wxPay) {
            rbtn_ali.isChecked = false
            rbtn_wx.isChecked = true
        } else {
            rbtn_wx.isChecked = false
            rbtn_ali.isChecked = true
        }
    }

    override fun close() {

    }

    private fun recharge(params: String) {
        val request = UnifyPayRequest()

        if (payType == wxPay) {
            request.payChannel = UnifyPayRequest.CHANNEL_WEIXIN
        }

        if (payType == aliPay) {
            request.payChannel = UnifyPayRequest.CHANNEL_ALIPAY
        }

        request.payData = params
        UnifyPayPlugin.getInstance(this).sendPayRequest(request)
    }

    override fun unifyPayResult(unifyPayBean: UnifyPayBean) {
        unifyPayBean.wechatPrePaymentOrderParam?.let {
            recharge(it)
        }

    }
}