package com.zhkj.smartpolice.meal

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.act_meal_detail.*

class MealDetailActivity : BaseActivity() {

    companion object {
        fun intent(context: Context, bean: MealGoodsBean) {
            ZyFrameStore.setData("MealGoodsBean",bean)
            val intent = Intent(context, MealDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_meal_detail

    override fun initView() {

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_black)

        val bean = ZyFrameStore.getData<MealGoodsBean>("MealGoodsBean")

        Glide.with(this)
            .load(UrlConstant.LOAD_IMAGE_PATH_URL + bean?.imageId)
            .dontAnimate()
            .placeholder(R.drawable.svg_default_image)
            .into(iv_bg)

        tv_name.text = bean?.goodsName
        tv_price.text = ("￥${bean?.price}")
        tv_desc.text = bean?.description

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