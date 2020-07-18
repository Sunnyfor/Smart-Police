package com.zhkj.smartpolice.meal.model

import com.google.gson.Gson
import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.bean.RestaurantBean
import org.json.JSONArray
import org.json.JSONObject

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
    suspend fun loadMealGoodsList(page: String, shopId: String, labelId: String): ArrayList<MealGoodsBean>? {

        val params = HashMap<String, String>()
        params["publishState"] = "1"
        params["page"] = page
        params["limit"] = Constant.pageLimit
        params["shopId"] = shopId
        params["labelId"] = labelId

        val httpResultBean = object : HttpResultBean<PageModel<MealGoodsBean>>() {}

        ZyHttp.get(UrlConstant.MEAL_GOODS_LIST_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

    /**
     * 餐厅菜品列表搜索
     */
    suspend fun searchMealGoodsList(shopId: String, searchData: String): ArrayList<MealGoodsBean>? {

        val params = HashMap<String, String>()
        params["shopId"] = shopId
        params["goodsName"] = searchData
        params["limit"] = "-1"

        val httpResultBean = object : HttpResultBean<PageModel<MealGoodsBean>>() {}

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


    /**
     * 下单
     */
    suspend fun commitMealOrder(
        shopId: String, createUserName: String, mobile: String, totalPrice: String, goodsList: ArrayList<MealGoodsBean>
    ): BaseModel<MealRecordBean>? {

        val params = JSONObject()
        params.put("shopId", shopId)
        params.put("createUserName", createUserName)
        params.put("mobile", mobile)
        params.put("isPayBehalf", 0)
        params.put("payPrice", totalPrice)
        params.put("totalPrice", totalPrice)
        val gson = Gson()
        val jsonArray = JSONArray()
        goodsList.forEach {
            for (i in 0 until it.count) {
                jsonArray.put(JSONObject(gson.toJson(it)))
            }
        }
        params.put("ordersLinkEntityList", jsonArray)

        val httpResultBean = object : HttpResultBean<BaseModel<MealRecordBean>>() {}
        ZyHttp.postJson(UrlConstant.PLACE_AN_ORDER, params.toString(), httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean
            }
        }

        return null
    }

}