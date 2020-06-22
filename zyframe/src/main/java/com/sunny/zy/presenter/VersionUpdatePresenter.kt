package com.sunny.zy.presenter

import com.sunny.zy.contract.VersionUpdateContract
import com.sunny.zy.http.bean.BaseHttpResultBean
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.model.VersionUpdateModel
import com.sunny.zy.utils.LogUtil
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import java.io.File
import java.text.NumberFormat

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/15 12:11
 */
class VersionUpdatePresenter(view: VersionUpdateContract.View) :
    VersionUpdateContract.Presenter(view) {

    private val versionUpdateModel: VersionUpdateModel by lazy {
        VersionUpdateModel()
    }

    //检查版本
    override fun checkVersion(version: Int) {
        launch(Main) {
            val versionBean = versionUpdateModel.checkVersion(version)
            if (versionBean != null) {
                if (versionBean.appAndroidVersion?.versionCode ?: 0 > version) {
                    view?.showVersionUpdate(versionBean)
                } else {
                    view?.noNewVersion()
                }
            }
        }
    }

    //下载新版本APK
    override fun downLoadAPk(url: String) {
        launch(Main) {

            val numberFormat = NumberFormat.getInstance().apply {
                maximumFractionDigits = 0
            }

            val httpResultBean = object : HttpResultBean<File>("housekeeping.apk") {
                override fun notifyData(baseHttpResultBean: BaseHttpResultBean<File>) {
                    LogUtil.i("下载进度:${baseHttpResultBean.readLength}")
                }
            }


            versionUpdateModel.downLoadAPK(url, httpResultBean)

            var progress = 0


//                numberFormat.format((httpResultBean.readLength.toFloat() / httpResultBean.contentLength.toFloat()) * 100)
//                    .toInt().let {
//                        if (it != progress) {
//                            progress = it
//                            withContext(Main) {
//                                view?.progress(progress)
//                            }
//                        }
//                    }
        }

//            val file = httpResultBean.bean
//            if (file != null) {
//                if (file.exists() && file.length() == httpResultBean.contentLength) {
//                    view?.downLoadResult(file.path)
//                } else {
//                    view?.showMessage("文件下载失败，请稍后重试")
//                }
//            }
    }


}