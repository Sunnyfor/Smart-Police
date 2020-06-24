package com.zhkj.smartpolice.app.login.activity

import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.login.Presenter.UserLoginPresenter
import com.zhkj.smartpolice.app.login.bean.UserInfoBean
import com.zhkj.smartpolice.app.login.view.LoginView
import kotlinx.android.synthetic.main.act_login.*

class LoginActivity : BaseActivity(), LoginView {

    override fun setLayout(): Int = R.layout.act_login

    private val userLoginPresenter: UserLoginPresenter by lazy {
        UserLoginPresenter(this)
    }

    override fun initView() {
        loginButton.setOnClickListener(this)
    }

    override fun loadData() {
        userLoginPresenter.onUserLogin("admin","admin")
    }

    override fun onClickEvent(view: View) {
        when(view.id) {
            R.id.loginButton -> {
                ToastUtil.show("点击了登录按钮")
            }
        }
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