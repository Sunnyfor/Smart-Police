package com.zhkj.smartpolice.notice.contract

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.notice.bean.NoticeBean

class NoticeContract {

    interface IUnReadNoticeView : IBaseView {
        fun showUnreadNoticeList(data: ArrayList<NoticeBean>)
    }

    interface INoticeView : IBaseView {
        fun showNoticeList(data: ArrayList<NoticeBean>)
        fun showReadNoticeResult(id: String)
    }

    abstract class Presenter(iView: IBaseView) : BasePresenter<IBaseView>(iView) {
        abstract fun loadUnreadNoticeList(id: String)
        abstract fun loadNoticeList(id: String, page: String, limit: String, isRead: String?)
        abstract fun readNotice(id: String)
    }

}
