package com.zhkj.smartpolice.login.Presenter

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.view.LoginView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

/**
 * Desc
 * Author 杨宁
 * Mail yang122612@yeah.net
 * Date 2020/6/23 19:41
 */
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
//                httpResultBean?.bean.let {
//                    if (it != null) {
//                        view?.onUserLogin(it)
//                    }
//                }
                view?.onUserLogin(httpResultBean.bean ?: return@launch)
            }
        }
    }
}