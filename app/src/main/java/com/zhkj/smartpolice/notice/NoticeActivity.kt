package com.zhkj.smartpolice.notice

import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.notice.adapter.NoticeAdapter
import com.zhkj.smartpolice.notice.bean.NoticeBean
import com.zhkj.smartpolice.notice.contract.NoticeContract
import com.zhkj.smartpolice.notice.presenter.NoticePresenter

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/21 16:58
 */
class NoticeActivity : BaseActivity(), NoticeContract.INoticeView {

    val pullRefreshFragment = PullRefreshFragment<NoticeBean>()

    val presenter: NoticePresenter by lazy {
        NoticePresenter(this)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        defaultTitle("消息通知")

        pullRefreshFragment.adapter = NoticeAdapter().apply {
            setOnItemClickListener { _, position ->
                if (getData(position).isRead == "2") {
                    presenter.readNotice(getData(position).noticeId ?: return@setOnItemClickListener)
                }
            }
        }
        pullRefreshFragment.loadData = {
            loadData()
        }
        supportFragmentManager.beginTransaction().add(getFrameBody().id, pullRefreshFragment).commit()
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        presenter.loadNoticeList(UserManager.getUserBean().userId ?: "", pullRefreshFragment.page.toString(), "20", null)
    }

    override fun close() {

    }

    override fun showNoticeList(data: ArrayList<NoticeBean>) {
        pullRefreshFragment.addData(data)
    }

    override fun showReadNoticeResult(id: String) {
        pullRefreshFragment.getAllData()?.find { it.noticeId == id }?.isRead = "1"
        pullRefreshFragment.adapter?.notifyDataSetChanged()
    }
}