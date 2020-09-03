package com.zhkj.smartpolice.version.presenter

import com.zhkj.smartpolice.version.contract.VersionContract
import com.zhkj.smartpolice.version.model.VersionModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class VersionPresenter(view: VersionContract.View) :
    VersionContract.Presenter(view) {

    private val versionUpdateModel: VersionModel by lazy {
        VersionModel()
    }

    override fun checkVersion(version: Int) {
        launch(Main) {
            val versionBean = versionUpdateModel.checkVersion(version)
            if (versionBean != null) {
                if (versionBean.versionCode > version) {
                    view?.showVersionUpdate(versionBean)
                } else {
                    view?.noNewVersion()
                }
            }
        }
    }

    override fun downLoadAPk(url: String) {
        launch(Main) {
            val file = versionUpdateModel.downLoadAPK(url) {
                launch(Main) {
                    view?.progress(it)
                }
            }
            view?.downLoadResult(file?.path ?: "")
        }
    }


}