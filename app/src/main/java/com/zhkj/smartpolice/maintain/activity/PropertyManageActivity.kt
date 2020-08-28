package com.zhkj.smartpolice.maintain.activity

import android.view.View
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.fragment.AccomplishFragment
import com.zhkj.smartpolice.maintain.fragment.MaintainUntreatedFragment
import com.zhkj.smartpolice.utils.MyPagerAdapter
import kotlinx.android.synthetic.main.act_apply_maintain_list.*


class PropertyManageActivity : BaseActivity() {
    private var fragmentList: ArrayList<BaseFragment> = ArrayList()
    private var mTitle: ArrayList<String> = ArrayList()
    override fun setLayout(): Int = R.layout.act_apply_maintain_list

    override fun initView() {
        defaultTitle("维修列表")
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        fragmentList.add(MaintainUntreatedFragment())
        fragmentList.add(AccomplishFragment())
        mTitle.add("未处理")
        mTitle.add("已处理")
        vp_maintain.adapter = MyPagerAdapter(supportFragmentManager, fragmentList, mTitle)
        tab_layout.setupWithViewPager(vp_maintain)
    }

    override fun close() {

    }

    override fun onResume() {
        super.onResume()
        if (ZyFrameStore.getData<Boolean>("isGoData", true) == true) {
            fragmentList.forEach {
                it.loadData()
            }
        }
    }
}