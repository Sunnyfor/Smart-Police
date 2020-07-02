package com.zhkj.smartpolice.wallet.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.wallet.bean.PurseBean
import com.zhkj.smartpolice.wallet.bean.RecordBean
import com.zhkj.smartpolice.wallet.http.WalletUrlConstant
import java.io.File

class WalletModel {

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
    suspend fun loadRecord(page: String, limit: String? = null): ArrayList<RecordBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<RecordBean>>() {}
        val params = hashMapOf<String, String>()
        params["page"] = page
        params["limit"] = limit ?: "20"
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
        val params = hashMapOf<String, String>()
        params["oldPayPassword"] = oldPayPassword
        params["newPayPassword"] = newPayPassword
        ZyHttp.post(WalletUrlConstant.UPDATE_PAY_PASSWORD, params, httpResultBean)
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
    suspend fun verifyPayPassword(payPassword: String): Boolean {
        val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
        val params = hashMapOf<String, String>()
        params["payPassword"] = payPassword
        ZyHttp.post(WalletUrlConstant.VERIFY_PAY_PASSWORD, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.code == "0") {
                return true
            }else {
                ToastUtil.show(httpResultBean.bean?.msg)
            }
        }
        return false
    }
}