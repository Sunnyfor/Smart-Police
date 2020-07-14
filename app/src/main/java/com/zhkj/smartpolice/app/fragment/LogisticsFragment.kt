package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.maintain.activity.ApplyMaintainListActivity
import com.zhkj.smartpolice.maintain.activity.PoliceMaintainActivity
import com.zhkj.smartpolice.merchant.MerchantListActivity
import kotlinx.android.synthetic.main.frag_logistics.*


class LogisticsFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_logistics

    override fun initView() {
        setOnClickListener(
            tv_restaurant,
            tv_haircut,
            tv_drugstore,
            tv_laundry,
            tv_stadium,
            tv_maintain
        )

    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_restaurant.id -> MerchantListActivity.intent(
                requireContext(),
                MerchantListActivity.TYPE_RESTAURANT
            )
            tv_haircut.id -> MerchantListActivity.intent(
                requireContext(),
                MerchantListActivity.TYPE_HAIRCUT
            )
            tv_drugstore.id -> MerchantListActivity.intent(
                requireContext(),
                MerchantListActivity.TYPE_DRUGSTORE
            )
            tv_laundry.id -> MerchantListActivity.intent(
                requireContext(),
                MerchantListActivity.TYPE_LAUNDRY
            )
            tv_stadium.id -> MerchantListActivity.intent(
                requireContext(),
                MerchantListActivity.TYPE_STADIUM
            )
            tv_maintain.id -> {
                val userInfoBean = UserManager.getInfo()
                LogUtil.i("进来人的身份=======${userInfoBean.roleId} ${userInfoBean.roleName}")
                when (userInfoBean.roleId) {
                    3 -> startActivity(Intent(requireContext(), PoliceMaintainActivity::class.java)) //普通警员
                    117 -> startActivity(Intent(requireContext(),
                        ApplyMaintainListActivity::class.java)) //维修管理员
                    else -> ToastUtil.show("你当前不是警员")
//                    166 -> startActivity(Intent(requireContext(),))//维修工人

                }
            }
        }
    }

    override fun close() {

    }

}