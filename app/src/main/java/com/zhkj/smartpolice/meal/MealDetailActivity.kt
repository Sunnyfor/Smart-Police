package com.zhkj.smartpolice.meal

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.act_meal_detail.*

class MealDetailActivity : BaseActivity() {

    companion object {
        fun intent(context: Context, bean: MealGoodsBean) {
            val intent = Intent(context, MealDetailActivity::class.java)
            intent.putExtra("MealGoodsBean", bean)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_meal_detail

    override fun initView() {

        window.statusBarColor = ContextCompat.getColor(this, com.sunny.zy.R.color.color_black)

        val bean = intent.getSerializableExtra("MealGoodsBean") as MealGoodsBean
        tv_name.text = bean.title
        tv_month_sales.text = ("月售${bean.count}")
        tv_price.text = bean.price
        tv_desc.text = bean.weight

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