package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.RouterPath
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.MealActivity
import kotlinx.android.synthetic.main.frag_mine.*


class MineFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_mine

    override fun initView() {
        setOnClickListener(
            ll_meal,
            rl_wallet,
            btn_withdrawal,
            btn_recharge
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            ll_meal.id -> startActivity(Intent(requireContext(), MealActivity::class.java))
            rl_wallet.id -> ARouter.getInstance().build(RouterPath.WALLET_ACTIVITY).navigation()
            btn_withdrawal.id -> ARouter.getInstance().build(RouterPath.WITHDRAWAL_ACTIVITY).navigation()
            btn_recharge.id -> ARouter.getInstance().build(RouterPath.RECHARGE_ACTIVITY).navigation()
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}