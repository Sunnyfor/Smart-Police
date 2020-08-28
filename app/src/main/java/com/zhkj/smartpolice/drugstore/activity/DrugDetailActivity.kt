package com.zhkj.smartpolice.drugstore.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.drugstore.bean.DrugBean
import kotlinx.android.synthetic.main.act_meal_detail.*

/**
 * 药品详情
 */
class DrugDetailActivity : BaseActivity() {

    val bean: DrugBean? by lazy {
        ZyFrameStore.getData<DrugBean>("DrugBean")
    }

    companion object {
        fun intent(context: Context, bean: DrugBean) {
            ZyFrameStore.setData("DrugBean", bean)
            val intent = Intent(context, DrugDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_meal_detail

    override fun initView() {

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_black)

        Glide.with(this)
            .load(UrlConstant.LOAD_IMAGE_PATH_URL + bean?.imageId)
            .dontAnimate()
            .placeholder(R.drawable.svg_default_image)
            .into(iv_bg)

        tv_name.text = bean?.goodsName
        tv_price.text = ("￥${bean?.price}")
        tv_desc.text = bean?.description

        tv_shopping_cart.visibility = View.GONE

        iv_back.setOnClickListener(this)

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.iv_back -> finish()
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}