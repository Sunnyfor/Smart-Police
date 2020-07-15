package com.zhkj.smartpolice.login.activity

import android.content.Context
import android.content.Intent
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.SpUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.MainActivity
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.presenter.LoginPresenter
import com.zhkj.smartpolice.login.view.LoginView
import kotlinx.android.synthetic.main.act_login.*

@Route(path = RouterManager.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity(), LoginView {

    @JvmField
    @Autowired
    var logout: Boolean = false

    private var isStatus: Boolean = false

    override fun setLayout(): Int = R.layout.act_login

    private val loginPresenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    companion object {
        fun intent(context: Context, isLogout: Boolean = false) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra("logout", isLogout)
            context.startActivity(intent)
        }
    }

    override fun initView() {

        logout = intent.getBooleanExtra("logout", false)

        if (logout) {
            //关闭其他所有页面
            ZyFrameStore.finishAllActivity(this)
            SpUtil.clear()
        }

        loginButton.setOnClickListener(this)
        passwordType.setOnClickListener(this)
        tvRevampPassword.setOnClickListener(this)
        val userName = SpUtil.getString("userName")
        val userPassword = SpUtil.getString("userPassword")
        LogUtil.i("登录参数是==========userName==$userName========userPassword=====$userPassword")
        if (userName.isNotEmpty() && userPassword.isNotEmpty()) {
            loginPresenter.onUserLogin(
                SpUtil.getString("userName"),
                SpUtil.getString("userPassword")
            )
        }
    }

    override fun loadData() {
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.loginButton -> {
                if (userName.text.toString().isEmpty()) {
                    ToastUtil.show("用户名不能为空！")
                    return
                }
                if (userPassword.text.toString().isEmpty()) {
                    ToastUtil.show("密码不能为空！")
                    return
                }
                hideKeyboard()
                loginPresenter.onUserLogin(userName.text.toString(), userPassword.text.toString())
            }
            R.id.passwordType -> {
                if (isStatus) {
                    passwordType.setBackgroundResource(R.drawable.svg_login_hide_password)
                    userPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    passwordType.setBackgroundResource(R.drawable.svg_login_show_password)
                    userPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                }
                isStatus = !isStatus
            }
            R.id.tvRevampPassword -> {
                startActivity(Intent(this, AlterPasswordActivity::class.java))
            }
        }
    }

    override fun close() {
    }

    override fun onUserLogin(baseModel: BaseModel<ArrayList<UserInfoBean>>) {
        baseModel.let {
            LogUtil.i("获取登录数据========$baseModel")
            it.data?.let { data ->
                if (data.size > 0) {
                    UserManager.setInfo(data.get(0))
                }
            }
            if (it.code != "0") {
                ToastUtil.show(it.msg)
            } else {
                if (userName.text.toString().isNotEmpty() && userPassword.text.toString()
                        .isNotEmpty()
                ) {
                    SpUtil.setString("userName", userName.text.toString())
                    SpUtil.setString("userPassword", userPassword.text.toString())
                }
                ToastUtil.show("登录成功")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}