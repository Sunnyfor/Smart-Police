package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
import com.bumptech.glide.Glide
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.meal.MealRecordActivity
import com.zhkj.smartpolice.mine.activity.RepairRecordActivity
import com.zhkj.smartpolice.mine.activity.ReserveRecordActivity
import com.zhkj.smartpolice.mine.activity.SettingActivity
import com.zhkj.smartpolice.mine.bean.UserBean
import com.zhkj.smartpolice.mine.model.UserContract
import com.zhkj.smartpolice.mine.model.UserPresenter
import com.sunny.zy.utils.isStrEmpty
import com.zhkj.smartpolice.mine.activity.PersonalInfoActivity
import kotlinx.android.synthetic.main.frag_mine.*
import kotlinx.coroutines.cancel


class MineFragment : BaseFragment(), UserContract.IUserInfoView {

    private val presenter: UserPresenter by lazy {
        UserPresenter(this)
    }

    override fun setLayout(): Int = R.layout.frag_mine

    override fun initView() {
        setOnClickListener(
            iv_head,
            ll_meal, ll_repair, ll_reserve, ll_medicine,
            tv_money, tv_wallet,
            btn_withdrawal, btn_recharge,
            item_help, item_about, item_setting
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            iv_head.id -> startActivity(Intent(requireContext(), PersonalInfoActivity::class.java))
            ll_meal.id -> startActivity(Intent(requireContext(), MealRecordActivity::class.java))
            ll_repair.id -> startActivity(Intent(requireContext(), RepairRecordActivity::class.java))
            ll_reserve.id -> startActivity(Intent(requireContext(), ReserveRecordActivity::class.java))
            ll_medicine.id -> ToastUtil.show("暂无购物记录")
            tv_money.id, tv_wallet.id -> RouterManager.navigation(requireContext(), RouterManager.WALLET_ACTIVITY)
            btn_withdrawal.id -> RouterManager.navigation(requireContext(), RouterManager.WITHDRAWAL_ACTIVITY)
            btn_recharge.id -> RouterManager.navigation(requireContext(), RouterManager.RECHARGE_ACTIVITY)
            item_help.id, item_about.id -> ToastUtil.show()
            item_setting.id -> startActivity(Intent(requireContext(), SettingActivity::class.java))
        }
    }

    override fun loadData() {
        presenter.loadUserInfo()
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showUserInfo(data: UserBean) {

        UserManager.setUserBean(data)

        Glide.with(requireContext())
            .load("${UrlConstant.IMAGE_PATH_URL}${data.avatar}")
            .placeholder(R.drawable.svg_default_head)
            .into(iv_head)

        tv_name.text = isStrEmpty("${data.nickName} ( ${data.mobile} )", "登录 / 注册")
        tv_sign.text = isStrEmpty(data.sign)
    }
}