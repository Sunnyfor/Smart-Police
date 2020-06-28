package com.zhkj.smartpolice.app

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_main.*

class MainActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_main

    override fun initView() {
        bottom_navigation_view.setupWithNavController(nav_host_fragment.findNavController())
    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {

    }

    override fun close() {

    }
}