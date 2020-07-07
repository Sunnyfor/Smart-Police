package com.zhkj.smartpolice.mine.model

import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.mine.bean.ReserveRecordBean

class ReserveModel {

    /**
     *  预约记录
     */
    suspend fun loadReverseRecord(page: String): ArrayList<ReserveRecordBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<ReserveRecordBean>>() {}
        val params = HashMap<String, String>()
        params["page"] = page
        params["limit"] = Constant.pageLimit

        ZyHttp.get(UrlConstant.RESERVE_RECORD_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }
}