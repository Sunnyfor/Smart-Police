package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.bean.MealRecordBean
import com.zhkj.smartpolice.meal.bean.RestaurantBean

interface MealContract {

    interface IRestaurantView : IBaseView {
        fun showRestaurantList(data: ArrayList<RestaurantBean>)
    }

    interface IMealMenuView : IBaseView {
        fun loadMealMenu(data: ArrayList<MealMenuBean>)
        fun loadMealGoodsList(data: ArrayList<MealGoodsBean>)
    }

    interface IMealRecordView : IBaseView {
        fun showMealRecord(data: ArrayList<MealRecordBean>)
    }

    abstract class Presenter(iBaseView: IBaseView) : BasePresenter<IBaseView>(iBaseView) {

        //餐厅列表
        abstract fun loadRestaurantList(page: String)

        //餐厅菜单分类
        abstract fun loadMealMenu(shopId: String)

        //餐厅菜品列表
        abstract fun loadMealGoodsList(page: String, shopId: String)

        //订餐记录
        abstract fun loadMealRecord(page: String)

    }
}