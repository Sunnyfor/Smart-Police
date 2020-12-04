package com.zhkj.smartpolice.maintain.activity

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.CameraUtil
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.adapter.MaintainPhotographAdapter
import com.zhkj.smartpolice.maintain.bean.FindImagePathBean
import com.zhkj.smartpolice.maintain.bean.MaintainTaskBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import com.zhkj.smartpolice.mine.bean.ImageBean
import com.zhkj.smartpolice.mine.model.UserPresenter
import com.zhkj.smartpolice.widget.dialog.ImageDialog
import kotlinx.android.synthetic.main.act_maintain_task_info.*
import java.io.File


class MaintainTaskInfoActivity : BaseActivity(), IMaintainView {
    var info: MaintainTaskBean? = null
    override fun setLayout(): Int = R.layout.act_maintain_task_info
    private var groupId: String? = null
    private var deptName: String = ""
    private var applyContent: String = ""
    private var operation: String = ""
    private var operationPhone: String = ""
    private var operationId: String = ""
    private var professionId: String = ""
    private var recordId: String = ""
    private var repairDate: String = ""
    var findImagePathBean: ArrayList<FindImagePathBean> = ArrayList()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainPhotographAdapter by lazy {
        MaintainPhotographAdapter(findImagePathBean).apply {
            setOnItemClickListener { _, position ->
                var imageDialog: ImageDialog = ImageDialog(this@MaintainTaskInfoActivity, getData(position).id)
                imageDialog.show()
                imageDialog.onImageClickList = {
                    imageDialog.dismiss()
                }
            }
        }
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
                deptName = info.deptName.toString()
                applyContent = info.applyContent.toString()
                operation = info.repairRecordEntity?.operation.toString()
                operationPhone = info.repairRecordEntity?.operationPhone.toString()
                operationId = info.repairRecordEntity?.operationId.toString()
                professionId = info.repairRecordEntity?.professionId.toString()
                recordId = info.repairRecordEntity?.recordId.toString()
                repairDate = info.repairRecordEntity?.repairDate.toString()
                groupId = info.attachmentGroupId
                info.repairType?.let {
                    when (it) {
                        "1" -> tv_repair_type.text = "维修"
                        "2" -> tv_repair_type.text = "换件"
                    }
                }
            }
        }
        tv_return.setOnClickListener(this)
        tv_maintain_put.setOnClickListener(this)


        LogUtil.i("传过来参数===========$info")

        rv_maintain_img.layoutManager = GridLayoutManager(this, 4)
        rv_maintain_img.adapter = adapter

    }

    override fun onClickEvent(view: View) {
        when (view.id) {

            R.id.tv_maintain_put -> {
                MaintainFulfillActivity.intent(
                    this@MaintainTaskInfoActivity, deptName, applyContent, operation, operationPhone, operationId,
                    professionId, recordId, repairDate
                )
                finish()
            }

            R.id.tv_return -> {
                finish()
            }
        }
    }

    override fun loadData() {
        groupId?.let {
            maintainPresenter.onFindImagePath(it)
        }
    }

    override fun close() {
    }

    override fun onFindImagePath(info: ArrayList<FindImagePathBean>) {
        findImagePathBean.clear()
        findImagePathBean.addAll(info)
        adapter.notifyDataSetChanged()
    }
}