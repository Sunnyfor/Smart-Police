package com.zhkj.wallet.activity


import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.ToastUtil
import com.zhkj.wallet.R
import com.zhkj.wallet.bean.PurseBean
import com.zhkj.wallet.contract.WalletContract
import com.zhkj.wallet.presenter.WalletPresenter
import com.zhkj.wallet.utils.PayPasswordUtil
import kotlinx.android.synthetic.main.act_wallet.*

/**
 * 钱包页面
 */
@Route(path = RouterManager.WALLET_ACTIVITY)
class WalletActivity : BaseActivity(), WalletContract.IWalletView {

    var isSettingPayPassword = false

    private val walletPresenter: WalletPresenter by lazy {
        WalletPresenter(this)
    }

    private val payPasswordUtil: PayPasswordUtil by lazy {
        PayPasswordUtil(view_pay_password_parent, this).apply {
            updatePayPassword = {
                ToastUtil.show(it.msg)
            }
        }
    }

    override fun setLayout(): Int = R.layout.act_wallet

    override fun initView() {

        defaultTitle("钱包").elevation = 0f

        setOnClickListener(
            btn_recharge,
            btn_withdrawal,
            view_pay_parent,
            view_record_parent,
            view_pay_password_parent
        )



    }


    override fun loadData() {
        //加载钱包数据
        walletPresenter.loadPurse()

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_recharge.id -> RouterManager.navigation(this, RouterManager.RECHARGE_ACTIVITY)
            btn_withdrawal.id -> RouterManager.navigation(this, RouterManager.WITHDRAWAL_ACTIVITY)
            view_pay_parent.id -> RouterManager.navigation(this, RouterManager.PAY_CODE_ACTIVITY)
            view_record_parent.id -> RouterManager.navigation(this, RouterManager.RECORD_ACTIVITY)
            view_pay_password_parent.id -> {
                if (!isSettingPayPassword) {
                    payPasswordUtil.showPayPasswordWindow()
                } else {
                    payPasswordUtil.showPayPasswordWindow(payPasswordUtil.modify)
                }
            }
        }
    }

    override fun close() {

    }

    override fun showPurseData(purseBean: PurseBean) {
        tv_balance.text = purseBean.balance
        if (purseBean.payPassword == null || purseBean.payPassword == "0") {
            isSettingPayPassword = false
            tv_pay_password_title.text = getString(R.string.create_pay_password)
        } else {
            isSettingPayPassword = true
            tv_pay_password_title.text = getString(R.string.modify_pay_password)
        }
    }
}