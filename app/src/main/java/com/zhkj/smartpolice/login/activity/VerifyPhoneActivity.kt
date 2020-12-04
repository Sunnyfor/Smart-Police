package com.zhkj.smartpolice.login.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseModel
import com.sunny.zy.utils.SpUtil
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.utils.isPhoneValid
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.MainActivity
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.model.LoginContract
import com.zhkj.smartpolice.login.presenter.LoginPresenter
import com.zhkj.smartpolice.login.view.LoginView
import com.zhkj.smartpolice.mine.bean.UserBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import com.zhkj.smartpolice.utils.SpKey
import kotlinx.android.synthetic.main.act_verify_phone.*
import kotlinx.coroutines.cancel

/**
 * 验证手机号
 */
class VerifyPhoneActivity : BaseActivity(), LoginView, LoginContract.IForgetPwdView, UserContract.IUserInfoView {

    private val username: String by lazy {
        intent.getStringExtra("username") ?: ""
    }

    private val password: String by lazy {
        intent.getStringExtra("password") ?: ""
    }

    private val presenter: com.zhkj.smartpolice.login.model.LoginPresenter by lazy {
        com.zhkj.smartpolice.login.model.LoginPresenter(this)
    }


    private val loginPresenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    private val userPresenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    companion object {
        fun intent(context: Context, username: String, password: String) {
            val intent = Intent(context, VerifyPhoneActivity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("password", password)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_verify_phone

    override fun initView() {

        defaultTitle("忘记密码")

        btn_obtain.setOnClickListener(this)
        btn_commit.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_obtain.id -> {
                val phone = et_phone.text.toString().trim()
                if (isPhoneValid(phone)) {
                    presenter.sendVerificationCode(phone)
                    hideKeyboard()
                }
            }
            btn_commit.id -> {
                if (!isPhoneValid(et_phone.text.toString())) {
                    et_phone.setShakeAnimation()
                    return
                }
                if (et_verify_code.text.toString().isEmpty()) {
                    ToastUtil.show("请输入验证码！")
                    return
                }
                hideKeyboard()
                loginPresenter.onUserLogin(username, password, et_verify_code.text.toString())
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {
        loginPresenter.cancel()
        userPresenter.cancel()
        presenter.cancel()
    }

    override fun sendVerificationCode(data: String) {
        when {
            data.contains("【模拟短信】验证码") -> ToastUtil.show(data)
            data.contains("用户不存在") -> ToastUtil.show(data)
            else -> {
                ToastUtil.show("验证码已发送，请注意查收")
                TimeCount().start()
                btn_obtain.isClickable = true
            }
        }
    }

    override fun forgetPassword(data: String) {

    }

    override fun userLogin(baseModel: BaseModel<ArrayList<UserInfoBean>>) {
        baseModel.let {
            if (it.code == "0") {
                it.data?.let { data ->
                    if (data.size > 0) {
                        UserManager.setInfo(data[0])
                        SpUtil.setObject(UserInfoBean::class.java.simpleName, data[0])
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
        data.userId?.let {
            SpUtil.setString(SpUtil.userId, it)
        }
        UserManager.setUserBean(data)
        if (username.isNotEmpty() || password.isNotEmpty()) {
            SpUtil.setString(SpKey.username, username)
            SpUtil.setString(SpKey.password, password)
        }

        ToastUtil.show("登录成功")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun updateUserInfo(msg: String) {

    }

    /**
     * 计时器
     */
    internal inner class TimeCount : CountDownTimer(60 * 1000, 1000) {

        override fun onTick(millisUntilFinished: Long) {
            btn_obtain.isClickable = false
            btn_obtain.text = ("( ${millisUntilFinished / 1000}) 秒重新发送")
        }

        override fun onFinish() {
            btn_obtain.text = "重新获取"
            btn_obtain.isClickable = true
        }
    }


}