package com.zhkj.smartpolice.login.activity

import android.text.TextUtils
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.login.presenter.LoginPresenter
import com.zhkj.smartpolice.login.view.LoginView
import kotlinx.android.synthetic.main.act_alter_password.*

class AlterPasswordActivity : BaseActivity(), LoginView {

    override fun setLayout(): Int = R.layout.act_alter_password

    private val loginPresenter: LoginPresenter by lazy {
        LoginPresenter(this)
    }

    override fun initView() {
        alterButton.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.alterButton -> {
                if (!TextUtils.isEmpty(formerPassword.text.toString())) {
                    if (!TextUtils.isEmpty(newPassword.text.toString())) {
                        loginPresenter.onAlterPassword(
                            formerPassword.text.toString(),
                            newPassword.text.toString()
                        )
                    } else {
                        ToastUtil.show("新密码不能为空")
                    }
                } else {
                    ToastUtil.show("原密码不能为空")
                }
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }

    override fun onAlterPassword(userInfoBean: String) {
        super.onAlterPassword(userInfoBean)
        userInfoBean?.let {
            if (userInfoBean.equals("0")) {
                ToastUtil.show("修改成功")
            }
        }
    }
}