package com.zhkj.smartpolice.laundry.view

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.laundry.bean.LaundryLabelBean

interface LaundryView : IBaseView {
    /**
     * 洗衣店提交预约
     */

    fun showLaundryLabel(data: ArrayList<LaundryLabelBean>)

    fun onLaundryPutIn(succeedBean: BaseModel<Any>) {}
}