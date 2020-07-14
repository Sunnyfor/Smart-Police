package com.zhkj.smartpolice.drugstore

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.drugstore.adapter.DrugGoodsAdapter
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import kotlinx.android.synthetic.main.act_drugstore.*
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.coroutines.cancel

class DrugstoreActivity : BaseActivity(), MealContract.IMealMenuView {

    private lateinit var toolbar: Toolbar

    private var shopId: String? = null

    private val menuList = arrayListOf<MealMenuBean>()

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    private val mealMenuAdapter: MealMenuAdapter by lazy {
        MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                presenter.loadMealGoodsList(pullRefreshFragment.page.toString(), shopId ?: "", getData(i).labelId ?: "")
                notifyDataSetChanged()
                et_search.setText("")
                hideKeyboard()
            }
        }
    }

    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    companion object {
        fun intent(context: Context, shopId: String?) {
            val intent = Intent(context, DrugstoreActivity::class.java)
            intent.putExtra("shopId", shopId)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_drugstore

    override fun initView() {

        toolbar = defaultTitle("药品列表")

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_suggestion_box -> FeedbackActivity.intent(this, shopId)
            }
            return@setOnMenuItemClickListener true
        }

        shopId = intent.getStringExtra("shopId")

        recyclerView_menu.layoutManager = LinearLayoutManager(this)
        recyclerView_menu.adapter = mealMenuAdapter

        pullRefreshFragment.layoutManager = LinearLayoutManager(this)
        pullRefreshFragment.adapter = DrugGoodsAdapter()
        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshFragment).commit()

        setOnClickListener(btn_search)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        toolbar.inflateMenu(R.menu.menu_drugstore_title)
        return true
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_search.id -> {
                presenter.searchMealGoodsList(shopId ?: return, et_search.text.toString())
                hideKeyboard()
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
}