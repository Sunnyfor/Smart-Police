package com.zhkj.smartpolice.version.contract

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.version.bean.VersionBean

interface VersionContract {

    interface View : IBaseView {
        fun showVersionUpdate(versionBean: VersionBean)
        fun noNewVersion()
        fun downLoadResult(path: String)
        fun progress(progress: Int)
    }

    abstract class Presenter(view: View) : BasePresenter<View>(view) {
        //检查版本
        abstract fun checkVersion(version: Int)

        abstract fun downLoadAPk(url: String)
    }

}