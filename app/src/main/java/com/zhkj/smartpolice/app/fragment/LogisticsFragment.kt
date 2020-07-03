package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.maintain.activity.ApplyMaintainListActivity
import com.zhkj.smartpolice.drugstore.DrugstoreActivity
import com.zhkj.smartpolice.maintain.activity.PoliceMaintainActivity
import com.zhkj.smartpolice.meal.MealActivity
import kotlinx.android.synthetic.main.frag_logistics.*


class LogisticsFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_logistics

    override fun initView() {
        setOnClickListener(
            tv_restaurant,
            tv_drugstore,
            tv_maintain
        )

    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_restaurant.id -> startActivity(Intent(requireContext(), MealActivity::class.java))
            tv_drugstore.id -> startActivity(
                Intent(
                    requireContext(),
                    DrugstoreActivity::class.java
                )
            )
            tv_maintain.id -> {
                var userInfoBean = UserManager.getInfo()
                LogUtil.i("进来人的身份=======${userInfoBean.roleId} ${userInfoBean.roleName}")
                startActivity(Intent(requireContext(), PoliceMaintainActivity::class.java))
//                when (userInfoBean.roleId) {
//                    3 -> startActivity(Intent(requireContext(), PoliceMaintainActivity::class.java))
//                    115 -> startActivity(
//                        Intent(
//                            requireContext(),
//                            ApplyMaintainListActivity::class.java
//                        )
//                    )
//                }
            }
        }
    }

    override fun close() {

    }

}