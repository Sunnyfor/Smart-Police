package com.zhkj.smartpolice.mine.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.bean.DictBean
import com.sunny.zy.utils.CameraUtil
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.utils.checkPhoneFormat
import com.sunny.zy.widget.dialog.CameraDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.bean.UserBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import com.zhkj.smartpolice.utils.dict.DictContract
import com.zhkj.smartpolice.utils.dict.PickerViewUtil
import com.zhkj.smartpolice.utils.dict.bean.DeptBean
import com.zhkj.smartpolice.utils.dict.bean.PickBean
import com.zhkj.smartpolice.widget.dialog.DeptDialog
import kotlinx.android.synthetic.main.act_personal_info.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.io.File


/**
 * 个人信息页
 */
class PersonalInfoActivity : BaseActivity(), UserContract.IUserInfoView, UserContract.IImageView, DictContract.IDictView, DictContract.IDeptView {

    companion object {
        fun intent(context: Context, isModify: Boolean? = false) {
            val intent = Intent(context, PersonalInfoActivity::class.java)
            intent.putExtra("isModify", isModify)
            context.startActivity(intent)
        }
    }

    private val isModify: Boolean by lazy {
        intent.getBooleanExtra("isModify", false)
    }

    private val presenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    private val pickerViewUtil: PickerViewUtil by lazy {
        PickerViewUtil(this)
    }

    private val cameraUtil: CameraUtil by lazy {
        CameraUtil(this)
    }

    private val cameraDialog: CameraDialog by lazy {
        CameraDialog(this, cameraUtil)
    }

    private val deptDialog: DeptDialog by lazy {
        DeptDialog(this)
    }

    private val dictPresenter: DictContract.Presenter by lazy {
        DictContract.Presenter(this)
    }

    private var isEditable = false

    private var userBean = UserBean()

    private val deptList = ArrayList<DeptBean>()

    private var mSexId: String? = null        //性别id
    private var mPositionId: String? = null   //职务id
    private var mUserTypeId: String? = null   //用户类型id
    private var mDepartmentId: String? = null //岗位id

    override fun setLayout(): Int = R.layout.act_personal_info

    override fun initView() {

        defaultTitle(if (isModify) "用户信息完善" else "个人信息")

        cameraUtil.setAspectXY(resources.getDimension(R.dimen.dp_100).toInt(), resources.getDimension(R.dimen.dp_100).toInt())
        cameraUtil.onResultListener = object : CameraUtil.OnResultListener {
            override fun onResult(file: File) {
                presenter.uploadImage(UrlConstant.UPLOAD_IMAGE_PATH_URL, file.path)
            }
        }


        // 默认不可编辑状态
        setEditState(false)

        setOnClickListener(
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
            R.id.item_sex -> dictPresenter.loadDictList(dictPresenter.sex)
            R.id.item_position -> dictPresenter.loadDictList(dictPresenter.position)
            R.id.item_user_type -> dictPresenter.loadDictList(dictPresenter.userType)
            R.id.item_department -> {
                deptDialog.show()
                deptDialog.onConfirmBtnListener = {
                    userBean.deptId = it.deptId
                    userBean.deptName = it.name

                    mDepartmentId = it.deptId
                    item_department.endTextView.text = it.name
                }
            }
        }
    }

    override fun loadData() {
        presenter.loadUserInfo()
        dictPresenter.loadDeptList()
    }

    override fun close() {
        presenter.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraUtil.onActivityResult(requestCode, resultCode, data)
    }

    override fun loadDeptList(data: ArrayList<DeptBean>) {
        deptList.clear()
        deptList.addAll(data.filter { it.parentId == "-1" })

        deptList.forEach { firstBean ->
            firstBean.childrenList.addAll(data.filter { it.parentId == firstBean.deptId })
            firstBean.childrenList.forEach {
                it.listLevel = 2
            }

            firstBean.childrenList.forEach { twoBean ->
                twoBean.childrenList.addAll(data.filter { it.parentId == twoBean.deptId })
                twoBean.childrenList.forEach {
                    it.listLevel = 3
                }

            }
        }

        deptDialog.deptList = deptList

        data.find { it.deptId == userBean.deptId }?.let {
            item_department.endTextView.text = it.name
        }
    }

    override fun loadDictList(data: ArrayList<DictBean>) {
        val list = ArrayList<PickBean>()
        data.forEach {
            list.add(PickBean().apply { init(it) })
        }
        pickerViewUtil.showSingleChoice(list, object : PickerViewUtil.OnSingleChoiceResultListener {
            override fun onPickerViewResult(id: String, value: String) {
                when (data[0].type) {
                    dictPresenter.sex -> {
                        mSexId = id
                        item_sex.endTextView.text = value
                    }
                    dictPresenter.position -> {
                        mPositionId = id
                        item_position.endTextView.text = value
                    }
                    dictPresenter.userType -> {
                        mUserTypeId = id
                        item_user_type.endTextView.text = value
                    }
                }
            }
        })
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

        mSexId = data.sex
        mPositionId = data.position
        mUserTypeId = data.userType
        mDepartmentId = data.deptId

        dictPresenter.launch {
            item_sex.endTextView.text = dictPresenter.getDictBean(userBean.sex ?: "", dictPresenter.sex)
            item_position.endTextView.text = dictPresenter.getDictBean(userBean.position ?: "", dictPresenter.position)
            item_user_type.endTextView.text = dictPresenter.getDictBean(userBean.userType ?: "", dictPresenter.userType)
        }
    }

    override fun updateUserInfo(msg: String) {
        if (msg == "success") {
            UserManager.setUserBean(userBean)
            ToastUtil.show("修改成功")
        } else {
            ToastUtil.show(msg)
        }
    }


    /**
     * 设置编辑状态
     */
    private fun setEditState(isEditState: Boolean) {

        val viewList = arrayListOf<View>(
            iv_head,
            et_name, et_phone, et_email,
            item_sex, item_department, item_position, item_user_type
        )

        viewList.forEach {
            it.setOnClickListener(this)
        }

        if (isEditState) { //可编辑状态
            isEditable = true
            btn_modify.text = "确认"
            btn_cancel.visibility = View.VISIBLE
            viewList.forEach {
                it.isEnabled = true
            }
        } else {
            isEditable = false
            btn_modify.text = "编辑"
            btn_cancel.visibility = View.GONE
            viewList.forEach {
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

            val phone = et_phone.text.toString()
            if (!checkPhoneFormat(phone)) {
                ToastUtil.show("请输入正确的手机号")
                return
            }

            if (mDepartmentId == null) {
                ToastUtil.show("请选择部门")
                return
            }

            if (mPositionId == null) {
                ToastUtil.show("请选择职位")
                return
            }

            if (mUserTypeId == null) {
                ToastUtil.show("请选择用户类型")
                return
            }

            val nickname = et_name.text.toString()
            val email = et_email.text.toString()

            userBean.userName = username
            userBean.nickName = nickname
            userBean.mobile = phone
            userBean.email = email

            userBean.sex = mSexId
            userBean.position = mPositionId
            userBean.userType = mUserTypeId

            presenter.updateUserInfo(userBean)
        }

        setEditState(!isEditable)
    }

}