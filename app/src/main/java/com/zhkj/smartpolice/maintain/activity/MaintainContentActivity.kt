package com.zhkj.smartpolice.maintain.activity

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
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
//                if (et_info.text.toString().isNotEmpty() == true) {
                val intent: Intent = intent
                intent.putExtra("info", et_info.text.toString())
                setResult(RESULT_OK, intent)
                finish()
//                } else {
//                    ToastUtil.show("维修问题为空！")
//                }
            }
        }
    }

    override fun loadData() {
        val intent = intent
        intent.getStringExtra("issue")?.let {
            val issue = intent.getStringExtra("issue")
            et_info.setText(issue)
            et_info.setSelection(issue?.length ?: 0)
        }
    }

    override fun close() {

    }
}