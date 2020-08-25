package com.zhkj.smartpolice.wxapi

import android.content.Intent
import android.view.View
import com.chinaums.pppay.unify.UnifyPayPlugin
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhkj.wallet.R

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/8/25 19:01
 */
class WXEntryActivity : BaseActivity(), IWXAPIEventHandler {

    private lateinit var api: IWXAPI

    override fun setLayout(): Int = R.layout.act_pay_result

    override fun initView() {
        api = WXAPIFactory.createWXAPI(this, UnifyPayPlugin.getInstance(this).appId)
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api.handleIntent(getIntent(), this)
    }

    override fun close() {

    }

    override fun onResp(baseReq: BaseResp?) {
        LogUtil.i("WXEntryActivity --- baseReq: $baseReq")
    }

    override fun onReq(baseResp: BaseReq?) {
        LogUtil.i("WXEntryActivity --- baseResp: $baseResp")
    }
}