package com.zhkj.smartpolice.maintain.presenter

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.bean.MaintainClassifyBean
import com.zhkj.smartpolice.maintain.bean.MaintainListBean
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


class MaintainPresenter(view: IMaintainView) : BasePresenter<IMaintainView>(view) {


    fun onMaintainClassify(shopType: Int, activeState: Int, certifyState: Int) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["shopType"] = shopType.toString()
        params["activeState"] = activeState.toString()
        params["certifyState"] = certifyState.toString()

        val httpResultBean = object : HttpResultBean<PageModel<MaintainClassifyBean>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.MAINTAIN_CLASSIFY, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onMaintainClassify(httpResultBean.bean ?: return@launch)
                view?.onMaintainClassify(httpResultBean.bean ?: return@launch)
            }
        }
    }
    
    /**
     * 维修部件分类请求
     */

    fun onMaintainList(shopId: Int, labelType: Int) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["shopId"] = shopId.toString()
        params["labelType"] = labelType.toString()

        val httpResultBean = object : HttpResultBean<PageModel<MaintainListBean>>() {}
        launch(Main) {
            ZyHttp.get(UrlConstant.MAINTAIN_liST, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onMaintainList(httpResultBean.bean ?: return@launch)
            }
        }
    }
}