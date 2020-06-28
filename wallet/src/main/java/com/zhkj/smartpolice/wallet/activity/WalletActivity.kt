package com.zhkj.smartpolice.wallet.activity


import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterPath
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_wallet.*

/**
 * 钱包页面
 */
@Route(path = RouterPath.WALLET_ACTIVITY)
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
            btn_recharge.id -> {
                ARouter.getInstance().build(RouterPath.RECHARGE_ACTIVITY).navigation()
            }
            btn_withdrawal.id -> {
                ARouter.getInstance().build(RouterPath.WITHDRAWAL_ACTIVITY).navigation()
            }
            view_pay_parent.id -> {
                ARouter.getInstance().build(RouterPath.PAY_CODE_ACTIVITY).navigation()
            }
        }
    }

    override fun close() {

    }
}