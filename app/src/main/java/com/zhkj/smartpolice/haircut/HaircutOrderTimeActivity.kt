package com.zhkj.smartpolice.haircut

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_haircut_order_detail.*


class HaircutOrderTimeActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_haircut_order_time

    override fun initView() {

        defaultTitle("预约时间")

        recyclerView.setOnClickListener { startActivity(Intent(this, HaircutOrderTimeActivity::class.java)) }
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {

    }

}