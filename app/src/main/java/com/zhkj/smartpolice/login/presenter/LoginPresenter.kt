package com.zhkj.smartpolice.login.presenter

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.view.LoginView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class LoginPresenter(view: LoginView): BasePresenter<LoginView>(view) {

    /**
     * 登录用户信息接口请求
     */
    fun onUserLogin(userName: String, password: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["userName"] = userName
        params["password"] = password

        val httpResultBean = object : HttpResultBean<BaseModel<ArrayList<UserInfoBean>>>(){ }
        launch (Main){
            ZyHttp.post(UrlConstant.USER_LOGIN,params,httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onUserLogin(httpResultBean.bean?: return@launch)
            }
        }
    }


    /**
     * 修改用户密码接口请求
     */
    fun onAlterPassword(formerPassword: String, newPassword: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["password"] = formerPassword
        params["newPassword"] = newPassword
        var httpResultBean = object : HttpResultBean<BaseModel<String>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.USER_ALTER_PASSWORD, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onAlterPassword(httpResultBean.bean?.code?:return@launch)
            }
        }
    }
}