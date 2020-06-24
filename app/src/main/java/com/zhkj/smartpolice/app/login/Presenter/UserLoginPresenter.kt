package com.zhkj.smartpolice.app.login.Presenter

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.login.bean.UserInfoBean
import com.zhkj.smartpolice.app.login.view.LoginView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class UserLoginPresenter(view: LoginView): BasePresenter<LoginView>(view) {

    fun onUserLogin(userName: String, password: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["userName"] = userName
        params["password"] = password
        var httpResultBean = object : HttpResultBean<UserInfoBean>(){ }
        launch (Main){
            ZyHttp.post(UrlConstant.USER_LOGIN,params,httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onUserLogin(httpResultBean.bean ?: return@launch)
            }
        }
    }
}