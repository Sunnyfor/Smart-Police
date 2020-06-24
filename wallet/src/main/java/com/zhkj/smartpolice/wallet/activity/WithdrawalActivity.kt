package com.zhkj.smartpolice.wallet.activity

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.RouterPath
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.act_withdrawal.*

@Route(path = RouterPath.WITHDRAWAL_ACTIVITY)
class WithdrawalActivity : BaseActivity() {
    var wx = 0
    var ali = 1
    var type = wx
    override fun setLayout(): Int = R.layout.act_withdrawal

    override fun initView() {
        defaultTitle("提现")

        setOnClickListener(
            view_wx_parent,
            rbtn_wx,
            view_ali_parent,
            rbtn_ali
        )

//        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab) {
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {
//
//            }
//
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                type = tab.position
//                if (type == 0) {
//                    tv_account.text = getString(R.string.wx_account)
//                    edt_account.hint = getString(R.string.input_account_wx)
//                } else {
//                    tv_account.text = getString(R.string.ali_account)
//                    edt_account.hint = getString(R.string.input_account_ali)
//                }
//            }
//
//        })


    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            view_wx_parent.id, rbtn_wx.id -> {
                type = wx
                singleSelect()
            }
            view_ali_parent.id, rbtn_ali.id -> {
                type = ali
                singleSelect()
            }
        }
    }

    private fun singleSelect() {
        if (type == wx) {
            rbtn_ali.isChecked = false
            rbtn_wx.isChecked = true
            tv_account.text = getString(R.string.wx_account)
            edt_account.hint = getString(R.string.input_account_wx)
        } else {
            rbtn_wx.isChecked = false
            rbtn_ali.isChecked = true
            tv_account.text = getString(R.string.ali_account)
            edt_account.hint = getString(R.string.input_account_ali)
        }
    }

    override fun close() {

    }
}