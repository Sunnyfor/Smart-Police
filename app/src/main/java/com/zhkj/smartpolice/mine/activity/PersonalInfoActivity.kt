package com.zhkj.smartpolice.mine.activity

import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.CameraUtil
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.utils.checkEmailFormat
import com.sunny.zy.utils.checkPhoneFormat
import com.sunny.zy.widget.dialog.CameraDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.bean.UserBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import kotlinx.android.synthetic.main.act_personal_info.*
import kotlinx.coroutines.cancel
import java.io.File


/**
 * 个人信息页
 */
class PersonalInfoActivity : BaseActivity(), UserContract.IUserInfoView, UserContract.IImageView {

    private var isEditable = false

    private var userBean = UserBean()

    private val presenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    private val cameraUtil: CameraUtil by lazy {
        CameraUtil(this)
    }

    private val cameraDialog: CameraDialog by lazy {
        CameraDialog(this, cameraUtil)
    }

    override fun setLayout(): Int = R.layout.act_personal_info

    override fun initView() {

        defaultTitle("个人信息")

        cameraUtil.setAspectXY(resources.getDimension(R.dimen.dp_100).toInt(), resources.getDimension(R.dimen.dp_100).toInt())
        cameraUtil.onResultListener = object : CameraUtil.OnResultListener {
            override fun onResult(file: File) {
                presenter.uploadImage(UrlConstant.UPLOAD_IMAGE_PATH_URL, file.path)
            }
        }


        // 默认不可编辑状态
        setEditState(false)

        setOnClickListener(
            iv_head,
            btn_modify, btn_cancel
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            iv_head.id -> cameraDialog.show()
            btn_modify.id -> doModify()
            btn_cancel.id -> {
                setEditState(false)
                loadData()
            }
        }
    }

    override fun loadData() {
        showLoading()
        presenter.loadUserInfo()
    }

    override fun close() {
        presenter.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraUtil.onActivityResult(requestCode, resultCode, data)
    }


    override fun uploadImage(bean: ImageBean) {
        bean.id.let {
            Glide.with(this)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it)
                .dontAnimate()
                .placeholder(R.drawable.svg_default_head)
                .into(iv_head)
        }

        userBean.avatar = bean.id
        ToastUtil.show("头像上传成功")
    }

    override fun loadUserInfo(data: UserBean) {
        hideLoading()

        userBean = data

        Glide.with(this)
            .load(UrlConstant.LOAD_IMAGE_PATH_URL + data.avatar)
            .dontAnimate()
            .placeholder(R.drawable.svg_default_head)
            .into(iv_head)

        et_username.setText(data.userName)
        et_name.setText(data.nickName)
        et_phone.setText(data.mobile)
        et_email.setText(data.email)

    }

    override fun updateUserInfo(msg: String) {
        if (msg == "success") {
            ToastUtil.show("修改成功")
        } else {
            ToastUtil.show(msg)
        }
    }

    /**
     * 设置编辑状态
     */
    private fun setEditState(isEditState: Boolean) {

        val editTextList = arrayListOf<View>(
            iv_head,
            et_name,
            et_phone,
            et_email
        )

        if (isEditState) { //可编辑状态
            isEditable = true
            btn_modify.text = "确认"
            btn_cancel.visibility = View.VISIBLE
            editTextList.forEach {
                it.isEnabled = true
            }
        } else {
            isEditable = false
            btn_modify.text = "编辑"
            btn_cancel.visibility = View.GONE
            editTextList.forEach {
                it.isEnabled = false
            }
        }
    }

    private fun doModify() {
        if (isEditable) {

            val username = et_username.text.toString()
            if (username.isEmpty()) {
                ToastUtil.show("姓名不能为空")
                return
            }

            val nickname = et_name.text.toString()

            val phone = et_phone.text.toString()
            if (!checkPhoneFormat(phone)) {
                ToastUtil.show("请输入正确的邮箱")
                return
            }

            val email = et_email.text.toString()
            if (!checkEmailFormat(email)) {
                ToastUtil.show("请输入正确的邮箱")
                return
            }

            userBean.userName = username
            userBean.nickName = nickname
            userBean.mobile = phone
            userBean.email = email

            presenter.updateUserInfo(userBean)
        }

        setEditState(!isEditable)
    }

}