package com.zhkj.smartpolice.meal.model

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MealPresenter(iBaseView: IBaseView) : MealContract.Presenter(iBaseView) {

    private val mealModel: MealModel by lazy {
        MealModel()
    }


    override fun loadRestaurantList() {
        launch(Dispatchers.Main) {
            showLoading()
            mealModel.loadRestaurantList()?.let {
                if (view is MealContract.IRestaurantView) {
                    (view as MealContract.IRestaurantView).loadRestaurantList(it)
                }
            }
            hideLoading()
        }
    }
}