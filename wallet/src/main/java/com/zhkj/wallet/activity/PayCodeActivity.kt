package com.zhkj.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.GlideApp
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.SpUtil
import com.zhkj.wallet.R
import com.zhkj.wallet.contract.WalletContract
import com.zhkj.wallet.presenter.WalletPresenter
import com.zhkj.wallet.utils.PayPasswordUtil
import kotlinx.android.synthetic.main.act_pay_code.*
import org.json.JSONObject
import java.io.File

@Route(path = RouterManager.PAY_CODE_ACTIVITY)
class PayCodeActivity : BaseActivity(), WalletContract.IPayCodeView {

    private val walletPresenter: WalletPresenter by lazy {
        WalletPresenter(this)
    }

    private val payPasswordUtil: PayPasswordUtil by lazy {
        PayPasswordUtil(iv_qr_code, this) {
            val dataObject = JSONObject()
            dataObject.put("ordersId", payPasswordUtil.ordersId)
            dataObject.put("userId", SpUtil.getString(SpUtil.userId))
            dataObject.put("payPassword", it)

            val sendResult = walletPresenter.socketResultBean.bean?.send(dataObject.toString())
            if (sendResult == true) {
                LogUtil.i("webSocket发送成功：$dataObject")
            } else {
                LogUtil.i("webSocket发送失败：$dataObject")
            }
        }
    }

    var ordersId = ""
    var totalPrice = ""

    @Autowired
    @JvmField
    var isFromMainActivity = false

    private var isSuccess = false

    override fun setLayout(): Int = R.layout.act_pay_code

    override fun initView() {

        ARouter.getInstance().inject(this)

        if (isFromMainActivity) {
            defaultTitle("警营一码通")
            tv_desc.visibility = View.VISIBLE
        } else {
            defaultTitle("支付码")
        }

        circleCountDownView.setStartCountValue(30)
        circleCountDownView.setAnimationInterpolator { inputFraction -> inputFraction * inputFraction }

    }

    override fun loadData() {
        //建立长连接
        walletPresenter.connectWebSocket()
    }

    override fun onClickEvent(view: View) {

    }

    override fun close() {
        circleCountDownView.stop()
        walletPresenter.stopTimer()
        walletPresenter.socketResultBean.bean?.cancel()
        walletPresenter.onDestroy()
    }

    override fun showPayCodeData(file: File) {
        //启动倒计时
        circleCountDownView.restart()
        walletPresenter.startTimer()
        //加载显示付款码
        GlideApp.with(this)
            .load(file)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.svg_pay_qr_code)
            .into(iv_qr_code)
    }

    override fun showCountdownData(number: String) {
//        tv_hint.text = ("$number 秒后刷新")
    }

    override fun showSocketResult(isSuccess: Boolean) {

        this.isSuccess = isSuccess

        if (isSuccess) {
            walletPresenter.generatePayQrCode()
        } else {
            walletPresenter.stopTimer()
            walletPresenter.setDefaultTime()
            iv_qr_code.postDelayed({
                loadData()
            }, 5000)
        }
    }

    override fun showSocketMessage(message: String) {
        runOnUiThread {
            val msgObj = JSONObject(message)

//        1支付成功，2请输入密码，3余额不足，4密码不正确，5未知错误

            when (msgObj.optInt("status")) {
                1 -> {
                    PayResultActivity.intent("1", msgObj.optString("message"))
                    finish()
                }
                2 -> {
                    msgObj.optJSONObject("data")?.let {

                        ordersId = it.optInt("ordersId").toString()
                        totalPrice = it.optString("totalPrice")

                        payPasswordUtil.showPayPasswordWindow(
                            payPasswordUtil.pay,
                            ordersId,
                            totalPrice.toFloat()
                        )
                    }
                }
                3 -> {
                    PayResultActivity.intent("0")
                }

                4 -> {
                    payPasswordUtil.showPayPasswordWindow(
                        payPasswordUtil.pay,
                        ordersId,
                        totalPrice.toFloat()
                    )
                }
                else -> {
                    PayResultActivity.intent("4", msgObj.optString("message"))
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (isSuccess) {
            walletPresenter.stopTimer()
        }

    }

    override fun onRestart() {
        super.onRestart()
        if (isSuccess) {
            walletPresenter.startTimer()
        }
    }
}