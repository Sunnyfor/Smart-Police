package com.sunny.zy.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter

object RouterManager {

    /**
     * App模块
     */
    const val LOGIN_ACTIVITY = "/app/LoginActivity"        //登录

    /**
     * 钱包模块
     */
    const val WALLET_ACTIVITY = "/wallet/WalletActivity"        //我的钱包
    const val RECHARGE_ACTIVITY = "/wallet/RechargeActivity"    //充值
    const val WITHDRAWAL_ACTIVITY = "/wallet/WithdrawalActivity"//提现
    const val PAY_CODE_ACTIVITY = "/wallet/PayCodeActivity"     //支付码
    const val RECORD_ACTIVITY = "/wallet/RecordActivity"        //账单记录

    fun navigation(context: Context, path: String) {
        ARouter.getInstance()
            .build(path)
            .navigation(context)
    }

    fun navigation(context: Context, path: String, flags: Int) {
        ARouter.getInstance()
            .build(path)
            .withFlags(flags)
            .navigation(context)
    }

    fun navigation(context: Context, path: String, bundle: Bundle) {
        ARouter.getInstance()
            .build(path)
            .with(bundle)
            .navigation(context)
    }

    fun navigation(context: Context, path: String, bundle: Bundle, flags: Int) {
        ARouter.getInstance()
            .build(path)
            .with(bundle)
            .withFlags(flags)
            .navigation(context)
    }

    fun navigationWithCode(activity: Activity, path: String, bundle: Bundle, code: Int) {
        ARouter.getInstance()
            .build(path)
            .with(bundle)
            .navigation(activity, code)
    }

    fun navigationWidthAnimation(
        context: Context,
        path: String,
        enterAnim: Int = android.R.anim.slide_in_left,
        exitAnim: Int = android.R.anim.slide_out_right
    ) {
        ARouter.getInstance()
            .build(path)
            .withTransition(enterAnim, exitAnim)
            .navigation(context)
    }
}