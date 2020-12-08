package com.zhkj.wallet.dialog

import android.app.Activity
import com.sunny.zy.base.BaseDialog
import com.sunny.zy.utils.ToastUtil
import com.zhkj.wallet.R
import com.zhkj.wallet.contract.WalletContract
import kotlinx.android.synthetic.main.dialog_add_band_card.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/12/8 11:23
 */
class AddBandCardDialog(activity: Activity) : BaseDialog(activity, R.layout.dialog_add_band_card) {


    fun show(presenter: WalletContract.Presenter) {
        btn_add.setOnClickListener {
            if (et_card_number.text?.isEmpty() == true) {
                ToastUtil.show("请输入银行卡号！")
                return@setOnClickListener
            }

            if (et_id_card.text?.isEmpty() == true) {
                ToastUtil.show("请输入身份证号！")
                return@setOnClickListener
            }

            if (et_user_name.text?.isEmpty() == true) {
                ToastUtil.show("请输入用户姓名！")
                return@setOnClickListener
            }

            if (et_bank_name.text?.isEmpty() == true) {
                ToastUtil.show("请输入所属银行！")
                return@setOnClickListener
            }

            btn_add.isEnabled = false
            btn_add.isClickable = false
            btn_add.text = "请求中..."

            presenter.addBandCard(
                et_card_number.text.toString(),
                et_id_card.text.toString(),
                et_user_name.text.toString(),
                et_bank_name.text.toString()
            )
        }
        iv_back.setOnClickListener { dismiss() }

        show()
    }


    fun resetButton() {
        btn_add.isEnabled = true
        btn_add.isClickable = true
        btn_add.text = "添加"
    }
}