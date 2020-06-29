package com.zhkj.smartpolice.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.GlideApp
import com.sunny.zy.utils.RouterManager
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.wallet.contract.WalletContract
import com.zhkj.smartpolice.wallet.presenter.WalletPresenter
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

    override fun showPayCode(file: File) {
        walletPresenter.startTimer() //启动倒计时
        //加载显示付款码
        GlideApp.with(this)
            .load(file)
            .placeholder(R.drawable.svg_pay_qr_code)
            .into(iv_qr_code)
    }

    override fun showCountdown(number: String) {
        tv_hint.text = ("$number 秒后刷新")
    }
}