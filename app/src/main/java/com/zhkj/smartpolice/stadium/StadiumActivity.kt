package com.zhkj.smartpolice.stadium

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.stadium.fragment.StadiumFragment
import com.zhkj.smartpolice.utils.MyPagerAdapter
import kotlinx.android.synthetic.main.act_stadium.*

class StadiumActivity : BaseActivity() {

    private val titleList = arrayListOf("室内运动场", "室外运动场", "澡堂")

    private val fragmentList = arrayListOf(StadiumFragment(), StadiumFragment(), StadiumFragment())

    companion object {
        fun intent(context: Context, shopType: String) {
            val intent = Intent(context, StadiumActivity::class.java)
            intent.putExtra("shopType", shopType)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_stadium

    override fun initView() {
        defaultTitle("运动场")

        viewPager.adapter = MyPagerAdapter(supportFragmentManager, fragmentList, titleList)

        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {

    }
}