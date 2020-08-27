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
import org.json.JSONArray
import org.json.JSONObject

/**
 * 充值页面
 */
@Route(path = RouterManager.RECHARGE_ACTIVITY)
class RechargeActivity : BaseActivity(), UnifyPayContract.IView {

    private var wxPay = 2
    private var aliPay = 1

    private var payType = wxPay

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
            } else {
                ToastUtil.show(resultInfo)
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
                if (edt_money.text.isEmpty()) {
                    ToastUtil.show("请输入充值金额！")
                    return
                }
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

    private fun recharge(json: String) {

        val jsonArray = JSONArray(json)
        val jsonObj = jsonArray.getJSONObject(0)
        val payJsonObj = jsonObj.getString("全民付预支付订单响应参数")
        val paramsObj = JSONObject(payJsonObj)
        val request = UnifyPayRequest()
        val code = paramsObj.optString("errCode")

        if (code == "SUCCESS") {
            if (payType == wxPay) {
                request.payChannel = UnifyPayRequest.CHANNEL_WEIXIN
            }
            if (payType == aliPay) {
                request.payChannel = UnifyPayRequest.CHANNEL_ALIPAY
            }

            request.payData = paramsObj.optString("appPayRequest")
            UnifyPayPlugin.getInstance(this).sendPayRequest(request)
        } else {
            ToastUtil.show(paramsObj.optString("errMsg"))
        }

    }

    override fun unifyPayResult(unifyPayBean: UnifyPayBean) {
        unifyPayBean.wechatPrePaymentOrderParam?.let {
            recharge(it.toString())
        }

    }
}