package com.zhkj.smartpolice.meal

import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealGoodsAdapter
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import com.zhkj.smartpolice.meal.widget.PlaceOrderPopupWindow
import kotlinx.android.synthetic.main.act_meal.*
import kotlinx.coroutines.cancel

class MealActivity : BaseActivity(), MealContract.IMealMenuView {

    private var shopId: String? = null

    private val menuList = arrayListOf<MealMenuBean>()
    private val goodsList = arrayListOf<MealGoodsBean>()

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    private val mealMenuAdapter: MealMenuAdapter by lazy {
        MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                notifyDataSetChanged()
            }
        }
    }

    private val mealGoodsAdapter: MealGoodsAdapter by lazy {
        MealGoodsAdapter().apply {
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
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_commit.id -> {
                PlaceOrderPopupWindow(this, goodsList).apply {
                    showAtLocation(contentView, Gravity.BOTTOM, 0, 0)
                }
            }
            iv_shopping_cart.id -> startActivity(Intent(this, MealOrderActivity::class.java))
        }
    }

    override fun loadData() {
        presenter.loadMealMenu(shopId ?: return)
        presenter.loadMealGoodsList(pullRefreshFragment.page.toString(), shopId ?: return)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun loadMealMenu(data: ArrayList<MealMenuBean>) {
        menuList.clear()
        menuList.addAll(data)
        mealMenuAdapter.notifyDataSetChanged()
    }

    override fun loadMealGoodsList(data: ArrayList<MealGoodsBean>) {
        pullRefreshFragment.addData(data)
    }
}