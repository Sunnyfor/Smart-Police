package com.zhkj.smartpolice.login.activity

import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.MainActivity
import com.zhkj.smartpolice.login.presenter.LoginPresenter
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.view.LoginView
import kotlinx.android.synthetic.main.act_login.*

class LoginActivity : BaseActivity(), LoginView {

    private var isStatus: Boolean = false

    override fun setLayout(): Int = R.layout.act_login

    private val loginPresenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    override fun initView() {
        loginButton.setOnClickListener(this)
        passwordType.setOnClickListener(this)
    }

    override fun loadData() {
        loginPresenter.onUserLogin("admin", "admin")
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                loginPresenter.onUserLogin(
                    userName.text.toString(),
                    userPassword.text.toString()
                )
            }

            R.id.passwordType -> {
                if (isStatus) {
                    passwordType.setBackgroundResource(R.drawable.svg_login_hide_password)
                    userPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    passwordType.setBackgroundResource(R.drawable.svg_login_show_password)
                    userPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
                isStatus = !isStatus
            }
        }
    }

    override fun close() {
    }

    override fun onUserLogin(userinfobean: UserInfoBean) {
        super.onUserLogin(userinfobean)
        userinfobean.let {
            ToastUtil.show("登录成功")
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}