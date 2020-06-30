package com.zhkj.smartpolice.wallet.contract

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.wallet.bean.PurseBean
import com.zhkj.smartpolice.wallet.bean.RecordBean
import java.io.File

interface WalletContract {
    interface IWalletView : IBaseView {
        fun showPurseData(purseBean: PurseBean)
    }

    interface IPayCodeView : IBaseView {
        fun showPayCodeData(file: File)
        fun showCountdownData(number: String)
    }

    interface IRecordView : IBaseView {
        fun showRecordData(recordBeans: ArrayList<RecordBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {
        //加载钱包数据
        abstract fun loadPurse()

        //生成付款码
        abstract fun generatePayQrCode()

        //加载钱包流水
        abstract fun loadRecord(page: String, limit: String? = null)
    }
}