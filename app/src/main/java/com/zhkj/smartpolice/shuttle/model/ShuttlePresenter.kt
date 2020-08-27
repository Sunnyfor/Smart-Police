package com.zhkj.smartpolice.shuttle.model

import com.sunny.zy.base.IBaseView
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class ShuttlePresenter(iBaseView: IBaseView) : ShuttleContract.Presenter(iBaseView) {

    private val model: ShuttleModel by lazy {
        ShuttleModel()
    }

    override fun loadShuttleBusList(page: String, shopId: String) {
        launch(Main) {
            showLoading()
            model.loadShuttleBusList(page, shopId)?.let {
                if (view is ShuttleContract.IShuttleBusView) {
                    (view as ShuttleContract.IShuttleBusView).loadShuttleBusList(it)
                }
            }
            hideLoading()
        }
    }
}