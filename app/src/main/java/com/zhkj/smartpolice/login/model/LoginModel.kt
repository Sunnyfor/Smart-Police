package com.zhkj.smartpolice.login.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.login.bean.UserInfoBean
import org.json.JSONObject

class LoginModel {

    suspend fun doLogin(username: String, password: String): ArrayList<UserInfoBean>? {
        val params = HashMap<String, String>()
        params["userName"] = username
        params["password"] = password

        val httpResultBean = object : HttpResultBean<BaseModel<ArrayList<UserInfoBean>>>() {}
        ZyHttp.post(UrlConstant.USER_LOGIN_URL, params, httpResultBean)
        if (httpResultBean.bean?.isSuccess() == true) {
            return httpResultBean.bean?.data
        }

        return null
    }

    suspend fun sendVerificationCode(phone: String): String? {
        val httpResultBean = object : HttpResultBean<BaseModel<String>>() {}
        ZyHttp.post(UrlConstant.SEND_VERIFICATION_CODE_URL, hashMapOf(Pair("mobile", phone)), httpResultBean)
        if (httpResultBean.bean?.code == "500") {
            return httpResultBean.bean?.msg
        }

        return null
    }

    suspend fun forgetPassword(phone: String, newPwd: String, verificationCode: String): String? {

        val json = JSONObject()
        json.put("mobile", phone)
        json.put("password", newPwd)
        json.put("verificationCode", verificationCode)

        val httpResultBean = object : HttpResultBean<BaseModel<String>>() {}
        ZyHttp.postJson(UrlConstant.FORGET_PASSWORD_URL, json.toString(), httpResultBean)
        if (httpResultBean.bean?.code == "0") {
            return httpResultBean.bean?.msg
        }

        return null
    }


}