package com.zhkj.smartpolice.maintain.activity

import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.fragment.AccomplishFragment
import com.zhkj.smartpolice.maintain.fragment.InProcessedFragment
import com.zhkj.smartpolice.utils.MyPagerAdapter
import kotlinx.android.synthetic.main.act_apply_maintain_list.*


class MaintainTaskActivity : BaseActivity() {
    var fragmentList: ArrayList<BaseFragment> = ArrayList()
    var mTitle: ArrayList<String> = ArrayList()

    override fun setLayout(): Int = R.layout.act_apply_maintain_list

    override fun initView() {
        defaultTitle("任务列表")
    }

    override fun onClickEvent(view: View) {
    }

    override fun loadData() {
        fragmentList.add(InProcessedFragment())
        fragmentList.add(AccomplishFragment())
        mTitle.add("未维修")
        mTitle.add("已维修")
        vp_maintain.adapter = MyPagerAdapter(supportFragmentManager, fragmentList, mTitle)
        tab_layout.setupWithViewPager(vp_maintain)

    }

    override fun close() {
    }
}