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
    fun onMaintainAudit(pagemodel: PageModel<MaintainAuditBean>){ }

    /**
     * 维修已处理列表
     */
    fun onMaintainAccomplish(){ }

    /**
     * 维修管理审核反馈
     */
    fun onMaintainFeedback(succeedBean: SucceedBean) { }
}