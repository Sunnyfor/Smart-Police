package com.zhkj.smartpolice.laundry.presenter

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.laundry.view.LaundryView
import com.zhkj.smartpolice.maintain.bean.SucceedBean
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
        val httpResultBean = object : HttpResultBean<BaseModel<SucceedBean>>() {}
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
}