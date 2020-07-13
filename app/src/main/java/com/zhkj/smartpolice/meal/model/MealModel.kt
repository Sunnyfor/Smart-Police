package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.bean.RestaurantBean

class MealModel {

    /**
     *  餐厅列表
     */
    suspend fun loadRestaurantList(page: String): ArrayList<RestaurantBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<RestaurantBean>>() {}

        val params = HashMap<String, String>()
        params["shopType"] = "1"
        params["activeState"] = "1"
        params["certifyState"] = "1"
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
     * 餐厅菜单分类
     */
    suspend fun loadMealMenu(shopId: String): ArrayList<MealMenuBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<MealMenuBean>>() {}

        val params = HashMap<String, String>()
        params["shopId"] = shopId

        ZyHttp.get(UrlConstant.MEAL_MENU_CLASSIFY_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

    /**
     * 餐厅菜品列表
     */
    suspend fun loadMealGoodsList(page: String, shopId: String): ArrayList<MealGoodsBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<MealGoodsBean>>() {}

        val params = HashMap<String, String>()
        params["page"] = page
        params["limit"] = Constant.pageLimit
        params["shopId"] = shopId

        ZyHttp.get(UrlConstant.MEAL_GOODS_LIST_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }


    /**
     *  订餐记录
     */
    suspend fun loadMealRecord(page: String): ArrayList<MealRecordBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<MealRecordBean>>() {}

        val params = HashMap<String, String>()
        params["page"] = page
        params["limit"] = Constant.pageLimit

        ZyHttp.get(UrlConstant.MEAL_RECORD_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }
}