package com.zhkj.smartpolice.maintain.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sunny.zy.base.BaseFragment
import java.util.*

class MyFragmentAdapter(
    fm: FragmentManager,
    private val fragmentList: ArrayList<BaseFragment>,
    private val mTitle: ArrayList<String>
) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitle[position]
    }

}