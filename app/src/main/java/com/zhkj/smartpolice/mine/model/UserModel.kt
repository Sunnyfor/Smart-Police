package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.bean.UserBean
import org.json.JSONObject

class UserModel {

    /**
     * 上传图片
     */
    suspend fun uploadImage(url: String, filePath: String): ImageBean? {

        val httpResultBean = object : HttpResultBean<BaseModel<ImageBean>>() {}

        ZyHttp.formUpload(url, filePath, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data
            }
        }
        return null
    }

    /**
     *  个人信息
     */
    suspend fun loadUserInfo(): UserBean? {
        val httpResultBean = object : HttpResultBean<BaseModel<UserBean>>("user") {}

        ZyHttp.get(UrlConstant.LOAD_USER_INFO_URL, null, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data
            }
        }
        return null
    }

    /**
     *  更新个人信息
     */
    suspend fun updateUserInfo(bean: UserBean): String? {
        val httpResultBean = object : HttpResultBean<BaseModel<UserBean>>() {}

        val params = JSONObject()
        params.put("avatar", bean.avatar)
        params.put("email", bean.email)
        params.put("mobile", bean.mobile)
        params.put("nickName", bean.nickName)
        params.put("userName", bean.userName)

        ZyHttp.postJson(UrlConstant.UPDATE_USER_INFO_URL, params.toString(), httpResultBean)

        return httpResultBean.bean?.msg ?: ""
    }

}