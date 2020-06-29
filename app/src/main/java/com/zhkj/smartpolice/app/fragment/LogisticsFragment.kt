package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.activity.MaintainApplyActivity
import com.zhkj.smartpolice.maintain.activity.PoliceMaintainActivity
import kotlinx.android.synthetic.main.frag_logistics.*


class LogisticsFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_logistics

    override fun initView() {
        rlMaintain.setOnClickListener(this)
    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.rlMaintain -> {
                startActivity(Intent(activity, MaintainApplyActivity::class.java))
            }
        }
    }

    override fun close() {

    }

}