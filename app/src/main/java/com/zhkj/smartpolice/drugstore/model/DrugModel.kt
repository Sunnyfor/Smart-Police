package com.zhkj.smartpolice.drugstore.model

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.Constant
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.DateUtil
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.drugstore.bean.DrugBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import org.json.JSONObject

class DrugModel {

    /**
     * 药品分类
     */
    suspend fun loadDrugClassify(shopId: String): ArrayList<MealMenuBean>? {
        val httpResultBean = object : HttpResultBean<PageModel<MealMenuBean>>() {}

        val params = HashMap<String, String>()
        params["shopId"] = shopId

        ZyHttp.get(UrlConstant.DRUG_CLASSIFY_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

    /**
     * 药品列表
     */
    suspend fun loadDrugList(page: Int, shopId: String, labelId: String): ArrayList<DrugBean>? {

        val params = HashMap<String, String>()
        params["publishState"] = "1"
        params["page"] = page.toString()
        params["limit"] = Constant.pageLimit
        params["shopId"] = shopId
        params["labelId"] = labelId

        val httpResultBean = object : HttpResultBean<PageModel<DrugBean>>() {}

        ZyHttp.get(UrlConstant.DRUG_LIST_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }


    /**
     * 药品列表搜索
     */
    suspend fun searchDrugList(shopId: String, searchData: String): ArrayList<DrugBean>? {

        val params = HashMap<String, String>()
        params["publishState"] = "1"
        params["shopId"] = shopId
        params["goodsName"] = searchData
        params["limit"] = "-1"

        val httpResultBean = object : HttpResultBean<PageModel<DrugBean>>() {}

        ZyHttp.get(UrlConstant.DRUG_LIST_URL, params, httpResultBean)
        if (httpResultBean.isSuccess()) {
            if (httpResultBean.bean?.isSuccess() == true) {
                return httpResultBean.bean?.data?.list
            }
        }
        return null
    }

    /**
     * 意见反馈
     */
    suspend fun commitFeedback(shopId: String, content: String, phone: String): String? {

        val params = JSONObject()
        params.put("content", content)
        params.put("mobile", phone)
        params.put("professionId", shopId)
        params.put("createTime", DateUtil.stampToDate(System.currentTimeMillis(), true))
        params.put("createUserId", UserManager.getUserBean().userId)
        params.put("createUserName", UserManager.getUserBean().userName)
        params.put("feedbackId", "")

        val httpResultBean = object : HttpResultBean<BaseModel<String>>() {}

        ZyHttp.postJson(UrlConstant.DRUG_FEEDBACK_URL, params.toString(), httpResultBean)

        return httpResultBean.bean?.msg ?: ""
    }

}