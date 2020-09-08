package com.zhkj.smartpolice.maintain.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.CameraUtil
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.PickerViewUtils
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.widget.dialog.CameraDialog
import com.sunny.zy.widget.dialog.PutInSucceedDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.maintain.adapter.GridViewAdapter
import com.zhkj.smartpolice.maintain.bean.DepartmentStructureBean
import com.zhkj.smartpolice.maintain.bean.MaintainRequestPushBean
import com.zhkj.smartpolice.maintain.bean.SucceedBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import com.zhkj.smartpolice.widget.dialog.ImageDialog
import kotlinx.android.synthetic.main.act_maintain_apply.*
import java.io.File

class MaintainApplyActivity : BaseActivity(), IMaintainView, UserContract.IImageView {
    private var list: ArrayList<String> = ArrayList()
    private var activeState: String? = null
    private var classifyId: String? = null
    private var createTime: String? = null
    private var goodsId: String? = null
    private var goodsName: String? = null
    private var info: String? = null
    private var deptId: String? = null
    private var deptName: String? = null
    private var imageList: ArrayList<ImageBean> = ArrayList()
    private var groupId: String? = null

    companion object {

        fun intent(
            context: Context,
            activeState: String?,
            classifyId: String?,
            createTime: String?,
            goodsId: String?,
            goodsName: String?
        ) {
            val intent = Intent(context, MaintainApplyActivity::class.java)
            intent.putExtra("activeState", activeState)
            intent.putExtra("classifyId", classifyId)
            intent.putExtra("createTime", createTime)
            intent.putExtra("goodsId", goodsId)
            intent.putExtra("goodsName", goodsName)
            context.startActivity(intent)
        }
    }

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val pickerViewUtils: PickerViewUtils by lazy {
        PickerViewUtils(this)
    }

    private val presenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    private val cameraUtil: CameraUtil by lazy {
        CameraUtil(this)
    }

    private val cameraDialog: CameraDialog by lazy {
        CameraDialog(this, cameraUtil)
    }

    private val putInSucceedDialog: PutInSucceedDialog by lazy {
        PutInSucceedDialog(this)
    }


    private val gridviewadapter: GridViewAdapter by lazy {
        GridViewAdapter(this, imageList)
    }

    override fun setLayout(): Int = R.layout.act_maintain_apply

