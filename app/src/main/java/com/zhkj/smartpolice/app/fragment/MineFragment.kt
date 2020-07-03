package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.RouterManager
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.MealRecordActivity
import kotlinx.android.synthetic.main.frag_mine.*


class MineFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_mine

    override fun initView() {
        setOnClickListener(
            ll_meal,
            tv_money, tv_wallet,
            btn_withdrawal,
            btn_recharge
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            ll_meal.id -> startActivity(Intent(requireContext(), MealRecordActivity::class.java))
            tv_money.id, tv_wallet.id -> RouterManager.navigation(requireContext(), RouterManager.WALLET_ACTIVITY)
            btn_withdrawal.id -> RouterManager.navigation(requireContext(), RouterManager.WITHDRAWAL_ACTIVITY)
            btn_recharge.id -> RouterManager.navigation(requireContext(), RouterManager.RECHARGE_ACTIVITY)
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}