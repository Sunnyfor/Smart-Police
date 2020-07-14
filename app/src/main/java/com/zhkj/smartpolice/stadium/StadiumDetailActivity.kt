package com.zhkj.smartpolice.stadium

import android.view.View
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.GlideApp
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.merchant.MerchantBean
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import kotlinx.android.synthetic.main.act_stadium_detail.*

class StadiumDetailActivity : BaseActivity(), MerchantContract.IReserveInfoView {

    val presenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    override fun setLayout(): Int = R.layout.act_stadium_detail

    override fun initView() {
        defaultTitle(getString(R.string.reserve_details))
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        intent.getStringExtra("shopId")?.let {
            presenter.loadMerchantInfo(it)
        }
    }

    override fun close() {

    }

    override fun showMerchantInfo(data: MerchantBean) {
        GlideApp.with(this)
            .load("${UrlConstant.LOAD_IMAGE_PATH_URL}${data.imageId}")
            .placeholder(R.drawable.svg_default_image)
            .into(iv_photo)
    }
}