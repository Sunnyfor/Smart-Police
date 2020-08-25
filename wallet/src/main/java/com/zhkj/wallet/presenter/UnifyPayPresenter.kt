package com.zhkj.wallet.presenter

import com.zhkj.wallet.contract.UnifyPayContract
import com.zhkj.wallet.model.UnifyPayModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/8/25 18:29
 */
class UnifyPayPresenter(iView: UnifyPayContract.IView) : UnifyPayContract.Presenter(iView) {

    private val unifyPayModel: UnifyPayModel by lazy {
        UnifyPayModel()
    }

    override fun unifyPay(qmfPayType: Int, amount: Float) {
        launch(Main) {
            showLoading()
            unifyPayModel.unifyPay(qmfPayType, amount)?.let {
                view?.unifyPayResult(it)
            }
            hideLoading()
        }
    }
}