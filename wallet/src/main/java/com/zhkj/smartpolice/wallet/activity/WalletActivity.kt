package com.zhkj.smartpolice.wallet.activity


import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_wallet.*

/**
 * 钱包页面
 */
@Route(path = RouterManager.WALLET_ACTIVITY)
class WalletActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.act_wallet

    override fun initView() {
        defaultTitle("钱包").elevation = 0f
    }

    override fun loadData() {
        setOnClickListener(
            btn_recharge,
            btn_withdrawal,
            view_pay_parent
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_recharge.id -> RouterManager.navigation(this, RouterManager.RECHARGE_ACTIVITY)
            btn_withdrawal.id -> RouterManager.navigation(this, RouterManager.WITHDRAWAL_ACTIVITY)
            view_pay_parent.id -> RouterManager.navigation(this, RouterManager.PAY_CODE_ACTIVITY)
        }
    }

    override fun close() {

    }
}