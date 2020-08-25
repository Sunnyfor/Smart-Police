package com.zhkj.wallet.contract

import com.sunny.zy.base.BasePresenter
import com.sunny.zy.base.IBaseView
import com.zhkj.wallet.bean.UnifyPayBean

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/8/25 18:27
 */
class UnifyPayContract {
    interface IView : IBaseView {
        fun unifyPayResult(unifyPayBean: UnifyPayBean)
    }

    abstract class Presenter(iView: IView) : BasePresenter<IView>(iView) {
        /**
         * 下单
         * @param qmfPayType  1=支付宝，2=微信，3全民付，4银联云闪付，5Apply Pay
         */
        abstract fun unifyPay(qmfPayType: Int, amount: Float)
    }

}