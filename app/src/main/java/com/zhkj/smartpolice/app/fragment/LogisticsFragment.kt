package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.maintain.activity.ApplyMaintainListActivity
import com.zhkj.smartpolice.drugstore.DrugstoreActivity
import com.zhkj.smartpolice.meal.MealActivity
import kotlinx.android.synthetic.main.frag_logistics.*


class LogisticsFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_logistics

    override fun initView() {
        setOnClickListener(
            tv_meal,
            tv_drugstore,
            tv_maintain
        )

    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_meal.id -> startActivity(Intent(requireContext(), MealActivity::class.java))
            tv_drugstore.id -> startActivity(Intent(requireContext(), DrugstoreActivity::class.java))
            tv_maintain.id -> startActivity(Intent(requireContext(), ApplyMaintainListActivity::class.java))
        }
    }

    override fun close() {

    }

}