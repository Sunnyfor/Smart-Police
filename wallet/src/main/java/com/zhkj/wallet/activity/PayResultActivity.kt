package com.zhkj.wallet.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterManager
import com.zhkj.wallet.R
import kotlinx.android.synthetic.main.act_pay_result.*

@Route(path = RouterManager.PAY_RESULT_ACTIVITY)
class PayResultActivity : BaseActivity() {

    private val payResult by lazy {
        intent.getStringExtra("payResult")
    }

    companion object {
        fun intent(context: Context, payResult: String) {
            val intent = Intent(context, PayResultActivity::class.java)
            intent.putExtra("payResult", payResult)
        }
    }

    override fun setLayout(): Int = R.layout.act_pay_result

    override fun initView() {
        defaultTitle("支付结果")

        when (payResult) {
            "0" -> {
                iv_icon.setImageResource(R.drawable.svg_pay_fail)
                tv_desc.text = ("您的可用余额不足，\n请前往钱包充值")
                btn_finish.text = "进入钱包"
                btn_finish.setOnClickListener {
                    startActivity(Intent(this, WalletActivity::class.java))
                }
            }
            "1" -> {
                iv_icon.setImageResource(R.drawable.svg_pay_success)
                tv_desc.text = ("支付成功")
                btn_finish.text = "完成"
                btn_finish.setOnClickListener {
                    finish()
                }
            }
        }
    }


    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {

    }

}