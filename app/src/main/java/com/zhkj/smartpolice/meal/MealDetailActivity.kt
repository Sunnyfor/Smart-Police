package com.zhkj.smartpolice.meal

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
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.act_meal_detail.*

class MealDetailActivity : BaseActivity() {

    companion object {
        fun intent(context: Context, bean: MealGoodsBean) {
            ZyFrameStore.setData("MealGoodsBean", bean)
            val intent = Intent(context, MealDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    val bean: MealGoodsBean? by lazy {
        ZyFrameStore.getData<MealGoodsBean>("MealGoodsBean")
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

        iv_back.setOnClickListener(this)
        tv_shopping_cart.setOnClickListener(this)

    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            R.id.iv_back -> finish()
            tv_shopping_cart.id -> {
                bean?.let {
                    ZyFrameStore.getData<ArrayList<MealGoodsBean>>("MealGoodsBeanList")?.let { list ->
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