package com.zhkj.smartpolice.notice.presenter

import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.notice.contract.NoticeContract
import com.zhkj.smartpolice.notice.model.NoticeModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class NoticePresenter(view: IBaseView) : NoticeContract.Presenter(view) {

    private val noticeModel: NoticeModel by lazy {
        NoticeModel()
    }


    override fun loadUnreadNoticeList(id: String) {
        launch(Main) {
            if (view is NoticeContract.IUnReadNoticeView)
                (view as NoticeContract.IUnReadNoticeView).showUnreadNoticeList(noticeModel.loadUnreadNoticeList(id) ?: arrayListOf())
        }
    }


    override fun loadNoticeList(id: String, page: String, limit: String, isRead: String?) {
        launch(Main) {
            if (view is NoticeContract.INoticeView)
                (view as NoticeContract.INoticeView).showNoticeList(
                    noticeModel.loadNoticeList(id, page, limit, isRead) ?: arrayListOf()
                )
            hideLoading()
        }
    }

    override fun readNotice(id: String) {
        launch(Main) {
            showLoading()
            noticeModel.readNotice(id)?.let {
                if (view is NoticeContract.INoticeView) {
                    (view as NoticeContract.INoticeView).showReadNoticeResult(id)
                }
            }
            hideLoading()
        }
    }


    override fun loadNoticeDetail(id: String) {
        launch(Main) {
            showLoading()
            noticeModel.loadNoticeDetail(id)?.let {
                if (view is NoticeContract.INoticeDetailView)
                    (view as NoticeContract.INoticeDetailView).showNoticeDetail(it)
            }
            hideLoading()
        }
    }

    override fun loadAnnouncementList() {
        launch(Main) {
            showLoading()
            noticeModel.loadAnnouncementList()?.let {
                if (view is NoticeContract.IAnnouncementView)
                    (view as NoticeContract.IAnnouncementView).loadAnnouncementList(it)
            }
            hideLoading()
        }
    }
}