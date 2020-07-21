package com.zhkj.smartpolice.app

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.fragment.MineFragment
import com.zhkj.smartpolice.notice.NoticeReceiver
import com.zhkj.smartpolice.notice.NoticeService
import kotlinx.android.synthetic.main.act_main.*

class MainActivity : BaseActivity() {

    var fragment: MineFragment? = null

    private val noticeReceiver: NoticeReceiver by lazy {
        NoticeReceiver {
            tv_point.visibility = if (it) View.VISIBLE else View.GONE
            nav_host_fragment.childFragmentManager.fragments[0].let { fragment ->
                if (fragment is MineFragment) {
                    fragment.updatePoint(it)
                }
            }
        }
    }

    override fun setLayout(): Int = R.layout.act_main

    override fun initView() {

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            nav_host_fragment.findNavController().let { controller ->
                var args: Bundle? = null
                if (it.itemId == R.id.mine_dest) {
                    args = Bundle()
                    args.putBoolean("hasUnread", tv_point.visibility == View.VISIBLE)
                }
                controller.navigate(it.itemId, args)
            }
            return@setOnNavigationItemSelectedListener true
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.zhkj.notice.message")
        registerReceiver(noticeReceiver, intentFilter)

        val intent = Intent(this, NoticeService::class.java)
        startService(intent)

        bottom_navigation_view.elevation = 0f
    }

    override fun loadData() {

    }

    override fun onClickEvent(view: View) {

    }

    override fun close() {
        unregisterReceiver(noticeReceiver)
    }
}