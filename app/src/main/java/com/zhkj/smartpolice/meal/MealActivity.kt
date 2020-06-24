package com.zhkj.smartpolice.meal

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.widget.PlaceOrderPopupWindow
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealGoodsAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import kotlinx.android.synthetic.main.act_meal.*

class MealActivity : BaseActivity() {

    private val menuList = arrayListOf<MealMenuBean>()
    private val goodsList = arrayListOf<MealGoodsBean>()

    override fun setLayout(): Int = R.layout.act_meal

    override fun initView() {

        menuList.add(MealMenuBean("1", "热卖", 1))

        menuList.add(MealMenuBean("2", "素菜系列", 0))

        menuList.add(MealMenuBean("3", "凉菜系列", 0))

        menuList.add(MealMenuBean("4", "肉菜系列", 0))

        menuList.add(MealMenuBean("5", "靓汤系列", 0))

        menuList.add(MealMenuBean("6", "主食系列", 0))

        recyclerView_menu.layoutManager = LinearLayoutManager(this)
        recyclerView_menu.adapter = MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                notifyDataSetChanged()
            }
        }

        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))
        goodsList.add(MealGoodsBean("1", "小炒手撕包菜", 0, 1, "", "￥9.90"))

        recyclerView_goods.layoutManager = LinearLayoutManager(this)
        recyclerView_goods.adapter = MealGoodsAdapter(goodsList)

        iv_back.setOnClickListener(this)

        btn_commit.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            iv_back.id -> finish()
            btn_commit.id -> {
                PlaceOrderPopupWindow(this, goodsList).apply {
                    showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
                }
            }
        }
    }

    override fun loadData() {

    }

    override fun close() {
    }
}