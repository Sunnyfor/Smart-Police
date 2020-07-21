package com.zhkj.smartpolice.notice.contract

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.notice.bean.NoticeBean

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/7/21 11:00
 */
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
