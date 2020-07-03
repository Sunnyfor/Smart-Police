package com.zhkj.smartpolice.maintain.view

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.IBaseView
import com.sunny.zy.base.PageModel
import com.zhkj.smartpolice.maintain.bean.MaintainClassifyBean
import com.zhkj.smartpolice.maintain.bean.MaintainListBean


interface IMaintainView : IBaseView {

    /**
     * 维修部件分类
     */
    fun onMaintainClassify(baseModel: PageModel<MaintainClassifyBean>) { }


    /**
     * 维修部件列表
     */

    fun onMaintainList(pagemodel: PageModel<MaintainListBean>){ }
}