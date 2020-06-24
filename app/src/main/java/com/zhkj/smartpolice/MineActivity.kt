package com.zhkj.smartpolice

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.meal.MealActivity
import kotlinx.android.synthetic.main.act_mine.*

class MineActivity : BaseActivity() {

    override fun setLayout(): Int = R.layout.act_mine

    override fun initView() {

        ll_meal.setOnClickListener(this)

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.ll_meal -> startActivity(Intent(this, MealActivity::class.java))
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}