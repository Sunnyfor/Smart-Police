package com.zhkj.smartpolice.login.presenter

import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.view.LoginView
import com.zhkj.smartpolice.utils.Base64Util
import com.zhkj.smartpolice.utils.input.OpenUDID
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class LoginPresenter(view: LoginView) : BasePresenter<LoginView>(view) {

    /**
     * 登录用户信息接口请求
     */
    fun onUserLogin(userName: String, password: String, verifyCode: String? = null) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["userName"] = userName
        params["password"] = Base64Util.encode(password)
        params["loginType"] = "android"
        params["phoneuuid"] = OpenUDID.getOpenUDIDInContext(ZyFrameStore.getContext())

        verifyCode?.let {
            params["captcha"] = it
        }

        val httpResultBean = object : HttpResultBean<BaseModel<ArrayList<UserInfoBean>>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.USER_LOGIN_URL, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()

                if (httpResultBean.bean?.code == "300") {
                    view?.doVerifyPhone(httpResultBean.msg ?: "")
                } else {
                    view?.userLogin(httpResultBean.bean ?: return@launch)
                }

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
            ZyHttp.post(UrlConstant.MODIFY_PASSWORD_URL, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.modifyPassword(httpResultBean.bean?.code ?: return@launch)
            }
        }
    }
}