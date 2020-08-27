package com.zhkj.smartpolice.meal

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.fragment.DineFragment
import com.zhkj.smartpolice.meal.fragment.MealFragment
import com.zhkj.smartpolice.utils.MyPagerAdapter
import kotlinx.android.synthetic.main.act_dining_room.*


class DiningRoomActivity : BaseActivity() {

    private val fragmentList = arrayListOf<BaseFragment>(DineFragment(), MealFragment())
    private val titleList = arrayListOf("就餐", "订餐")

    companion object {
        fun intent(context: Context, shopId: String?) {
            val intent = Intent(context, DiningRoomActivity::class.java)
            intent.putExtra("shopId", shopId)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_dining_room

    override fun initView() {
        defaultTitle("餐厅")

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