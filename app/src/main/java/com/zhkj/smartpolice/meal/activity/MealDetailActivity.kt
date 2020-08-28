package com.zhkj.smartpolice.meal.activity

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseActivity
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.UrlConstant
import com.zhkj.smartpolice.meal.bean.MealBean
import kotlinx.android.synthetic.main.act_meal_detail.*

/**
 * 菜品详情
 */
class MealDetailActivity : BaseActivity() {

    private val bean: MealBean? by lazy {
        ZyFrameStore.getData<MealBean>("MealBean")
    }

    private val isCanBuy :Boolean by lazy {
        intent.getBooleanExtra("isCanBuy", true)
    }

    companion object {
        fun intent(context: Context, bean: MealBean, isCanBuy: Boolean? = true) {
            ZyFrameStore.setData("MealBean", bean)
            val intent = Intent(context, MealDetailActivity::class.java)
            intent.putExtra("isCanBuy", isCanBuy)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_meal_detail

    override fun initView() {

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_black)

        bean?.shopGoodsEntity?.let {
            Glide.with(this)
                .load(UrlConstant.LOAD_IMAGE_PATH_URL + it.imageId)
                .dontAnimate()
                .placeholder(R.drawable.svg_default_image)
                .into(iv_bg)

            tv_name.text = it.goodsName
            tv_price.text = ("￥${it.price}")
            tv_desc.text = it.description
        }

        iv_back.setOnClickListener(this)
        tv_shopping_cart.setOnClickListener(this)


        if (!isCanBuy) {
            tv_shopping_cart.visibility = View.GONE
        }
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.iv_back -> finish()
            tv_shopping_cart.id -> {
                bean?.let {
                    ZyFrameStore.getData<ArrayList<MealBean>>("MealGoodsBeanList")?.let { list ->
                        if (list.contains(it)) {
                            list[list.indexOf(it)].count++
                        } else {
                            list.add(it)
                        }
                        ToastUtil.show("成功加入购物车！")
                    }
                }
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {

    }
}