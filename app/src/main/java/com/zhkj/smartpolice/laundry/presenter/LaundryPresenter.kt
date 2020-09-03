package com.zhkj.smartpolice.laundry.presenter

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.laundry.bean.LaundryLabelBean
import com.zhkj.smartpolice.laundry.view.LaundryView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.json.JSONObject

class LaundryPresenter(view: LaundryView) : BasePresenter<LaundryView>(view) {

    fun onLaundryPutIn(
        beginTime: String,
        reserveType: String,
        clothesCasualNum: String,
        clothesPoliceNum: String,
        endTime: String,
        shopId: String
    ) {
        view?.showLoading()
        val params = JSONObject()
        params.put("beginTime", beginTime)
        params.put("reserveType", reserveType)
        params.put("clothesCasualNum", clothesCasualNum)
        params.put("clothesPoliceNum", clothesPoliceNum)
        params.put("endTime", endTime)
        params.put("shopId", shopId)
        val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
        launch(Main) {
            ZyHttp.postJson(UrlConstant.SAVE_RECORD_URL, params.toString(), httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onLaundryPutIn(httpResultBean.bean ?: return@launch)
                } else {
                    ToastUtil.show(httpResultBean.bean?.msg)
                }
            }
        }
    }


    fun loadLaundryLabel(labelType: String) {
        launch(Main) {
            val params = HashMap<String, String>()
            params["shopType"] = "4"
            params["labelType"] = labelType
            view?.showLoading()
            val httpResultBean = object : HttpResultBean<PageModel<LaundryLabelBean>>() {}
            ZyHttp.post(UrlConstant.SHOP_GOODS_LABEL_URL, params, httpResultBean)
            if (httpResultBean.isSuccess() && httpResultBean.bean?.isSuccess() == true) {
                view?.showLaundryLabel(httpResultBean.bean?.data?.list ?: arrayListOf())
            }
            view?.hideLoading()
        }
    }

    /**
     *
     * isAgent 代理时必传 1代理
     * thePrincipalId 代理时必传 被代理人的用户id
     * clothesPoliceLabel 必传 警服标签，以,隔开
     * clothesCasualLabel 必传 便服数量，以,隔开
     */
    fun saveWashRecord(
        shopId: String,
        isAgent: String? = null,
        thePrincipalId: String? = null,
        clothesPoliceLabel: String? = null,
        clothesCasualLabel: String? = null,
        reserveTime: String
    ) {
        launch(Main) {
            val params = JSONObject()
            params.put("shopId", shopId)

            isAgent?.let {
                params.put("isAgent", it)
            }
            thePrincipalId?.let {
                params.put("thePrincipalId", it)
            }

            clothesPoliceLabel?.let {
                params.put("clothesPoliceLabel", it)
            }

            clothesCasualLabel?.let {
                params.put("clothesCasualLabel", it)
            }

            params.put("reserveTime", reserveTime)

            view?.showLoading()
            val httpResultBean = object : HttpResultBean<BaseModel<Any>>() {}
            ZyHttp.postJson(UrlConstant.SAVE_WASH_RECORD_URL, params.toString(), httpResultBean)
            if (httpResultBean.isSuccess() && httpResultBean.bean?.isSuccess() == true) {
                view?.onLaundryPutIn(httpResultBean.bean ?: return@launch)
            }
            view?.hideLoading()
        }
    }
}