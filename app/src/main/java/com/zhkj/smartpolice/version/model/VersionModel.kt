package com.zhkj.smartpolice.version.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.BaseHttpResultBean
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.version.bean.VersionBean
import java.io.File
import java.text.NumberFormat

class VersionModel {

    //检查是否有新版本

    suspend fun checkVersion(version: Int): VersionBean? {
        val params = hashMapOf<String, String>()
        params["versionCode"] = version.toString()

        val httpResultBean = object : HttpResultBean<BaseModel<VersionBean>>("appAndroidVersion") {}

        ZyHttp.get(UrlConstant.VERSION_UPDATE_URL, params, httpResultBean)

        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true)
                return httpResultBean.bean?.data
        }
        return null
    }


    suspend fun downLoadAPK(
        url: String, result: ((progress: Int) -> Unit)
    ): File? {

        val fileName = "housekeeping.apk"
        val file = File(UrlConstant.TEMP, fileName)
        if (file.exists()) {
            file.delete()
        }

        var progress = 0
        val httpResultBean = object : HttpResultBean<File>(fileName) {
            override fun notifyData(baseHttpResultBean: BaseHttpResultBean<File>) {
                val numberFormat = NumberFormat.getInstance().apply {
                    maximumFractionDigits = 0
                }
                numberFormat.format((baseHttpResultBean.readLength.toFloat() / baseHttpResultBean.contentLength.toFloat()) * 100)
                    .toInt().let { mProgress ->
                        if (mProgress != progress) {
                            progress = mProgress
                            result.invoke(progress)
                        }
                    }
            }
        }
        ZyHttp.get(url, null, httpResultBean)
        if (httpResultBean.isSuccess()) {
            return httpResultBean.bean
        }
        return null
    }

}