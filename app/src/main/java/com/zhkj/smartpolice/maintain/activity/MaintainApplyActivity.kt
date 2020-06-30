package com.zhkj.smartpolice.maintain.activity

import android.view.View
import android.widget.Toast
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.PickerViewUtils
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_maintain_apply.*

class MaintainApplyActivity : BaseActivity() {
    var list: ArrayList<String> = ArrayList()

    override fun setLayout(): Int = R.layout.act_maintain_apply

    override fun initView() {
        defaultTitle("提交维修申请")

        cs_section.showTextTv?.setText("选择部门")
        cs_section.setTextImage(R.drawable.svg_left_arrows)
        rl_uploading.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when(view.id){
            R.id.rl_uploading -> {
                var pickerViewUtils = PickerViewUtils(this)
                var buttonSelector: HashMap<String,String> = HashMap()

                buttonSelector["1"] = "拍照"
                buttonSelector["2"] = "查看相册"

                pickerViewUtils.showSingleChoice(buttonSelector,object :PickerViewUtils.OnSingleChoiceResultListener{
                    override fun onPickerViewResult(id: String, value: String) {
                        when(id) {
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