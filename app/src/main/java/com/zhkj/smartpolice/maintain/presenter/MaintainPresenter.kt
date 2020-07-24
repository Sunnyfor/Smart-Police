package com.zhkj.smartpolice.maintain.presenter

import android.util.Log
import com.google.gson.Gson
import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.PageModel
import com.sunny.zy.http.ZyHttp
import com.sunny.zy.http.bean.HttpResultBean
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.maintain.bean.*
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MaintainPresenter(view: IMaintainView) : BasePresenter<IMaintainView>(view) {


    fun onMaintainClassify(shopType: Int, activeState: Int, certifyState: Int) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["shopType"] = shopType.toString()
//        params["activeState"] = activeState.toString()
//        params["certifyState"] = certifyState.toString()

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
     * 维修中心：列表查询
     * isApply：1.代表查询申请人申请数据、2.审核人的待审核数据、3.完成审核数据
     */

    fun onMaintainAudit(isApply: String, page: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["isApply"] = isApply
        params["page"] = page
        LogUtil.i("维修管理员下载维修申请列表数据传递参数=========$params")
        val httpResultBean = object : HttpResultBean<PageModel<MaintainAuditBean>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.MAINTAIN_AUDIT, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onMaintainAudit(httpResultBean.bean?.data?.list ?: return@launch)
                } else {
                    ToastUtil.show(httpResultBean.bean?.msg)
                }
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
        optionUserName: String?
    ) {
        view?.showLoading()
        val params = JSONObject()
        params.put("applyContent", applyContent)
        params.put("applyId", applyId)
        params.put("createTime", createTime)
        params.put("opinionType", opinionType)
        params.put("optionUserName", optionUserName)
        val httpResultBean = object : HttpResultBean<SucceedBean>() {}
        launch(Main) {
            ZyHttp.postJson(UrlConstant.MAINTAIN_AUDIT_FEEDBACK, params.toString(), httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                view?.onMaintainFeedback(httpResultBean.bean ?: return@launch)
            }
        }
    }

    /**
     *维修中心：已审核完的数据
     */
    fun onMaintainAccomplish(page: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["page"] = page
        val httpResultBean = object : HttpResultBean<PageModel<MaintainAccompListBean>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.MAINIAIN_AUDIT_FINISH, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onMaintainAccomplish(httpResultBean.bean?.data?.list ?: return@launch)
                } else {
                    ToastUtil.show(httpResultBean.bean?.msg)
                }
            }
        }
    }

    /**
     * 维修中心：维修工人列表
     */
    fun onMaintainerList(deptId: String, isDisables: String, noRole: String, position: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["deptId"] = deptId
        params["isDisables"] = isDisables
        params["noRole"] = noRole
        params["position"] = position
        val httpResultBean = object : HttpResultBean<PageModel<MaintainerListBean>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.MAINTAINER_LIST, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onMaintainerList(httpResultBean.bean?.data?.list ?: return@launch)
                } else {
                    ToastUtil.show(httpResultBean.bean?.msg)
                }
            }
        }
    }

    /**
     * 下发维修任务
     */
    fun onIssueTask(
        content: String,
        operation: String,
        operationId: String,
        operationPhone: String,
        professionId: String,
        repairDate: String
    ) {
        view?.showLoading()
        val params = JSONObject()
        params.put("content", content)
        params.put("operation", operation)
        params.put("operationId", operationId)
        params.put("operationPhone", operationPhone)
        params.put("professionId", professionId)
        params.put("repairDate", repairDate)
        LogUtil.i("上传的josn 数据是========$params")
        val httpResultBean = object : HttpResultBean<SucceedBean>() {}
        launch(Main) {
            ZyHttp.postJson(UrlConstant.ISSUE_TASK, params.toString(), httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onIssueTask(httpResultBean.bean ?: return@launch)
                } else {
                    ToastUtil.show(httpResultBean.bean?.msg)
                }
            }
        }
    }

    /**
     * 维修工人
     */
    fun onMaintainTask(repairState: String, page: String) {
        view?.showLoading()
        val params = HashMap<String, String>()
        params["repairState"] = repairState
        params["page"] = page
        val httpResultBean = object : HttpResultBean<PageModel<MaintainTaskBean>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.MAINTAIN_TASK, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onMaintainTask(httpResultBean.bean?.data?.list ?: return@launch)
                } else {
                    ToastUtil.show(httpResultBean.bean?.msg)
                }
            }
        }
    }

    /**
     * 维修员提交完成
     */
    fun onMaintainFinish(
        content: String,
        finishDate: String,
        groupId: String,
        operation: String,
        operationId: String,
        operationPhone: String,
        professionId: String,
        recordId: String,
        repairDate: String,
        repairState: String
    ) {
        view?.showLoading()
        val params = JSONObject()
        params.put("content", content)
        params.put("finishDate", finishDate)
        params.put("groupId", groupId)
        params.put("operation", operation)
        params.put("operationId", operationId)
        params.put("operationPhone", operationPhone)
        params.put("professionId", professionId)
        params.put("recordId", recordId)
        params.put("repairDate", repairDate)
        params.put("repairState", repairState)
        val httpResultBean = object : HttpResultBean<SucceedBean>() {}
        launch(Main) {
            ZyHttp.putJson(UrlConstant.MAINTAIN_FINISH, params.toString(), httpResultBean)
            if (httpResultBean.isSuccess()) {
                view?.hideLoading()
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onMaintainFinish(httpResultBean.bean ?: return@launch)
                } else {
                    ToastUtil.show(httpResultBean.bean?.msg)
                }
            }
        }
    }

    /**
     * 维修图片列表下载
     */
    fun onFindImagePath(groupId: String) {
        val params = HashMap<String, String>()
        params["groupId"] = groupId
        val httpResultBean = object : HttpResultBean<PageModel<FindImagePathBean>>() {}
        launch(Main) {
            ZyHttp.post(UrlConstant.FIND_IMAGE_PATH_URL, params, httpResultBean)
            if (httpResultBean.isSuccess()) {
                if (httpResultBean.bean?.code?.toInt() == 0) {
                    view?.onFindImagePath(httpResultBean.bean?.data?.list ?: return@launch)
                } else {
                    ToastUtil.show(
                        httpResultBean.bean?.msg
                    )
                }
            }
        }
    }
}