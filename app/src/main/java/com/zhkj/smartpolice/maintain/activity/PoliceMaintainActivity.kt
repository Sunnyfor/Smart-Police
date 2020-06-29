package com.zhkj.smartpolice.maintain.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R

class PoliceMaintainActivity : BaseActivity() {

    var navigationList: ArrayList<String> = ArrayList()
    override fun setLayout(): Int = R.layout.act_police_maintain

    override fun initView() {
        defaultTitle("维修部件")

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {

    }
}