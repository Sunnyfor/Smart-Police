package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.MealActivity
import kotlinx.android.synthetic.main.frag_logistics.*


class LogisticsFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_logistics

    override fun initView() {
        setOnClickListener(
            rl_meal
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            rl_meal.id -> startActivity(Intent(requireContext(), MealActivity::class.java))
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }

}