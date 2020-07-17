package com.zhkj.smartpolice.meal

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealGoodsAdapter
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import kotlinx.android.synthetic.main.act_meal.*
import kotlinx.coroutines.cancel

/**
 * 订餐列表
 */
class MealActivity : BaseActivity(), MealContract.IMealMenuView {

    private var shopId: String? = null

    private val menuList = arrayListOf<MealMenuBean>()
    private val goodsList = arrayListOf<MealGoodsBean>()

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    private val mealMenuAdapter: MealMenuAdapter by lazy {
        MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                presenter.loadMealGoodsList(pullRefreshFragment.page.toString(), shopId ?: "", getData(i).labelId ?: "")
                notifyDataSetChanged()
            }
        }
    }

    private val mealGoodsAdapter: MealGoodsAdapter by lazy {
        MealGoodsAdapter(View.OnClickListener {
            val bean = it.tag as MealGoodsBean
            if (goodsList.contains(bean)) {
                goodsList[goodsList.indexOf(bean)].count++
            } else {
                goodsList.add(bean)
            }

            updateShoppingHit()

        }).apply {
            setOnItemClickListener { _, i ->
                MealDetailActivity.intent(this@MealActivity, getData(i))
            }
        }
    }


    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    companion object {
        fun intent(context: Context, shopId: String?) {
            val intent = Intent(context, MealActivity::class.java)
            intent.putExtra("shopId", shopId)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_meal

    override fun initView() {

        defaultTitle("订餐列表")

        shopId = intent.getStringExtra("shopId")

        recyclerView_menu.layoutManager = LinearLayoutManager(this)
        recyclerView_menu.adapter = mealMenuAdapter

        pullRefreshFragment.layoutManager = GridLayoutManager(this, 2)
        pullRefreshFragment.adapter = mealGoodsAdapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshFragment).commit()

        setOnClickListener(
            tv_commit,
            iv_shopping_cart
        )

        ZyFrameStore.setData("MealGoodsBeanList", goodsList)

    }

    override fun onStart() {
        super.onStart()
        updateShoppingHit()
    }


    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_commit.id, iv_shopping_cart.id -> {
                if (goodsList.isEmpty()) {
                    return
                }
                MealOrderActivity.intent(this, shopId ?: "")
            }
        }
    }

    override fun loadData() {
        presenter.loadMealMenu(shopId ?: return)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun loadMealMenu(data: ArrayList<MealMenuBean>) {
        menuList.clear()
        menuList.addAll(data)
        mealMenuAdapter.notifyDataSetChanged()

        presenter.loadMealGoodsList(pullRefreshFragment.page.toString(), shopId ?: return, data[0].labelId ?: "")
    }

    override fun loadMealGoodsList(data: ArrayList<MealGoodsBean>) {
        pullRefreshFragment.addData(data)
    }

    private fun updateShoppingHit() {
        if (goodsList.isEmpty()) {
            tv_shopping_hit.text = "购物车空的哦"
            tv_shopping_hit.setTextColor(ContextCompat.getColor(this, R.color.font_gray))
            iv_shopping_cart.setImageResource(R.drawable.svg_meal_shopping_cart_null)
            tv_commit.setBackgroundColor(Color.parseColor("#CBCBCB"))

        } else {
            var total = 0F
            goodsList.filter { it.isChecked }.forEach {
                it.price?.let { price ->
                    total += price.toFloat() * it.count
                }
            }
            tv_shopping_hit.setTextColor(ContextCompat.getColor(this, R.color.font_red))
            tv_shopping_hit.text = ("合计：¥${total}")
            iv_shopping_cart.setImageResource(R.drawable.svg_meal_shopping_cart_full)
            tv_commit.setBackgroundResource(R.color.font_orange)
        }
    }
}