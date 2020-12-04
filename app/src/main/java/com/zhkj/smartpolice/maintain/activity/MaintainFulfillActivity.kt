package com.zhkj.smartpolice.maintain.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.act_maintain_fulfill.*
import java.io.File

/**
 * Desc
 * Author NingYang
 * Mail yang122612@yeah.net
 * Date 2020/12/4 14:21
 */
class MaintainFulfillActivity : BaseActivity(), IMaintainView, UserContract.IImageView {
    private var imageList: ArrayList<ImageBean> = ArrayList()
    private var groupId: String? = null

    companion object {
        fun intent(
            context: Context,
            deptName: String,
            applyContent: String,
            operation: String,
            operationPhone: String,
            operationId: String,
            professionId: String,
            recordId: String,
            repairDate: String
        ) {
            val intent = Intent(context, MaintainFulfillActivity::class.java)
            intent.putExtra("deptName",deptName)
            intent.putExtra("applyContent",applyContent)
            intent.putExtra("operation",operation)
            intent.putExtra("operationPhone",operationPhone)
            intent.putExtra("operationId",operationId)
            intent.putExtra("professionId",professionId)
            intent.putExtra("recordId",recordId)
            intent.putExtra("repairDate",repairDate)
            context.startActivity(intent)
        }
    }

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val gridviewadapter: GridViewAdapter by lazy {
        GridViewAdapter(this, imageList)
    }

    private val cameraUtil: CameraUtil by lazy {
        CameraUtil(this)
    }

    private val cameraDialog: CameraDialog by lazy {
        CameraDialog(this, cameraUtil)
    }

    private val presenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    private val pickerViewUtils: PickerViewUtils by lazy {
        PickerViewUtils(this)
    }

    private val putInSucceedDialog: PutInSucceedDialog by lazy {
        PutInSucceedDialog(this)
    }

    override fun setLayout(): Int = R.layout.act_maintain_fulfill

    override fun initView() {
        defaultTitle("维修确认")
        var intent = intent.extras
        intent?.let {
            LogUtil.i("info======${it}")
            tv_section.text = it.getString("deptName")
            tv_info.text = it.getString("applyContent")
            tv_maintainer_name.text = it.getString("operation")
            tv_maintainer_cellphone.text = it.getString("operationPhone")
        }

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

        setOnClickListener(tv_maintain_put, rl_date_select, tv_return)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
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
                            var intent = intent.extras
                            intent?.let {

                                maintainPresenter.onMaintainFinish(
                                    it.getString("applyContent").orEmpty(),
                                    tv_date.text.toString(), groupId.orEmpty(), it.getString("operation").orEmpty(),
                                    it.getString("operationId").orEmpty(), tv_maintainer_cellphone.text.toString(),
                                    it.getString("professionId").orEmpty(), it.getString("recordId").orEmpty(),
                                    it.getString("repairDate").orEmpty(), Constant.MAINTAIN_TASK_FINISH
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

            R.id.tv_return -> finish()

        }
    }

    override fun loadData() {
    }

    override fun close() {
    }

    override fun onMaintainFinish(succeedBean: SucceedBean) {
        super.onMaintainFinish(succeedBean)
        LogUtil.i("完成反馈=========$succeedBean")
        putInSucceedDialog.show()
        putInSucceedDialog.onServiceListener = {
            putInSucceedDialog.dismiss()
            val intent = intent
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cameraUtil.onActivityResult(requestCode, resultCode, data)
    }


    override fun uploadImage(bean: ImageBean) {
        imageList.add(bean)
        gridviewadapter.notifyDataSetChanged()
    }
}