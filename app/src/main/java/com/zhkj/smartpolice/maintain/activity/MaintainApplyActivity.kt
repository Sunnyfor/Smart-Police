package com.zhkj.smartpolice.maintain.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.PickerViewUtils
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.maintain.bean.DepartmentStructureBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.act_maintain_apply.*

class MaintainApplyActivity : BaseActivity(), IMaintainView {
    private var list: ArrayList<String> = ArrayList()
    private var activeState: String? = null
    private var classifyId: String? = null
    private var createTime: String? = null
    private var goodsName: String? = null
    private var info: String? = null

    companion object {

        fun intent(
            context: Context,
            activeState: String?,
            classifyId: String?,
            createTime: String?,
            goodsName: String?
        ) {
            val intent = Intent(context, MaintainApplyActivity::class.java)
            intent.putExtra("activeState", activeState)
            intent.putExtra("classifyId", classifyId)
            intent.putExtra("createTime", createTime)
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

    override fun setLayout(): Int = R.layout.act_maintain_apply

    override fun initView() {
        defaultTitle("提交维修申请")
        activeState = intent.getStringExtra("activeState")
        classifyId = intent.getStringExtra("classifyId")
        createTime = intent.getStringExtra("createTime")
        goodsName = intent.getStringExtra("goodsName")
        tv_goods_name.text = goodsName
        cs_section.showTextTv?.text = "选择部门"
        cs_section.setTextImage(R.drawable.svg_left_arrows)
        rl_uploading.setOnClickListener(this)
        tv_return.setOnClickListener(this)
        rl_date_select.setOnClickListener(this)
        rl_content.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.rl_uploading -> {
//                val pickerViewUtils = PickerViewUtils(this)
                val buttonSelector: HashMap<String, String> = HashMap()

                buttonSelector["1"] = "拍照"
                buttonSelector["2"] = "查看相册"

                pickerViewUtils.showSingleChoice(buttonSelector,
                    object : PickerViewUtils.OnSingleChoiceResultListener {
                        override fun onPickerViewResult(id: String, value: String) {
                            when (id) {
                                "1" -> {
                                    ToastUtil.show("我点击了拍照")
                                }
                                "2" -> {
                                    ToastUtil.show("我点击了查看相册")
                                }
                            }
                        }

                    })
            }

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
                var intent = Intent(this, MaintainContentActivity::class.java)
                startActivityForResult(intent,Constant.MAINTAIN_CONTENT_ANSWER)
            }
        }
    }

    override fun loadData() {
        maintainPresenter.onDepartmentStructure()
    }

    override fun close() {

    }

    /**
     * 部门选择树
     */
    override fun onDepartmentStructure(departmentStructureBean: DepartmentStructureBean) {
        super.onDepartmentStructure(departmentStructureBean)
        departmentStructureBean?.let {
            it.data?.let { data ->
                data.forEach { info ->
                    if (info.name?.isNotEmpty() == true) {
                        LogUtil.i("部门选择树==============${info.name}")
                        list.add(info.name!!)
                    }
                }
                cs_section.textList.addAll(list)
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            Constant.MAINTAIN_CONTENT_ANSWER -> {
                data?.let {
                    info = data.getStringExtra("info")
                    tv_info.text = info
                    LogUtil.i("维修问题回传============$info")
                }
            }
        }
    }
}