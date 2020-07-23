package com.zhkj.smartpolice.notice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sunny.zy.base.ErrorViewType
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.notice.bean.NoticeBean
import com.zhkj.smartpolice.notice.contract.NoticeContract
import com.zhkj.smartpolice.notice.presenter.NoticePresenter
import java.util.*

class NoticeService : Service(), NoticeContract.IUnReadNoticeView {

    private val presenter: NoticeContract.Presenter by lazy {
        NoticePresenter(this)
    }

    private val timer: Timer by lazy {
        Timer()
    }


    private val timerTask: TimerTask by lazy {
        object : TimerTask() {
            override fun run() {
                presenter.loadUnreadNoticeList(UserManager.getUserBean().userId ?: return)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        //启动定时器
        timer.schedule(timerTask, 0, 5000)
    }

    override fun onDestroy() {
        timerTask.cancel()
        timer.cancel()
        super.onDestroy()
    }


    override fun showUnreadNoticeList(data: ArrayList<NoticeBean>) {
        val hasUnread = data.isNotEmpty()
        val intent = Intent()
        intent.action = "com.zhkj.notice.message"
        intent.putExtra("hasUnread", hasUnread)
        sendBroadcast(intent)
    }


    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showError(errorType: ErrorViewType) {}

    override fun hideError(errorType: ErrorViewType) {}

    override fun showMessage(message: String) {}
}