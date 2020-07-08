package com.zhkj.smartpolice.maintain.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.PickerViewUtils
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_maintain_apply.*

class MaintainApplyActivity : BaseActivity() {
    private var list: ArrayList<String> = ArrayList()
    private var activeState: String? = null
    private var classifyId: String? = null
    private var createTime: String? = null
    private var goodsName: String? = null

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
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.rl_uploading -> {
                val pickerViewUtils = PickerViewUtils(this)
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
        }
    }

    override fun loadData() {
        list.add("销售部")
        list.add("研发部")
        list.add("采集部")
        list.add("人事部")
        list.add("刑侦部")
        cs_section.textList.addAll(list)

    }

    override fun close() {

    }
}