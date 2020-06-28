package com.zhkj.smartpolice.login.view

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.login.bean.UserInfoBean


interface LoginView: IBaseView {
    /**
     * 用户登录后返回信息
     */
    fun onUserLogin(userInfoBean: UserInfoBean) {}

    /**
     * 修改登录密码返回信息
     */
    fun onAlterPassword(userinfobean: UserInfoBean){}
}