package com.zhkj.wallet.contract

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.wallet.bean.BandCardBean
import com.zhkj.wallet.bean.PurseBean
import com.zhkj.wallet.bean.RecordBean
import java.io.File

interface WalletContract {
    interface IWalletView : IBaseView {
        fun showPurseData(purseBean: PurseBean)
    }

    interface IPayCodeView : IBaseView {
        fun showPayCodeData(file: File)
        fun showCountdownData(number: String)
        fun showSocketResult(isSuccess: Boolean)
        fun showSocketMessage(message: String)
    }

    interface IRecordView : IBaseView {
        fun showRecordData(recordBeans: ArrayList<RecordBean>)
    }

    interface IPayPassWordView : IBaseView {
        fun isSettingPayPassword(hasPayPassword: Boolean)
        fun paySuccess()
        fun updatePayPassword()
    }

    interface IWithdrawalView : IBaseView {
        fun showVerificationCodeResult(isSuccess: Boolean)
        fun showWithdrawalResult(isSuccess: Boolean)
    }

    interface IBandCardView : IBaseView {
        fun showBandCard(data: ArrayList<BandCardBean>)
        fun showAddBandCardResult(isSuccess: Boolean)
    }


    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {
        //加载钱包数据
        abstract fun loadPurse()

        //生成付款码
        abstract fun generatePayQrCode()

        //加载钱包流水
        abstract fun loadRecord(page: String)

        //是否设置过支付密码
        abstract fun isSettingPayPassword()

        //创建或修改支付密码
        abstract fun updatePayPassword(oldPayPassword: String, newPayPassword: String)

        //支付方法
        abstract fun pay(orderId: String, payPassword: String)

        //加载银行卡列表
        abstract fun loadBandCardList()

        //添加银行卡
        abstract fun addBandCard(bandCard: String, idCard: String, name: String, bankName: String)

        //发送验证码
        abstract fun sendVerificationCode(phone: String)

        //提现
        abstract fun withdrawal(amount: String, bandCardId: String, verificationCode: String)
    }
}