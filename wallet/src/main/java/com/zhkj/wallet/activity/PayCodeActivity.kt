package com.zhkj.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.GlideApp
import com.sunny.zy.utils.RouterManager
import com.zhkj.wallet.R
import com.zhkj.wallet.contract.WalletContract
import com.zhkj.wallet.presenter.WalletPresenter
import kotlinx.android.synthetic.main.act_pay_code.*
import java.io.File

@Route(path = RouterManager.PAY_CODE_ACTIVITY)
class PayCodeActivity : BaseActivity(), WalletContract.IPayCodeView {

    private val walletPresenter: WalletPresenter by lazy {
        WalletPresenter(this)
    }


    override fun setLayout(): Int = R.layout.act_pay_code

    override fun initView() {
        defaultTitle("支付码")
    }

    override fun loadData() {
        //生成支付码
        walletPresenter.generatePayQrCode()
    }

    override fun onClickEvent(view: View) {

    }

    override fun close() {
        walletPresenter.stopTimer()
    }

    override fun showPayCodeData(file: File) {
        walletPresenter.startTimer() //启动倒计时
        //加载显示付款码
        GlideApp.with(this)
            .load(file)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .placeholder(R.drawable.svg_pay_qr_code)
            .into(iv_qr_code)
    }

    override fun showCountdownData(number: String) {
        tv_hint.text = ("$number 秒后刷新")
    }

    override fun onPause() {
        super.onPause()
        walletPresenter.stopTimer()
    }

    override fun onRestart() {
        super.onRestart()
        walletPresenter.startTimer()
    }
}