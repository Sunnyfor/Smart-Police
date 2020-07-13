package com.zhkj.smartpolice.merchant

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.haircut.bean.MerchantTime

class MerchantModel {

    /**
     *  商家列表
     */
    suspend fun loadMerchantList(page: String, shopType: String): ArrayList<MerchantBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<MerchantBean>>() {}
        val params = HashMap<String, String>()
        params["shopType"] = shopType// 1餐厅、2药店、3理发店
        params["activeState"] = "1"  //有效状态:1启用、2未启用
        params["certifyState"] = "1" //认证状态:1认证通过、2未通过、3认证中
        params["page"] = page
        params["limit"] = Constant.pageLimit

        ZyHttp.get(UrlConstant.RESTAURANT_LIST_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

    /**
     * 商品详情
     */
    suspend fun loadMerchantInfo(shopId: String): BaseModel<MerchantBean>? {

        val httpResultBean = object : HttpResultBean<BaseModel<MerchantBean>>() {}
        ZyHttp.get(String.format(UrlConstant.RESTAURANT_INFO_URL, shopId), null, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean
            }
        }
        return null
    }

    /**
     * 预约时间（理发店、运动场）
     *LIST_RESOURCE_MANAGE_TIME
     */
    @Suppress("UNCHECKED_CAST")
    suspend fun loadMerchantTime(endDate: String, shopId: String): ArrayList<MerchantTime>? {
        val params = HashMap<String, String>()
        params["shopId"] = shopId
        params["endDate"] = endDate


        val httpResultBean =
            object : HttpResultBean<BaseModel<LinkedHashMap<String, ArrayList<MerchantTime>>>>() {}

        ZyHttp.get(UrlConstant.LIST_RESOURCE_MANAGE_TIME, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.get(endDate)
            }
        }

        return null
    }
}