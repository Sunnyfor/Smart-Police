package com.zhkj.smartpolice.wxapi

import android.content.Intent
import android.view.View
import com.chinaums.pppay.unify.UnifyPayPlugin
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.LogUtil
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseReq
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhkj.wallet.R
import kotlinx.android.synthetic.main.act_pay_result.*

/**
 * Desc
 * Author ZhangYe
 * Mail zhangye98@foxmail.com
 * Date 2020/8/25 19:01
 */
class WXEntryActivity : BaseActivity(), IWXAPIEventHandler {

    private var api: IWXAPI? = null

    override fun setLayout(): Int = R.layout.act_pay_result

    override fun initView() {
        btn_finish.visibility = View.GONE
        api = WXAPIFactory.createWXAPI(this, UnifyPayPlugin.getInstance(this).appId)
        api?.handleIntent(intent, this)
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        api?.handleIntent(getIntent(), this)
    }

    override fun close() {

    }

    override fun onResp(baseResp: BaseResp) {
        LogUtil.i("WXEntryActivity --- baseReq: $baseResp")
        if (baseResp.type == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM) {
            val launchMiniProResp = baseResp as WXLaunchMiniProgram.Resp
            val extraData = launchMiniProResp.extMsg //对应小程序组件 <button open-type="launchApp"> 中的 app-parameter 属性
            LogUtil.i("onResp   ---   $extraData")
            val msg = ("onResp   ---   errStr：" + baseResp.errStr + " --- errCode： " + baseResp.errCode + " --- transaction： "
                    + baseResp.transaction + " --- openId：" + baseResp.openId + " --- extMsg：" + launchMiniProResp.extMsg)
            LogUtil.i(msg)
            tv_desc.text = msg
            UnifyPayPlugin.getInstance(this).wxListener.onResponse(this, baseResp)
        }
    }

    override fun onReq(baseReq: BaseReq) {
        LogUtil.i("WXEntryActivity --- baseResp: $baseReq")
    }
}