package com.zhkj.smartpolice.app.login.view

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.app.login.bean.UserInfoBean


interface LoginView: IBaseView {
    /**
     * 用户登录后返回信息
     */
    fun onUserLogin(userinfobean: UserInfoBean) {}
}