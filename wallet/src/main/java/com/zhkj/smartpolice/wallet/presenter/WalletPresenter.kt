package com.zhkj.smartpolice.wallet.presenter

import com.sunny.zy.base.ErrorViewType
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.wallet.contract.WalletContract
import com.zhkj.smartpolice.wallet.model.WalletModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * 钱包功能的presenter
 */
class WalletPresenter(iBaseView: IBaseView) :
    WalletContract.Presenter(iBaseView) {
    private val defaultCount = 30
    private val delay = 1000L
    private var count = defaultCount

    var timer: Timer? = null
    var timerTask: TimerTask? = null

    private val walletModel: WalletModel by lazy {
        WalletModel()
    }

    //加载钱包数据
    override fun loadPurse() {
        launch(Main) {
            showLoading()
            walletModel.loadPurse()?.let {
                if (view is WalletContract.IWalletView) {
                    (view as WalletContract.IWalletView).showPurseData(it)
                }
            }
            hideLoading()
        }
    }

    //生成付款码
    override fun generatePayQrCode() {
        launch(Main) {
            if (count != defaultCount) {
                return@launch
            }
            showLoading()
            val file = walletModel.generatePayQrCode()
            hideLoading()
            if (file != null) {
                if (view is WalletContract.IPayCodeView) {
                    (view as WalletContract.IPayCodeView).showPayCodeData(file)
                }
            } else {
                view?.showError(ErrorViewType(ErrorViewType.emptyData, "生成付款码失败", 0))
            }
        }
    }

    /**
     * 加载钱包流水
     */
    override fun loadRecord(page: String, limit: String?) {
        launch(Main) {
            if (view is WalletContract.IRecordView) {
                (view as WalletContract.IRecordView).showRecordData(
                    walletModel.loadRecord(
                        page,
                        limit
                    ) ?: arrayListOf()
                )
            }
        }
    }

    /**
     * 是否设置过支付密码
     */
    override fun isSettingPayPassword() {
        launch(Main) {
            walletModel.loadPurse()?.let {
                if (view is WalletContract.IPayPassWordView) {
                    val hasPayPassword = !(it.payPassword == null || it.payPassword == "")
                    (view as WalletContract.IPayPassWordView).isSettingPayPassword(hasPayPassword)
                }
            }
        }

    }

    /**
     * 创建/更新支付密码
     */
    override fun updatePayPassword(oldPayPassword: String, newPayPassword: String) {
        launch(Main) {
            val baseModel = walletModel.updatePayPassword(oldPayPassword, newPayPassword)
            if (view is WalletContract.IPayPassWordView) {
                if (baseModel?.code == "0") {
                    (view as WalletContract.IPayPassWordView).updatePayPassword(baseModel)
                }
            }
        }
    }

    /**
     * /验证支付密码
     */
    override fun verifyPayPassword(payPassword: String) {
        launch(Main) {
            val isOK = walletModel.verifyPayPassword(payPassword)
            if (view is WalletContract.IPayPassWordView) {
                (view as WalletContract.IPayPassWordView).verifyPayPassword(isOK)
            }
        }
    }


    //启动倒计时刷新付款码
    fun startTimer() {
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                launch(IO) {
                    count--
                    withContext(Main) {
                        showCountdown()
                    }
                    if (count <= 0) {
                        count = defaultCount
                        stopTimer()
                        generatePayQrCode()
                    }
                }
            }
        }
        //显示倒计时
        showCountdown()
        //启动定时器
        timer?.schedule(timerTask, delay, delay)
    }

    private fun showCountdown() {
        if (view is WalletContract.IPayCodeView) {
            (view as WalletContract.IPayCodeView).showCountdownData(count.toString())
        }
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
        timerTask?.cancel()
        timerTask = null
    }
}