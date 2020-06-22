package com.sunny.zy.model

import com.sunny.zy.bean.VersionBean
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import java.io.File

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/15 11:47
 */
class VersionUpdateModel {

    //检查是否有新版本

    suspend fun checkVersion(version: Int): VersionBean? {
        val params = hashMapOf<String, String>()
        params["versionCode"] = version.toString()

        val httpResultBean = object :HttpResultBean<VersionBean>(){}
        ZyHttp.post(
            UrlConstant.APP_VERSION_UPDATE_URL,
            params,
            httpResultBean
        )
        if (httpResultBean.isSuccess()) {
            return httpResultBean.bean
        }
        return null
    }


    suspend fun downLoadAPK(
        url: String,
        httpResultBean: HttpResultBean<File>
    ) {

        val file = File(UrlConstant.TEMP, httpResultBean.serializedName)
        if (file.exists()) {
            file.delete()
        }
        ZyHttp.get(url, null, httpResultBean)
    }

}