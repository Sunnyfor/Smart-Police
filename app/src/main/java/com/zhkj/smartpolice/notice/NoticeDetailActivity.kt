package com.zhkj.smartpolice.notice

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.notice.bean.NoticeBean
import com.zhkj.smartpolice.notice.contract.NoticeContract
import com.zhkj.smartpolice.notice.presenter.NoticePresenter
import kotlinx.android.synthetic.main.act_notice_detail.*

class NoticeDetailActivity : BaseActivity(), NoticeContract.INoticeDetailView {

    private val noticeId: String by lazy {
        intent.getStringExtra("noticeId") ?: ""
    }

    private val presenter: NoticePresenter by lazy {
        NoticePresenter(this)
    }

    companion object {
        fun intent(context: Context, noticeId: String?) {
            val intent = Intent(context, NoticeDetailActivity::class.java)
            intent.putExtra("noticeId", noticeId)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_notice_detail

    override fun initView() {

        defaultTitle("通知详情")

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        presenter.loadNoticeDetail(noticeId)
    }

    override fun close() {

    }


    override fun showNoticeDetail(data: NoticeBean) {
        tv_notice_title.text = data.noticeTitle
        tv_time.text = data.createDate
        tv_content.text = data.noticeValue
    }
}