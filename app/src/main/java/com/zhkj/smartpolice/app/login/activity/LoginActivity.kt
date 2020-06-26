package com.zhkj.smartpolice.app.login.activity

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.MainActivity
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
        userLoginPresenter.onUserLogin("admin", "admin")
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                if (userName.text.toString() != null) {

                    if (userPassword.text.toString() != null) {
                        userLoginPresenter.onUserLogin(
                            userName.text.toString(),
                            userPassword.text.toString()
                        )
                    } else {
                        ToastUtil.show("密码不能为空！")
                    }
                } else {
                    ToastUtil.show("用户名不能为空！")
                }
            }
        }
    }

    override fun close() {
    }

    override fun onUserLogin(userinfobean: UserInfoBean) {
        super.onUserLogin(userinfobean)
        userinfobean?.let {
            ToastUtil.show("登录成功")
            var intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}