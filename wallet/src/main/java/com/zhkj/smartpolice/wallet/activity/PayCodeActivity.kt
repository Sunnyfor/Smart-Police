package com.zhkj.smartpolice.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.zhkj.smartpolice.R

@Route(path = RouterManager.PAY_CODE_ACTIVITY)
class PayCodeActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_pay_code

    override fun initView() {
        defaultTitle("支付码")
    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {

    }

    override fun close() {

    }
}