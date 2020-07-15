package com.zhkj.smartpolice.maintain.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_audit_info.*

class AuditInfoActivity : BaseActivity() {

    companion object {
        fun intent(
            context: Context,
            applyId: String?,
            petitioner: String?,
            petitionerPhone: String?,
            deptName: String?,
            applyDate: String?,
            applyContent: String?
        ) {
            val intent = Intent(context,AuditInfoActivity::class.java)
            intent.putExtra("applyId",applyId)
            intent.putExtra("petitioner",petitioner)
            intent.putExtra("petitionerPhone",petitionerPhone)
            intent.putExtra("deptName",deptName)
            intent.putExtra("applyDate",applyDate)
            intent.putExtra("applyContent",applyContent)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_audit_info

    override fun initView() {
        defaultTitle("审批详情")
        tv_petitioner.text = intent.getStringExtra("petitioner")
        tv_petitioner_phone.text = intent.getStringExtra("petitionerPhone")
        tv_deptName.text = intent.getStringExtra("deptName")
        tv_apply_date.text = intent.getStringExtra("applyDate")
        tv_apply_content.text = intent.getStringExtra("applyContent")
        tv_refuse.setOnClickListener(this)
        tv_confirm.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when(view.id){
            R.id.tv_refuse -> {

            }

            R.id.tv_confirm -> {

            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}