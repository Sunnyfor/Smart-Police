package com.zhkj.smartpolice.login.activity

import android.content.Intent
import android.os.CountDownTimer
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.utils.isPhoneValid
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.login.model.LoginContract
import com.zhkj.smartpolice.login.model.LoginPresenter
import kotlinx.android.synthetic.main.act_forget_password.*
import kotlinx.coroutines.cancel

/**
 * 忘记密码
 */
class ForgetPasswordActivity : BaseActivity(), LoginContract.IForgetPwdView {

    private val presenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    override fun setLayout(): Int = R.layout.act_forget_password

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
            btn_commit.id -> doForget()
        }
    }

    override fun loadData() {

    }

    override fun close() {
        presenter.cancel()
    }

    override fun forgetPassword(data: String) {
        ToastUtil.show("密码提交成功！")
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
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

    private fun doForget() {

        val phone = et_phone.text.toString()
        val newPwd = et_new_password.text.toString()
        val surePwd = et_sure_password.text.toString()
        val verificationCode = et_verify_code.text.toString()

        if (!isPhoneValid(phone)) {
            et_phone.setShakeAnimation()
            return
        }

        if (verificationCode.isEmpty()) {
            ToastUtil.show("请输入验证码")
            et_verify_code.setShakeAnimation()
            return
        }

        if (newPwd.length < 6) {
            ToastUtil.show("新密码不能小于6位！")
            return
        }

        if (surePwd.length < 6) {
            ToastUtil.show("确认密码不能小于6位！")
            return
        }

        if (newPwd != surePwd) {
            ToastUtil.show("新密码与确认密码不一致！")
            return
        }

        presenter.forgetPassword(phone, newPwd, verificationCode)

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