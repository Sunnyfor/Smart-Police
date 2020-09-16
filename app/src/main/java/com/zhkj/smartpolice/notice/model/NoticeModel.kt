package com.zhkj.smartpolice.notice.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.notice.bean.NoticeBean

class NoticeModel {

    suspend fun loadNoticeList(id: String, page: String, limit: String, isRead: String?): ArrayList<NoticeBean>? {
        val params = HashMap<String, String>()
        params["personal"] = id
        params["page"] = page
        params["limit"] = limit
        isRead?.let {
            params["isRead"] = it
        }
        val httpResultBean = object : HttpResultBean<PageModel<NoticeBean>>() {}
        ZyHttp.post(UrlConstant.NOTICE_LIST_URL, params, httpResultBean)
        return httpResultBean.bean?.data?.list
    }


    suspend fun loadUnreadNoticeList(id: String): ArrayList<NoticeBean>? {
        return loadNoticeList(id, "1", "1", "2")
    }


    /**
     * 读取通知
     */
    suspend fun readNotice(id: String): BaseModel<Any>? {
        val params = HashMap<String, String>()
        params["noticeId"] = id
        val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
        ZyHttp.get(UrlConstant.UPDATE_BY_NOTICE_ID_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean
            }
        }
        return null
    }

    /**
     * 通知详情
     */
    suspend fun loadNoticeDetail(id: String): NoticeBean? {
        val httpResultBean = object : HttpResultBean<BaseModel<NoticeBean>>("appNotice") {}
        ZyHttp.post(String.format(UrlConstant.NOTICE_DETAIL_URL, id), hashMapOf(), httpResultBean)
        return httpResultBean.bean?.data
    }
}