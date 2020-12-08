package com.zhkj.wallet.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.SpUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.wallet.bean.BandCardBean
import com.zhkj.wallet.bean.PurseBean
import com.zhkj.wallet.bean.RecordBean
import com.zhkj.wallet.http.WalletUrlConstant
import okhttp3.WebSocket
import org.json.JSONObject
import java.io.File

class WalletModel {

    private val userId: String by lazy {
        SpUtil.getString(SpUtil.userId)
    }

    /**
     *  get请求加载钱包数据
     */
    suspend fun loadPurse(): PurseBean? {
        val httpResultBean = object : HttpResultBean<BaseModel<PurseBean>>() {}
        ZyHttp.get(WalletUrlConstant.PAY_PURSE, null, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data
            }
        }
        return null
    }

    /**
     * 下载付款码
     */
    suspend fun generatePayQrCode(): File? {
        val httpResultBean = object : HttpResultBean<File>("pay_code.png") {}
        ZyHttp.get(WalletUrlConstant.PAY_GENERATE_QR_CODE, null, httpResultBean)
        if (httpResultBean.isSuccess()) {
            return httpResultBean.bean
        }
        return null
    }

    /**
     * 加载钱包流水
     */
    suspend fun loadRecord(page: String): ArrayList<RecordBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<RecordBean>>() {}
        val params = hashMapOf<String, String>()
        params["page"] = page
        params["limit"] = Constant.pageLimit
        params["createUserId"] = SpUtil.getString(SpUtil.userId)
        ZyHttp.get(WalletUrlConstant.PAY_RECORD_LIST, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

    /**
     * 创建/更新支付密码
     */
    suspend fun updatePayPassword(oldPayPassword: String, newPayPassword: String): BaseModel<Any>? {
        val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
        val params = JSONObject()
        params.put("oldPayPassword", oldPayPassword)
        params.put("newPayPassword", newPayPassword)
        ZyHttp.postJson(WalletUrlConstant.UPDATE_PAY_PASSWORD, params.toString(), httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.code == "0") {
                return httpResultBean.bean
            } else {
                ToastUtil.show(httpResultBean.bean?.msg)
            }
        }
        return null
    }


    /**
     * 验证支付密码
     */
    suspend fun pay(ordersId: String, payPassword: String): BaseModel<Any>? {
        val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
        val params = JSONObject()
        params.put("ordersId", ordersId)
        params.put("payPassword", payPassword)
        ZyHttp.postJson(WalletUrlConstant.PAY_FOR_USER_URL, params.toString(), httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.code == "0") {
                return httpResultBean.bean
            } else {
                ToastUtil.show(httpResultBean.bean?.msg)
            }
        }
        return null
    }

    /**
     * 银行卡列表
     */
    suspend fun loadBandCardList(): ArrayList<BandCardBean> {
        val httpResultBean = object : HttpResultBean<PageModel<BandCardBean>>() {}
        val params = HashMap<String, String>()
        params["userId"] = SpUtil.getString(SpUtil.userId)
        ZyHttp.post(WalletUrlConstant.BAND_CARD_LIST_URL, params, httpResultBean)
        httpResultBean.isSuccess()
        return httpResultBean.bean?.data?.list ?: arrayListOf()
    }

    /**
     *  添加银行卡
     */
    suspend fun addBandCard(bandCard: String, idCard: String, name: String, bankName: String): Boolean {
        val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
        val params = JSONObject()
        params.put("bandCard", bandCard)
        params.put("idCard", idCard)
        params.put("name", name)
        params.put("bankName", bankName)

        ZyHttp.postJson(WalletUrlConstant.BAND_CARD_SAVE_URL, params.toString(), httpResultBean)
        if (httpResultBean.isSuccess() && httpResultBean.bean?.isSuccess() == true) {
            return true
        }
        return false
    }

    /**
     *  发送验证码
     */
    suspend fun sendVerificationCode(phone: String): Boolean {
        val httpResultBean = object : HttpResultBean<BaseModel<String>>() {}
        ZyHttp.post(WalletUrlConstant.SEND_VERIFICATION_CODE_URL, hashMapOf(Pair("mobile", phone)), httpResultBean)
        ToastUtil.show(httpResultBean.bean?.msg)
        if (httpResultBean.bean?.code == "500") {
            return true
        }
        return false
    }

    /**
     * 提现
     */
    suspend fun withdrawal(amount: String, bandCardId: String, verificationCode: String): Boolean {
        val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
        val params = JSONObject()
        params.put("amount", amount)
        params.put("bandCardId", bandCardId)
        params.put("verificationCode", verificationCode)
        ZyHttp.postJson(WalletUrlConstant.RECORD_QMF_PAYMENT, params.toString(), httpResultBean)
        if (httpResultBean.isSuccess() && httpResultBean.bean?.isSuccess() == true) {
            return true
        }
        return false
    }


    fun connectWebSocket(bean: HttpResultBean<WebSocket>) {
        ZyHttp.webSocket(String.format(WalletUrlConstant.WEB_SOCKET_HOST, userId), bean)
    }
}