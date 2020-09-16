package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.graphics.Paint
import android.view.View
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.ToastUtil
import com.sunny.zy.utils.isStrEmpty
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.meal.activity.MealRecordActivity
import com.zhkj.smartpolice.mine.activity.PersonalInfoActivity
import com.zhkj.smartpolice.mine.activity.RepairRecordActivity
import com.zhkj.smartpolice.mine.activity.ReserveRecordActivity
import com.zhkj.smartpolice.mine.activity.SettingActivity
import com.zhkj.smartpolice.notice.ConsumeRecordActivity
import kotlinx.android.synthetic.main.frag_mine.*


class MineFragment : BaseFragment() {

    override fun setLayout(): Int = R.layout.frag_mine

    override fun initView() {

        getBaseActivity().simpleTitle("个人中心")

        arguments?.let {
            updatePoint(it.getBoolean("hasUnread"))
        }

        tv_money.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线

        setOnClickListener(
            iv_head,
            iv_edit,
            ll_meal, ll_repair, ll_reserve, ll_consume,
            tv_money, tv_wallet,
            btn_withdrawal, btn_recharge,
            item_help, item_about, item_setting
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            iv_head.id, iv_edit.id -> startActivity(Intent(requireContext(), PersonalInfoActivity::class.java))
            ll_meal.id -> startActivity(Intent(requireContext(), MealRecordActivity::class.java))
            ll_repair.id -> startActivity(Intent(requireContext(), RepairRecordActivity::class.java))
            ll_reserve.id -> startActivity(Intent(requireContext(), ReserveRecordActivity::class.java))
            ll_consume.id -> startActivity(Intent(requireContext(), ConsumeRecordActivity::class.java))
            tv_money.id, tv_wallet.id -> RouterManager.navigation(requireContext(), RouterManager.WALLET_ACTIVITY)
            btn_withdrawal.id -> RouterManager.navigation(requireContext(), RouterManager.WITHDRAWAL_ACTIVITY)
            btn_recharge.id -> RouterManager.navigation(requireContext(), RouterManager.RECHARGE_ACTIVITY)
            item_help.id, item_about.id -> ToastUtil.show()
            item_setting.id -> startActivity(Intent(requireContext(), SettingActivity::class.java))
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }

    override fun onResume() {
        super.onResume()

        val userBean = UserManager.getUserBean()
        tv_name.text = isStrEmpty("${userBean.nickName}", "登录 / 注册")
        tv_sign.text = isStrEmpty(userBean.sign)

        Glide.with(requireContext())
            .load("${UrlConstant.LOAD_IMAGE_PATH_URL}${userBean.avatar}")
            .placeholder(R.drawable.svg_default_head)
            .into(iv_head)
    }

    fun updatePoint(hasUnread: Boolean) {
        tv_point.visibility = if (hasUnread) View.VISIBLE else View.GONE
    }
}