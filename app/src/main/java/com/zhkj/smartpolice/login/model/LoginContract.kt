package com.zhkj.smartpolice.login.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.login.bean.UserInfoBean

interface LoginContract {

    interface ILoginView : IBaseView {
        fun doLogin(data: ArrayList<UserInfoBean>)
    }

    interface IForgetPwdView : IBaseView {
        fun sendVerificationCode(data: String)
        fun forgetPassword(data: String)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        abstract fun doLogin(username: String, password: String)

        abstract fun sendVerificationCode(phone: String)

        abstract fun forgetPassword(phone: String, newPwd: String, verificationCode: String)

        abstract fun modifyPassword(oldPwd: String, newPwd: String)

    }
}