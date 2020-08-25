package com.zhkj.wallet.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.wallet.bean.UnifyPayBean
import com.zhkj.wallet.http.WalletUrlConstant
import org.json.JSONObject

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/8/25 18:15
 */
class UnifyPayModel {
    /**
     * 下单接口
     * @param qmfPayType  1=支付宝，2=微信，3全民付，4银联云闪付，5Apply Pay
     */
    suspend fun unifyPay(qmfPayType: Int, amount: Float): UnifyPayBean? {
        val jsonObj = JSONObject()
        jsonObj.put("type", "6")
        jsonObj.put("qmfPayType", qmfPayType)
        jsonObj.put("amount", amount)
        val httpResultBean = object : HttpResultBean<BaseModel<UnifyPayBean>>() {}
        ZyHttp.postJson(WalletUrlConstant.PAY_RECORD_SAVE, jsonObj.toString(), httpResultBean)
        if (httpResultBean.isSuccess() && httpResultBean.bean?.isSuccess() == true) {
            return httpResultBean.bean?.data
        }
        return null
    }
}