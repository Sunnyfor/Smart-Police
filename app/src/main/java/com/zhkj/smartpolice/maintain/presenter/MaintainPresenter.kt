package com.zhkj.smartpolice.maintain.presenter

import com.google.gson.Gson
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.bean.*
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.json.JSONObject


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

    /**
     * 维修申请提交请求
     */

    fun onMaintainRequestPush(maintainRequestPushBean: MaintainRequestPushBean) {
        view?.showLoading()
        val gson = Gson()
        val toJson = gson.toJson(maintainRequestPushBean)
        LogUtil.i("提交的json参数===========$toJson")
        val httpResultBean = object : HttpResultBean<SucceedBean>() {}
        launch(Main) {
            ZyHttp.postJson(UrlConstant.APPLY_MAINTAIN, toJson, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onMaintainRequestPush(httpResultBean.bean ?: return@launch)
            }
        }
    }

    /**
     * 维修中心：部门选择树
     */

    fun onDepartmentStructure() {
        view?.showLoading()
        val httpResultBean = object : HttpResultBean<DepartmentStructureBean>() {}
        launch(Main) {
            ZyHttp.get(UrlConstant.DEPARTMENT_STRUCTURE, null, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onDepartmentStructure(httpResultBean.bean ?: return@launch)
            }
        }
    }

    /**
     * 维修中心：审批维修列表
     */

    fun onMaintainAudit(isApply: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["isApply"] = isApply
        val httpResultBean = object : HttpResultBean<PageModel<MaintainAuditBean>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.MAINTAIN_AUDIT, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onMaintainAudit(httpResultBean.bean ?: return@launch)
            }
        }
    }

    /**
     * 维修中心：维修管理员审批提交
     */
    fun onMaintainFeedback(
        applyContent: String,
        applyId: String,
        createTime: String,
        opinionType: String,
        optionUserId: String,
        optionUserName: String
    ) {
        view?.showLoading()
        val params = JSONObject()
        params.put("applyContent", applyContent)
        params.put("applyId", applyId)
        params.put("createTime", createTime)
        params.put("opinionType", opinionType)
        params.put("optionUserId", optionUserId)
        params.put("optionUserName", optionUserName)
        val httpResultBean = object : HttpResultBean<SucceedBean>() {}
        launch(Main) {
            ZyHttp.postJson(UrlConstant.MAINTAIN_AUDIT_FEEDBACK,params.toString(),httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onMaintainFeedback(httpResultBean.bean ?: return@launch)
            }
        }
    }
}