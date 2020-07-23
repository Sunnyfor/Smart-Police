package com.zhkj.smartpolice.maintain.view

import com.sunny.zy.base.IBaseView
import com.sunny.zy.base.PageModel
import com.zhkj.smartpolice.maintain.bean.*


interface IMaintainView : IBaseView {

    /**
     * 维修部件分类
     */
    fun onMaintainClassify(baseModel: PageModel<MaintainClassifyBean>) { }

    /**
     * 维修部件列表
     */
    fun onMaintainList(pagemodel: PageModel<MaintainListBean>){ }

    /**
     * 维修申请提交
     */
    fun onMaintainRequestPush(succeedBean: SucceedBean){ }

    /**
     * 部门树结构
     */
    fun onDepartmentStructure(departmentStructureBean: DepartmentStructureBean) { }

    /**
     * 维修处理列表
     */
    fun onMaintainAudit(pagemodel: ArrayList<MaintainAuditBean>){ }

    /**
     * 维修已处理列表
     */
    fun onMaintainAccomplish(info: ArrayList<MaintainAccompListBean>){ }

    /**
     * 维修管理审核反馈
     */
    fun onMaintainFeedback(succeedBean: SucceedBean) { }

    /**
     * 维修工人列表
     */
    fun onMaintainerList(barberListBean: ArrayList<MaintainerListBean>){ }

    /**
     * 下发维修任务
     */
    fun onIssueTask(succeedBean: SucceedBean){ }

    /**
     * 维修工任务列表
     */
    fun onMaintainTask(maintainTaskBean: ArrayList<MaintainTaskBean>) { }

    /**
     * 维修工提交维修成果
     */
    fun onMaintainFinish(succeedBean: SucceedBean){ }

    /**
     * 维修查找图片接口
     */
    fun onFindImagePath(findImagePathBean: ArrayList<FindImagePathBean>) { }
}