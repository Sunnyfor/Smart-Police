package com.zhkj.smartpolice.wallet.contract

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.wallet.bean.PurseBean
import java.io.File

interface WalletContract {
    interface IWalletView : IBaseView {
        fun showPurse(purseBean: PurseBean)
    }

    interface IPayCodeView : IBaseView {
        fun showPayCode(file: File)
        fun showCountdown(number: String)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {
        //加载钱包数据
        abstract fun loadPurse()

        //生成付款码
        abstract fun generatePayQrCode()
    }
}