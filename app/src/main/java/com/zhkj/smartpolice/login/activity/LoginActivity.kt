package com.zhkj.smartpolice.login.activity

import android.content.Context
import android.content.Intent
import android.os.Build
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
import com.umeng.message.PushAgent
import com.umeng.message.tag.TagManager
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.MainActivity
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.login.bean.UserInfoBean
import com.zhkj.smartpolice.login.presenter.LoginPresenter
import com.zhkj.smartpolice.login.view.LoginView
import com.zhkj.smartpolice.mine.bean.UserBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import com.zhkj.smartpolice.utils.SpKey
import com.zhkj.smartpolice.utils.fingerprint.BiometricPromptManager
import kotlinx.android.synthetic.main.act_login.*
import kotlinx.coroutines.cancel

@Route(path = RouterManager.LOGIN_ACTIVITY)
class LoginActivity : BaseActivity(), LoginView, UserContract.IUserInfoView {

    private var mUsername = SpUtil.getString(SpKey.username)
    private var mPassword = SpUtil.getString(SpKey.password)
    private var isRememberPassword = SpUtil.getBoolean(SpKey.isRememberPassword, false)

    @JvmField
    @Autowired
    var logout: Boolean = false

    private var isStatus: Boolean = false

    private val biometricPromptManager: BiometricPromptManager by lazy {
        BiometricPromptManager.from(this)
    }

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

        // 记住密码
        if (isRememberPassword) {
            et_username.setText(mUsername)
            et_password.setText(mPassword)
            cb_remember_pwd.isChecked = true
        }

        cb_remember_pwd.setOnCheckedChangeListener { _, isChecked ->
            isRememberPassword = isChecked
        }


        // 指纹信息
        val stringBuilder = StringBuilder()
        stringBuilder.append("SDK version is " + Build.VERSION.SDK_INT)
            .append("\n")
            .append("isHardwareDetected : " + biometricPromptManager.isHardwareDetected)
            .append("\n")
            .append("hasEnrolledFingerprints : " + biometricPromptManager.hasEnrolledFingerprints())
            .append("\n")
            .append("isKeyguardSecure : " + biometricPromptManager.isKeyguardSecure)
            .append("\n")

        LogUtil.e("指纹信息：\n$stringBuilder")

        setOnClickListener(
            btn_login,
            iv_eye,
            tv_forget_pwd,
            iv_fingerprint
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
            tv_forget_pwd.id -> startActivity(Intent(this, ForgetPasswordActivity::class.java))
            iv_fingerprint.id -> doFingerprint()
        }
    }

    override fun loadData() {
        logout = intent.getBooleanExtra("logout", false)
        if (logout) {
            disable()
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

        if (isRememberPassword) {
            SpUtil.setObject(UserBean::class.java.simpleName, data)
            SpUtil.setString(SpKey.username, et_username.text.toString().trim())
            SpUtil.setString(SpKey.password, et_password.text.toString().trim())
        }

        ToastUtil.show("登录成功")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun updateUserInfo(msg: String) {

    }

    private fun disable() {
        PushAgent.getInstance(this).deleteAlias(UserManager.getUserBean().userId, "ytzhjb") { isSuccess: Boolean, message: String ->
            LogUtil.i("友盟推送删除别名: isSuccess = $isSuccess ||| message = $message")
        }

        PushAgent.getInstance(this).tagManager.deleteTags(TagManager.TCallBack { isSuccess, message ->
            LogUtil.i("友盟推送删除Tag: isSuccess = $isSuccess ||| message = $message")
        }, UserManager.getUserBean().deptId)
    }

    /**
     * 指纹验证
     */
    private fun doFingerprint() {

        if (!biometricPromptManager.isHardwareDetected) {
            ToastUtil.show("您的系统版本过低，不支持指纹功能")
        } else if (biometricPromptManager.isKeyguardSecure) {
            ToastUtil.show("您的手机不支持指纹功能")
        } else if (!biometricPromptManager.hasEnrolledFingerprints()) {
            ToastUtil.show("您至少需要在系统设置中添加一个指纹")
        } else {
            if (biometricPromptManager.isBiometricPromptEnable) {
                biometricPromptManager.authenticate(object : BiometricPromptManager.OnBiometricIdentifyCallback {
                    override fun onUsePassword() {
                        ToastUtil.show("onUsePassword")
                    }

                    override fun onSucceeded() {
                        ToastUtil.show("指纹验证成功")
                    }

                    override fun onFailed() {
                        ToastUtil.show("指纹验证失败")
                    }

                    override fun onError(code: Int, reason: String?) {
                        ToastUtil.show("指纹有误")
                    }

                    override fun onCancel() {
                        ToastUtil.show("指纹验证取消")
                    }
                })
            }
        }
    }
}