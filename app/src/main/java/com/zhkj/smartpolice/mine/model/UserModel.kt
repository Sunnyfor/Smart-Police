package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.mine.bean.UserBean

class UserModel {

    /**
     *  个人信息
     */
    suspend fun loadUserInfo(): UserBean? {
        val httpResultBean = object : HttpResultBean<BaseModel<UserBean>>("user") {}
        ZyHttp.get(UrlConstant.USER_INFO_URL, null, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data
            }
        }
        return null
    }

}