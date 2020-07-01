package com.zhkj.smartpolice.drugstore

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.drugstore.adapter.DrugGoodsAdapter
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import kotlinx.android.synthetic.main.act_meal.*

class DrugstoreActivity : BaseActivity() {

    private val menuList = arrayListOf<MealMenuBean>()
    private val goodsList = arrayListOf<MealGoodsBean>()

    override fun setLayout(): Int = R.layout.act_drugstore

    override fun initView() {

        defaultTitle("药品列表")

        menuList.add(MealMenuBean("1", "营养成分", 1))
        menuList.add(MealMenuBean("2", "营养健康", 0))
        menuList.add(MealMenuBean("3", "保健机械", 0))
        menuList.add(MealMenuBean("4", "护理护具", 0))
        menuList.add(MealMenuBean("5", "传统滋补", 0))
        menuList.add(MealMenuBean("6", "隐形眼镜", 0))

        recyclerView_menu.layoutManager = LinearLayoutManager(this)
        recyclerView_menu.adapter = MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                notifyDataSetChanged()
            }
        }

        goodsList.add(MealGoodsBean("1", "莲花清瘟胶囊", 0, 46, "清瘟解毒，宣肺泄热", "￥49.90"))
        goodsList.add(MealGoodsBean("1", "莲花清瘟胶囊", 0, 46, "清瘟解毒，宣肺泄热", "￥49.90"))
        goodsList.add(MealGoodsBean("1", "感冒药", 0, 10, "丫米", "￥39.90"))

        recyclerView_goods.layoutManager = LinearLayoutManager(this)
        recyclerView_goods.adapter = DrugGoodsAdapter(goodsList)

    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {
    }
}