package com.zhkj.smartpolice.login.view

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.login.bean.UserInfoBean


interface LoginView : IBaseView {

    /**
     * 用户登录后返回信息
     */
    fun userLogin(baseModel: BaseModel<ArrayList<UserInfoBean>>) {}

    /**
     * 该账户已绑定其他手机,需要手机号验证
     */
    fun doVerifyPhone(msg: String) {}

    /**
     * 修改登录密码返回信息
     */
    fun modifyPassword(code: String) {}
}