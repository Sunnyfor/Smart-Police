package com.zhkj.smartpolice.maintain.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_maintain_content.*

class MaintainContentActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_maintain_content

    override fun initView() {
        defaultTitle("维修问题")
        tv_return.setOnClickListener(this)
        tv_accomplish.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.tv_return -> finish()
            R.id.tv_accomplish -> {
                if (et_info.text.toString()?.isNotEmpty() == true) {
                    var intent: Intent = intent
                    intent.putExtra("info", et_info.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                } else {
                    ToastUtil.show("维修问题为空！")
                }
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}