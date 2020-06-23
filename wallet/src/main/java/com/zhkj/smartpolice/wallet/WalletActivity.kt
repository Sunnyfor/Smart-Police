package com.zhkj.smartpolice.wallet

import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.wallet.R

/**
 * 钱包页面
 */
class WalletActivity : BaseActivity() {
    override fun setLayout(): Int = R.layout.act_wallet

    override fun initView() {
        defaultTitle("钱包").elevation = 0f
    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {

    }

    override fun close() {

    }
}