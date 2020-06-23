package com.zhkj.smartpolice.app.login.view

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.app.login.bean.UserInfoBean

/**
 * Desc
 * Author 杨宁
 * Mail yang122612@yeah.net
 * Date 2020/6/23 19:45
 */
interface LoginView: IBaseView {
    /**
     * 用户登录后返回信息
     */
    fun onUserLogin(userinfobean: UserInfoBean) {}
}