    override fun initView() {
        defaultTitle("提交维修申请")
        activeState = intent.getStringExtra("activeState")
        classifyId = intent.getStringExtra("classifyId")
        createTime = intent.getStringExtra("createTime")
        goodsId = intent.getStringExtra("goodsId")
        goodsName = intent.getStringExtra("goodsName")
        tv_goods_name.text = goodsName
//        cs_section.showTextTv?.text = "选择部门"
//        cs_section.setTextImage(R.drawable.svg_left_arrows)
        tv_section.text = UserManager.getUserBean().deptName
        tv_apply_name.text = UserManager.getUserBean().nickName
        tv_apply_cellphone.text =
            UserManager.getUserBean().mobile
        tv_return.setOnClickListener(this)
        rl_date_select.setOnClickListener(this)
        rl_content.setOnClickListener(this)
        tv_maintain_put.setOnClickListener(this)

        cameraUtil.setAspectXY(
            resources.getDimension(R.dimen.dp_100).toInt(),
            resources.getDimension(R.dimen.dp_100).toInt()
        )
        cameraUtil.onResultListener = object : CameraUtil.OnResultListener {
            override fun onResult(file: File) {
                if (groupId.isNullOrEmpty()) {
                    groupId = System.currentTimeMillis().toString()
                }
                presenter.uploadImage(UrlConstant.UPLOAD_IMAGE_PATH_URL, file.path, groupId.orEmpty())
            }
        }
        gv_uploading.adapter = gridviewadapter

        gridviewadapter.onClicklistAdd = {
            cameraDialog.show()
        }

        gridviewadapter.onClickImageMagnify = { id ->
            var imageDialog: ImageDialog = ImageDialog(this, id)
            imageDialog.show()
            imageDialog.onImageClickList = {
                imageDialog.dismiss()
            }
        }
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.tv_return -> {
                finish()
            }

            R.id.rl_date_select -> {

                pickerViewUtils.showReleaseTime(object : PickerViewUtils.OnDataResultListener {
                    override fun onPickerViewResult(value: String) {
                        tv_date.text = value
                    }

                })
            }

            R.id.rl_content -> {
                val intent = Intent(this, MaintainContentActivity::class.java)
                if (tv_info.text.toString().isNotEmpty()) {
                    intent.putExtra("issue", tv_info.text.toString())
                }
                startActivityForResult(intent, Constant.MAINTAIN_CONTENT_ANSWER)
            }

            R.id.tv_maintain_put -> {
                if (tv_apply_name.text.toString().isNotEmpty()) {
                    if (tv_apply_cellphone.text.toString().isNotEmpty()) {
                        if (tv_section.text != null) {
                            if (tv_date.text.toString() != "请选择") {
                                val maintainRequestPushBean = MaintainRequestPushBean()
                                maintainRequestPushBean.applyState = "1"
                                maintainRequestPushBean.approvalId = "1"
                                maintainRequestPushBean.petitioner = tv_apply_name.text.toString()
                                maintainRequestPushBean.petitionerPhone =
                                    tv_apply_cellphone.text.toString()
                                maintainRequestPushBean.createTime =
                                    tv_date.text.toString() + " 00:00:00"
                                maintainRequestPushBean.applyDate =
                                    tv_date.text.toString() + " 00:00:00"
                                maintainRequestPushBean.deptId = UserManager.getUserBean().deptId
                                maintainRequestPushBean.deptName = deptName
                                maintainRequestPushBean.applyContent = tv_info.text.toString()
                                maintainRequestPushBean.shopGoodsId = goodsId
                                maintainRequestPushBean.attachmentGroupId = groupId
                                LogUtil.i(maintainRequestPushBean.attachmentGroupId.orEmpty())
                                maintainPresenter.onMaintainRequestPush(maintainRequestPushBean)
                            } else {
                                ToastUtil.show("维修时间不能为空")
                            }
                        } else {
                            ToastUtil.show("维修地点不能为空")
                        }
                    } else {
                        ToastUtil.show("维修人手机号不能为空")
                    }
                } else {
                    ToastUtil.show("维修人姓名不能为空")
                }
            }
        }
    }

    override fun loadData() {
//        maintainPresenter.onDepartmentStructure()
    }

    override fun close() {

    }

    /**
     * 部门选择树
     */
    override fun onDepartmentStructure(departmentStructureBean: DepartmentStructureBean) {
        super.onDepartmentStructure(departmentStructureBean)
//        departmentStructureBean.let {
//            it.data?.let { data ->
//                data[0].children?.let { info ->
//                    LogUtil.i("部门数据结构=========$info")
//                    list.clear()
//                    info.forEach { stg ->
//                        if (stg.name?.isNotEmpty() == true) {
//
//                            list.add(stg.name!!)
//                        }
//                    }
//                    cs_section.textList.clear()
//                    cs_section.textList.addAll(list)
//                    cs_section.setOnCustomItemCheckedListener(object : OnCustomItemCheckedListener {
//                        override fun OnCustomItemChecked(position: Int) {
//                            deptId = info[position].id
//                            deptName = info[position].name
//                        }
//                    })
//                }
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.MAINTAIN_CONTENT_ANSWER -> {
                data?.let {
                    info = data.getStringExtra("info")
                    tv_info.text = info
                    LogUtil.i("维修问题回传============$info")
                }
            }
        }
        cameraUtil.onActivityResult(requestCode, resultCode, data)
    }

    override fun onMaintainRequestPush(succeedBean: SucceedBean) {
        super.onMaintainRequestPush(succeedBean)
        succeedBean.let {
            LogUtil.i("提交返回结果==========================$succeedBean")
            val code = it.code?.toInt()
            if (code == 0) {
                putInSucceedDialog.show()
                putInSucceedDialog.onServiceListener = {
                    putInSucceedDialog.dismiss()
                    ToastUtil.show("提交成功")
                    finish()
                }
            }
        }
    }

    override fun uploadImage(bean: ImageBean) {
        LogUtil.i("图片显示实体类========$bean")
        imageList.add(bean)
        gridviewadapter.notifyDataSetChanged()
//        groupId = if (groupId != null) {
//            groupId + "," + bean.id
//        } else {
//            bean.id
//        }
    }
}