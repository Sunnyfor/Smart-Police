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
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.SpUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.MainActivity
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.presenter.LoginPresenter
import com.zhkj.smartpolice.login.view.LoginView
import com.zhkj.smartpolice.mine.bean.UserBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import kotlinx.android.synthetic.main.act_login.*
import kotlinx.coroutines.cancel

@Route(path = RouterManager.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity(), LoginView, UserContract.IUserInfoView {

    @JvmField
    @Autowired
    var logout: Boolean = false

    private var isStatus: Boolean = false

    private var mUsername = SpUtil.getString(SpUtil.username)
    private var mPassword = SpUtil.getString(SpUtil.password)


    private val loginPresenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    private val userPresenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    companion object {
        fun intent(context: Context, isLogout: Boolean = false) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.putExtra("logout", isLogout)
            context.startActivity(intent)
        }
    }


    override fun setLayout(): Int = R.layout.act_login


    override fun initView() {
        setOnClickListener(
            btn_login,
            iv_eye,
            tv_modify_pwd
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_login.id -> {
                if (et_username.text.toString().isEmpty()) {
                    ToastUtil.show("用户名不能为空！")
                    return
                }
                if (et_password.text.toString().isEmpty()) {
                    ToastUtil.show("密码不能为空！")
                    return
                }
                hideKeyboard()
                loginPresenter.onUserLogin(et_username.text.toString(), et_password.text.toString())
            }
            iv_eye.id -> {
                if (isStatus) {
                    iv_eye.setImageResource(R.drawable.svg_login_hide_password)
                    et_password.transformationMethod = PasswordTransformationMethod.getInstance()
                } else {
                    iv_eye.setImageResource(R.drawable.svg_login_show_password)
                    et_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                }
                et_password.setSelection(et_password.text.toString().length)
                isStatus = !isStatus
            }
            tv_modify_pwd.id -> {
                startActivity(Intent(this, ModifyPasswordActivity::class.java))
            }
        }
    }

    override fun loadData() {
        logout = intent.getBooleanExtra("logout", false)
        if (logout) {
            ZyFrameStore.finishAllActivity(this)
            SpUtil.clear()
        } else {
            if (mUsername.isNotEmpty() && mPassword.isNotEmpty()) {
                et_username.setText(mUsername)
                et_password.setText(mPassword)
                loginPresenter.onUserLogin(mUsername, mPassword)
            }
        }
    }

    override fun close() {
        userPresenter.cancel()
    }

    override fun userLogin(baseModel: BaseModel<ArrayList<UserInfoBean>>) {
        baseModel.let {
            if (it.code == "0") {
                it.data?.let { data ->
                    if (data.size > 0) {
                        UserManager.setInfo(data[0])
                    }
                }
                showLoading()
                userPresenter.loadUserInfo()
            } else {
                ToastUtil.show(it.msg)
            }
        }
    }

    override fun loadUserInfo(data: UserBean) {
        hideLoading()
        UserManager.setUserBean(data)

        SpUtil.setString(SpUtil.username, et_username.text.toString().trim())
        SpUtil.setString(SpUtil.password, et_password.text.toString().trim())

        ToastUtil.show("登录成功")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun updateUserInfo(msg: String) {

    }
}