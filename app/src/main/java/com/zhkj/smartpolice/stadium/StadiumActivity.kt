package com.zhkj.smartpolice.stadium

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.stadium.fragment.StadiumFragment
import kotlinx.android.synthetic.main.act_stadium.*

/**
 * Desc
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020/8/31 22:45
 */
class StadiumActivity : BaseActivity() {

    private val titleList = arrayListOf(
        "室内运动场", "室外运动场", "澡堂"
    )

    private val fragmentList = arrayListOf(
        StadiumFragment(),
        StadiumFragment(),
        StadiumFragment()
    )

    override fun setLayout(): Int = R.layout.act_stadium

    override fun initView() {
        defaultTitle("运动场")

        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment = fragmentList[position]

            override fun getCount(): Int = fragmentList.size

            override fun getPageTitle(position: Int): CharSequence? {
                return titleList[position]
            }
        }

        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {

    }
}