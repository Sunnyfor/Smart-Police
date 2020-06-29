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
                    (view as WalletContract.IWalletView).showPurse(it)
                }
            }
            hideLoading()
        }
    }

    //生成付款码
    override fun generatePayQrCode() {
        launch(Main) {
            showLoading()
            val file = walletModel.generatePayQrCode()
            hideLoading()
            if (file != null) {
                if (view is WalletContract.IPayCodeView) {
                    (view as WalletContract.IPayCodeView).showPayCode(file)
                }
            } else {
                view?.showError(ErrorViewType(ErrorViewType.emptyData, "生成付款码失败", 0))
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
            (view as WalletContract.IPayCodeView).showCountdown(count.toString())
        }
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
        timerTask?.cancel()
        timerTask = null
    }
}