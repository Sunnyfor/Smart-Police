package com.zhkj.smartpolice.meal.model

import com.google.gson.Gson
import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.meal.bean.MealBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.bean.RestaurantBean
import org.json.JSONArray
import org.json.JSONObject

class MealModel {

    /**
     *  餐厅列表
     */
    suspend fun loadRestaurantList(page: Int): ArrayList<RestaurantBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<RestaurantBean>>() {}

        val params = HashMap<String, String>()
        params["shopType"] = "1"
        params["activeState"] = "1"
        params["certifyState"] = "1"
        params["page"] = page.toString()
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
    suspend fun loadMealMenu(): ArrayList<MealMenuBean>? {
        val httpResultBean = object : HttpResultBean<BaseModel<ArrayList<MealMenuBean>>>() {}

        ZyHttp.get(UrlConstant.MEAL_CLASSIFY_URL, hashMapOf(), httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data
            }
        }
        return null
    }

    /**
     * 餐厅菜品列表
     * classify(必传) 分类 1就餐菜单 2订餐菜单
     * type（就餐必传） 类型id: 1早餐，2午餐，3晚餐
     * labelId (订餐必传) 标签id
     */
    suspend fun loadMealList(page: Int, isDine: Boolean, labelId: String): ArrayList<MealBean>? {

        val params = HashMap<String, String>()
        params["page"] = page.toString()
        params["limit"] = Constant.pageLimit
        params["classify"] = if (isDine) "1" else "2"

        if (isDine) {
            params["type"] = labelId
        } else {
            params["labelId"] = labelId
        }

        val httpResultBean = object : HttpResultBean<PageModel<MealBean>>() {}

        ZyHttp.get(UrlConstant.MEAL_LIST_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }


    /**
     *  订餐记录
     *  消费记录
     */
    suspend fun loadMealRecord(page: Int, isConsumeRecord: Boolean?): ArrayList<MealRecordBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<MealRecordBean>>() {}

        val params = HashMap<String, String>()
        params["page"] = page.toString()
        params["limit"] = Constant.pageLimit

        if (isConsumeRecord == true) {
            params["payState"] = "1"
            params["createUserId"] = "1"
        } else {
            params["shopType"] = "1"
            params["createUserId"] = UserManager.getUserBean().userId ?: ""
        }

        ZyHttp.get(UrlConstant.MEAL_RECORD_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }


    /**
     * 加载订餐记录详情
     */
    suspend fun loadMealRecordDetail(id: String): MealRecordBean? {
        val httpResultBean = object : HttpResultBean<BaseModel<MealRecordBean>>() {}
        ZyHttp.get(String.format(UrlConstant.MEAL_RECORD_DETAIL_URL, id), null, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data
            }
        }
        return null
    }


    /**
     * 下单
     */
    suspend fun commitMealOrder(
        shopId: String, createUserName: String, mobile: String, totalPrice: String, goodsList: ArrayList<MealBean>
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
            it.shopGoodsEntity?.piece = it.count
            jsonArray.put(JSONObject(gson.toJson(it.shopGoodsEntity)))
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


    /**
     * 确认收货
     */
    suspend fun confirmReceive(orderId: String): String? {
        val params = JSONObject()
        params.put("id", orderId)

        val httpResultBean = object : HttpResultBean<BaseModel<String>>() {}
        ZyHttp.postJson(UrlConstant.CONFIRM_RECEIVE_URL, params.toString(), httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.msg
            }
        }
        return null
    }
}