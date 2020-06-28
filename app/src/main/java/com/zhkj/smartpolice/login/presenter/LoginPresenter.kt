package com.zhkj.smartpolice.login.presenter

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.view.LoginView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class LoginPresenter(view: LoginView): BasePresenter<LoginView>(view) {

    fun onUserLogin(userName: String, password: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["userName"] = userName
        params["password"] = password
        val httpResultBean = object : HttpResultBean<UserInfoBean>(){ }
        launch (Main){
            ZyHttp.post(UrlConstant.USER_LOGIN,params,httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onUserLogin(httpResultBean.bean ?: return@launch)
            }
        }
    }
}