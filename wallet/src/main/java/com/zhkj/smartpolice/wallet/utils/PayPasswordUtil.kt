package com.zhkj.smartpolice.wallet.utils

import android.view.Gravity
import android.view.View
import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.ErrorViewType
import com.sunny.zy.base.IBaseView
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.wallet.contract.WalletContract
import com.zhkj.smartpolice.wallet.presenter.WalletPresenter
import com.zhkj.smartpolice.wallet.widget.InputPayPasswordPopupWindow

class PayPasswordUtil(
    var view: View,
    var iBaseView: IBaseView
) :
    WalletContract.IPayPassWordView {

    var verifyPayPassword: ((isOk: Boolean) -> Unit)? = null

    var updatePayPassword: ((baseModel: BaseModel<Any>) -> Unit)? = null


    private val presenter: WalletContract.Presenter by lazy {
        WalletPresenter(this)
    }


    var pay = 0
    var modify = 1


    var type = pay


    /**
     * 对外开放的调用入口
     */
    fun showPayPasswordWindow(type: Int = pay) {
        this.type = type
        presenter.isSettingPayPassword() //检测是否设置过支付密码
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
    private fun showVerifyPayPassword() {
        val popupWindow =
            InputPayPasswordPopupWindow(view.context) { popupWindow: InputPayPasswordPopupWindow, password: String ->
                presenter.verifyPayPassword(password)
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
                view.context.getString(R.string.create_pay_password_hint)
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
                view.context.getString(R.string.create_pay_password),
                view.context.getString(R.string.create_pay_sure_password_hint)
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
            //有支付密码
            if (type == modify) {
                showModifyPayPassword()
            } else {
                showVerifyPayPassword()
            }
        } else {
            showCreatePayPassword()
        }
    }

    override fun verifyPayPassword(isOk: Boolean) {
        verifyPayPassword?.invoke(isOk)
    }

    override fun updatePayPassword(baseModel: BaseModel<Any>) {
        updatePayPassword?.invoke(baseModel)
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