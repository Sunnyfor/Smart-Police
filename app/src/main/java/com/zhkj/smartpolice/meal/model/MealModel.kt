package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.PageModel
import com.sunny.zy.http.UrlConstant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.meal.bean.RestaurantBean

class MealModel {

    /**
     *  get请求加载餐厅列表
     */
    suspend fun loadRestaurantList(): ArrayList<RestaurantBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<RestaurantBean>>() {}
        val params = HashMap<String,String>()
        params["shopType"] = "1"
        params["activeState"] = "1"
        params["certifyState"] = "1"

        ZyHttp.get(UrlConstant.RESTAURANT_LIST_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

}