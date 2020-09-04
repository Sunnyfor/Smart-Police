package com.zhkj.smartpolice.maintain.activity

import android.app.Activity
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
import com.zhkj.smartpolice.maintain.adapter.GridViewAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainTaskBean
import com.zhkj.smartpolice.maintain.bean.SucceedBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import kotlinx.android.synthetic.main.act_maintain_task_info.*
import java.io.File


class MaintainTaskInfoActivity : BaseActivity(), IMaintainView, UserContract.IImageView {
    var info: MaintainTaskBean? = null
    override fun setLayout(): Int = R.layout.act_maintain_task_info
    private var imageList: ArrayList<ImageBean> = ArrayList()
    private var groupId: String? = null

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

    override fun initView() {
        defaultTitle("维修确认")
        var intent = intent.extras
        intent?.let {
            info = it.getSerializable("info") as MaintainTaskBean?
            info?.let { info ->
                tv_goods_name.text = info.shopGoodsEntityList?.get(0)?.goodsName
                tv_apply_name.text = info.petitioner
                tv_apply_cellphone.text = info.petitionerPhone
                tv_section.text = info.deptName
                tv_info.text = info.applyContent
                tv_maintainer_name.text = info.repairRecordEntity?.operation
                tv_maintainer_cellphone.text = info.repairRecordEntity?.operationPhone
                info.repairType?.let {
                    when (it) {
                        "1" -> tv_repair_type.text = "维修"
                        "2" -> tv_repair_type.text = "换件"
                    }
                }
            }
        }
        tv_return.setOnClickListener(this)
        rl_date_select.setOnClickListener(this)
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

        LogUtil.i("传过来参数===========$info")

        gv_uploading.adapter = gridviewadapter
        gridviewadapter.onClicklistAdd = {
            cameraDialog.show()
        }

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.tv_refuse -> finish()

            R.id.rl_date_select -> {
                pickerViewUtils.showReleaseTime(object : PickerViewUtils.OnDataResultListener {
                    override fun onPickerViewResult(value: String) {
                        tv_date.text = value
                    }

                })
            }

            R.id.tv_maintain_put -> {
                if (tv_maintainer_name.text.toString().isNotEmpty()) {
                    if (tv_maintainer_cellphone.text.toString().isNotEmpty()) {
                        if (tv_date.text.toString() != "请选择") {
                            info?.let {
                                maintainPresenter.onMaintainFinish(
                                    it.applyContent.orEmpty(),
                                    tv_date.text.toString(), groupId.orEmpty(), it.repairRecordEntity?.operation.orEmpty(),
                                    it.repairRecordEntity?.operationId.orEmpty(), tv_maintainer_cellphone.text.toString(),
                                    it.repairRecordEntity?.professionId.orEmpty(), it.repairRecordEntity?.recordId.orEmpty(),
                                    it.repairRecordEntity?.repairDate.orEmpty(), Constant.MAINTAIN_TASK_FINISH
                                )
                            }
                        } else {
                            ToastUtil.show("维修时间不能为空")
                        }
                    } else {
                        ToastUtil.show("维修员手机号不能为空")
                    }
                } else {
                    ToastUtil.show("维修员姓名不能为空")
                }
            }

            R.id.tv_return -> {
                finish()
            }
        }
    }

    override fun loadData() {
    }

    override fun close() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraUtil.onActivityResult(requestCode, resultCode, data)
    }

    override fun uploadImage(bean: ImageBean) {
        imageList.add(bean)
        gridviewadapter.notifyDataSetChanged()
    }

    override fun onMaintainFinish(succeedBean: SucceedBean) {
        LogUtil.i("完成反馈=========$succeedBean")
        putInSucceedDialog.show()
        putInSucceedDialog.onServiceListener = {
            putInSucceedDialog.dismiss()
            val intent = intent
            setResult(RESULT_OK, intent)
            finish()
        }
    }

}