package com.zhkj.smartpolice.app

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.RouterManager
import com.sunny.zy.utils.ToastUtil
import com.umeng.message.PushAgent
import com.umeng.message.tag.TagManager
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.fragment.MineFragment
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.login.activity.LoginActivity
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

        ZyFrameStore.onSuccessCallback = {
            if (it.url.contains("login.html")) {
                LoginActivity.intent(this, true)
                ToastUtil.show("登录失效！，请重新登录！")
                false
            } else {
                it.exception == null
            }

        }


        enable()

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

//        val intent = Intent(this, NoticeService::class.java)
//        startService(intent)

        bottom_navigation_view.elevation = 0f

        setOnClickListener(rl_one_code)

    }

    override fun onClickEvent(view: View) {
        when(view.id){
            rl_one_code.id-> RouterManager.navigation(this, RouterManager.PAY_CODE_ACTIVITY)
        }
    }

    override fun close() {
        unregisterReceiver(noticeReceiver)
    }


    private fun enable() {
        PushAgent.getInstance(this).addAlias(UserManager.getUserBean().userId, "ytzhjb") { isSuccess, message ->
            LogUtil.i("友盟推送绑定别名: isSuccess = $isSuccess ||| message = $message")
        }
        PushAgent.getInstance(this).tagManager.addTags(TagManager.TCallBack { isSuccess, message ->
            LogUtil.i("友盟推送绑定Tag: isSuccess = $isSuccess ||| message = $message")
        }, UserManager.getUserBean().deptId)
    }
}