package com.zhkj.smartpolice.login.activity

import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.login.presenter.LoginPresenter
import com.zhkj.smartpolice.login.view.LoginView
import kotlinx.android.synthetic.main.act_modify_password.*

class ModifyPasswordActivity : BaseActivity(), LoginView {

    override fun setLayout(): Int = R.layout.act_modify_password

    private val loginPresenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    override fun initView() {
        setOnClickListener(
            iv_back,
            tv_back,
            btn_modify
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            iv_back.id, tv_back.id -> finish()
            btn_modify.id -> {
                val oldPwd = et_old_pwd.text.toString()
                val newPwd = et_new_pwd.text.toString()
                if (oldPwd.isEmpty()) {
                    ToastUtil.show("原密码不能为空")
                    return
                }
                if (newPwd.isEmpty()) {
                    ToastUtil.show("新密码不能为空")
                    return
                }
                loginPresenter.onAlterPassword(oldPwd, newPwd)
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }

    override fun modifyPassword(code: String) {
        super.modifyPassword(code)
        if (code == "0") {
            ToastUtil.show("修改成功")
        }
    }
}