package com.zhkj.smartpolice.login.activity

import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.login.Presenter.UserLoginPresenter
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.view.LoginView

class LoginActivity : BaseActivity(), LoginView {
    override fun setLayout(): Int = R.layout.act_login

    private val userLoginPresenter: UserLoginPresenter by lazy {
        UserLoginPresenter(this)
    }

    override fun initView() {

    }

    override fun loadData() {
        userLoginPresenter.onUserLogin("admin","admin")
    }

    override fun onClickEvent(view: View) {
    }

    override fun close() {
    }

    override fun onUserLogin(userinfobean: UserInfoBean) {
        super.onUserLogin(userinfobean)
        userinfobean?.let {
            LogUtil.i("当前下载的数据是=====$it")
        }
    }
}