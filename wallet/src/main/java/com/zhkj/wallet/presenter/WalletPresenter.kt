package com.zhkj.wallet.presenter

import com.sunny.zy.base.ErrorViewType
import com.sunny.zy.base.IBaseView
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.LogUtil
import com.zhkj.wallet.contract.WalletContract
import com.zhkj.wallet.model.WalletModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString
import java.util.*

/**
 * 钱包功能的presenter
 */
class WalletPresenter(iBaseView: IBaseView) :
    WalletContract.Presenter(iBaseView) {
    private val defaultCount = 30
    private val delay = 1000L
    private var count = defaultCount

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    private val walletModel: WalletModel by lazy {
        WalletModel()
    }
    var balance = 0f

    val socketResultBean = object : HttpResultBean<WebSocket>() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            bean = webSocket
            LogUtil.i("webSocket：连接成功！${webSocket}")
            if (view is WalletContract.IPayCodeView) {
                (view as WalletContract.IPayCodeView).showSocketResult(true)
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            LogUtil.i("webSocket：接收到消息！${text}")
            if (view is WalletContract.IPayCodeView) {
                (view as WalletContract.IPayCodeView).showSocketMessage(text)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            LogUtil.i("webSocket：接收到消息！${bytes}")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            LogUtil.i("webSocket：连接失败！URL:${response?.request?.url} ---- 消息：${t.message}")
            if (view is WalletContract.IPayCodeView) {
                (view as WalletContract.IPayCodeView).showSocketResult(false)
            }
        }
    }


    //加载钱包数据
    override fun loadPurse() {
        launch(Main) {
            showLoading()
            walletModel.loadPurse()?.let {
                balance = it.balance.toFloat()
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
    override fun loadRecord(page: String) {
        launch(Main) {
            if (view is WalletContract.IRecordView) {
                (view as WalletContract.IRecordView).showRecordData(
                    walletModel.loadRecord(page) ?: arrayListOf()
                )
            }
        }
    }

    /**
     * 是否设置过支付密码
     */
    override fun isSettingPayPassword() {
        launch(Main) {
            view?.showLoading()
            walletModel.loadPurse()?.let {
                balance = it.balance.toFloat()
                if (view is WalletContract.IPayPassWordView) {
                    val hasPayPassword = !(it.payPassword == null || it.payPassword == "")
                    (view as WalletContract.IPayPassWordView).isSettingPayPassword(hasPayPassword)
                }
            }
            view?.hideLoading()
        }

    }

    /**
     * 创建/更新支付密码
     */
    override fun updatePayPassword(oldPayPassword: String, newPayPassword: String) {
        launch(Main) {
            view?.showLoading()
            val baseModel = walletModel.updatePayPassword(oldPayPassword, newPayPassword)
            if (view is WalletContract.IPayPassWordView) {
                if (baseModel?.code == "0") {
                    (view as WalletContract.IPayPassWordView).updatePayPassword()
                }
            }
            view?.hideLoading()
        }
    }

    /**
     * /验证支付密码
     */
    override fun pay(orderId: String, payPassword: String) {
        launch(Main) {
            view?.showLoading()
            walletModel.pay(orderId, payPassword)?.let {
                if (view is WalletContract.IPayPassWordView) {
                    (view as WalletContract.IPayPassWordView).paySuccess()
                }
            }
            view?.hideLoading()
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

    fun setDefaultTime() {
        count = defaultCount
    }


    fun connectWebSocket() {
        walletModel.connectWebSocket(socketResultBean)
    }

    fun onDestroy() {
        view = null
        cancel()
    }
}