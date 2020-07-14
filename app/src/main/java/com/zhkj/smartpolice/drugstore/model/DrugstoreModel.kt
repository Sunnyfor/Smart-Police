package com.zhkj.smartpolice.drugstore.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.DateUtil
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import org.json.JSONObject

class DrugstoreModel {

    /**
     * 意见反馈
     */
    suspend fun commitFeedback(shopId: String, content: String, phone: String): String? {

        val params = JSONObject()
        params.put("content", content)
        params.put("mobile", phone)
        params.put("professionId", shopId)
        params.put("createTime", DateUtil.stampToDate(System.currentTimeMillis(), true))
        params.put("createUserId", UserManager.getUserBean().userId)
        params.put("createUserName", UserManager.getUserBean().userName)
        params.put("feedbackId", "")

        val httpResultBean = object : HttpResultBean<BaseModel<String>>() {}

        ZyHttp.postJson(UrlConstant.DRUGSTORE_FEEDBACK_URL, params.toString(), httpResultBean)

        return httpResultBean.bean?.msg ?: ""
    }

}