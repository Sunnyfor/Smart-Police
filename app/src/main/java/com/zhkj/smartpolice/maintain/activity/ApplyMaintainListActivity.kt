package com.zhkj.smartpolice.maintain.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.maintain.adapter.MyFragmentAdapter
import com.zhkj.smartpolice.maintain.fragment.ProcessedFragment
import com.zhkj.smartpolice.maintain.fragment.UntreatedFragment
import kotlinx.android.synthetic.main.act_apply_maintain_list.*

class ApplyMaintainListActivity : BaseActivity() {
    var fragmentList: ArrayList<BaseFragment> = ArrayList()
    var mTitle: ArrayList<String> = ArrayList()
    override fun setLayout(): Int = R.layout.act_apply_maintain_list

    override fun initView() {
        defaultTitle("申请列表")
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        fragmentList.add(UntreatedFragment())
        fragmentList.add(ProcessedFragment())
        mTitle.add("未处理")
        mTitle.add("已处理")
        vp_maintain.adapter = MyFragmentAdapter(supportFragmentManager, fragmentList, mTitle)
        tab_layout.setupWithViewPager(vp_maintain)
    }

    override fun close() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            Constant.MAINTAIN_CONTENT_ANSWER -> {
                if (resultCode == Activity.RESULT_OK) {
                    fragmentList.forEach {
                        it.loadData()
                    }
                }
            }
        }
    }
}