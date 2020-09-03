package com.zhkj.smartpolice.login.model

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class LoginPresenter(iBaseView: IBaseView) : LoginContract.Presenter(iBaseView) {

    private val model: LoginModel by lazy {
        LoginModel()
    }

    override fun doLogin(username: String, password: String) {
        launch(Main) {
            showLoading()
            model.doLogin(username, password)?.let {
                if (view is LoginContract.ILoginView) {
                    (view as LoginContract.ILoginView).doLogin(it)
                }
            }
            hideLoading()
        }
    }

    override fun sendVerificationCode(phone: String) {
        launch(Main) {
            showLoading()
            model.sendVerificationCode(phone)?.let {
                if (view is LoginContract.IForgetPwdView) {
                    (view as LoginContract.IForgetPwdView).sendVerificationCode(it)
                }
            }
            hideLoading()
        }
    }

    override fun forgetPassword(phone: String, newPwd: String, verificationCode: String) {
        launch(Main) {
            showLoading()
            model.forgetPassword(phone, newPwd, verificationCode)?.let {
                if (view is LoginContract.IForgetPwdView) {
                    (view as LoginContract.IForgetPwdView).forgetPassword(it)
                }
            }
            hideLoading()
        }
    }

    override fun modifyPassword(oldPwd: String, newPwd: String) {

    }


}