package com.zhkj.smartpolice.laundry.view

import com.sunny.zy.base.BaseModel
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.maintain.bean.SucceedBean

interface LaundryView: IBaseView {
    /**
     * 洗衣店提交预约
     */
    fun onLaundryPutIn(succeedBean: BaseModel<Any>){ }
}