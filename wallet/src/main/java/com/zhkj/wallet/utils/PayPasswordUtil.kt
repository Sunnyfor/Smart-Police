package com.zhkj.wallet.utils

import android.view.Gravity
import android.view.View
import com.sunny.zy.base.ErrorViewType
import com.sunny.zy.base.IBaseView
import com.sunny.zy.utils.ToastUtil
import com.zhkj.wallet.R
import com.zhkj.wallet.activity.PayResultActivity
import com.zhkj.wallet.contract.WalletContract
import com.zhkj.wallet.presenter.WalletPresenter
import com.zhkj.wallet.widget.InputPayPasswordPopupWindow

class PayPasswordUtil(
    var view: View,
    var iBaseView: IBaseView
) :
    WalletContract.IPayPassWordView {

    var onPaySuccessResult: (() -> Unit)? = null

    var onUpdatePayPassword: (() -> Unit)? = null


    private val presenter: WalletPresenter by lazy {
        WalletPresenter(this)
    }


    var pay = 0
    var create = 1
    var modify = 2


    var type = pay

    var ordersId: String? = null

    var goodsPrice = 0f

    /**
     * 对外开放的调用入口
     */
    fun showPayPasswordWindow(type: Int, ordersId: String? = null, goodsPrice: Float? = 0f) {
        this.ordersId = ordersId
        this.type = type
        this.goodsPrice = goodsPrice ?: 0f
        if (type == create) {
            showCreatePayPassword()
        } else {
            presenter.isSettingPayPassword() //检测是否设置过支付密码
        }

    }

    /**
     * 显示创建支付密码弹窗
     */
    private fun showCreatePayPassword() {
        val popupWindow =
            InputPayPasswordPopupWindow(
                view.context,
                view.context.getString(R.string.create_pay_password),
                view.context.getString(R.string.create_pay_password_hint)

            ) { popupWindow: InputPayPasswordPopupWindow, password: String ->
                popupWindow.dismiss()
                showSurePayPassword(password)
            }
        popupWindow.showAtLocation(
            view, Gravity.BOTTOM, 0, 0
        )
    }

    /**
     * 确认密码弹框
     */
    private fun showSurePayPassword(
        lastPassword: String
    ) {
        val popupWindow =
            InputPayPasswordPopupWindow(
                view.context,
                view.context.getString(R.string.create_pay_password),
                view.context.getString(R.string.create_pay_sure_password_hint)
            ) { popupWindow: InputPayPasswordPopupWindow, password: String ->
                if (password == lastPassword) {
                    popupWindow.dismiss()
                    presenter.updatePayPassword(lastPassword, password)
                } else {
                    ToastUtil.show("两次输入的密码不一致！")
                }
            }
        popupWindow.showAtLocation(
            view, Gravity.BOTTOM, 0, 0
        )
    }


    /**
     * 日常验证支付密码
     */
    private fun showPayPassword(orderId: String) {
        val popupWindow =
            InputPayPasswordPopupWindow(view.context) { popupWindow: InputPayPasswordPopupWindow, password: String ->
                popupWindow.dismiss()
                presenter.pay(orderId, password)
            }
        popupWindow.showAtLocation(
            view, Gravity.BOTTOM, 0, 0
        )
    }


    /**
     * 修改支付密码
     */
    private fun showModifyPayPassword() {
        val popupWindow =
            InputPayPasswordPopupWindow(
                view.context,
                view.context.getString(R.string.modify_pay_password),
                view.context.getString(R.string.pay_password_hint)
            ) { popupWindow: InputPayPasswordPopupWindow, password: String ->
                popupWindow.dismiss()
                showNewPayPassword(password)
            }
        popupWindow.showAtLocation(
            view, Gravity.BOTTOM, 0, 0
        )
    }


    /**
     * 设置新支付密码
     */
    private fun showNewPayPassword(
        oldPassword: String
    ) {
        val popupWindow =
            InputPayPasswordPopupWindow(
                view.context,
                view.context.getString(R.string.modify_pay_password),
                view.context.getString(R.string.modify_pay_password_hint)
            ) { popupWindow: InputPayPasswordPopupWindow, password: String ->
                popupWindow.dismiss()
                presenter.updatePayPassword(oldPassword, password)
            }
        popupWindow.showAtLocation(
            view, Gravity.BOTTOM, 0, 0
        )
    }


    /**
     * 是否有支付密码
     */
    override fun isSettingPayPassword(hasPayPassword: Boolean) {
        if (hasPayPassword) {
            when (type) {
                //修改支付密码
                modify -> showModifyPayPassword()
                //去支付
                pay -> {
                    if (goodsPrice > presenter.balance) {
                        PayResultActivity.intent(view.context, "2")
                        return
                    }
                    ordersId?.let {
                        showPayPassword(it)
                    }
                }
            }
        } else {
            showCreatePayPassword()
        }
    }

    override fun paySuccess() {
        onPaySuccessResult?.invoke()
    }

    override fun updatePayPassword() {
        onUpdatePayPassword?.invoke()
    }

    override fun showLoading() {
        iBaseView.showLoading()
    }

    override fun hideLoading() {
        iBaseView.hideLoading()
    }

    override fun showError(errorType: ErrorViewType) {

    }

    override fun hideError(errorType: ErrorViewType) {

    }

    override fun showMessage(message: String) {

    }
}