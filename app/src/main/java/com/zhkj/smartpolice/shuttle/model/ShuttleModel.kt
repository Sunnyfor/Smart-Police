package com.zhkj.smartpolice.shuttle.model

import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.merchant.MerchantBean

class ShuttleModel {

    /**
     * 班车
     */
    suspend fun loadShuttleBusList(page: String, shopType: String): ArrayList<MerchantBean>? {

        val params = HashMap<String, String>()
        params["page"] = page
        params["limit"] = Constant.pageLimit

        val httpResultBean = object : HttpResultBean<PageModel<MerchantBean>>() {}

        ZyHttp.post(String.format(UrlConstant.SHUTTLE_BUS_LIST_URL, shopType), params, httpResultBean)

        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

}