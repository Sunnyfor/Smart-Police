package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.meal.bean.RestaurantBean

interface MealContract {

    interface IRestaurantView : IBaseView {
        fun loadRestaurantList(data: ArrayList<RestaurantBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        abstract fun loadRestaurantList() //加载餐厅列表

    }
}