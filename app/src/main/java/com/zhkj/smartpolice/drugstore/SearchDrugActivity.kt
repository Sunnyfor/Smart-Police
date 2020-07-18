package com.zhkj.smartpolice.drugstore

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.drugstore.adapter.DrugGoodsAdapter
import com.zhkj.smartpolice.meal.MealDetailActivity
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import kotlinx.android.synthetic.main.act_search_drug.*
import kotlinx.android.synthetic.main.layout_search.*

class SearchDrugActivity : BaseActivity(), MealContract.IMealMenuView {

    companion object {
        fun intent(context: Context, shopId: String?, keyName: String? = "") {
            val intent = Intent(context, SearchDrugActivity::class.java)
            intent.putExtra("shopId", shopId)
            intent.putExtra("keyName", keyName)
            context.startActivity(intent)
        }
    }

    private var keyName = ""
    private var shopId = ""

    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    override fun setLayout(): Int = R.layout.act_search_drug

    override fun initView() {
        defaultTitle("搜索结果")

        keyName = intent.getStringExtra("keyName") ?: ""
        shopId = intent.getStringExtra("shopId") ?: ""

        et_search.setText(keyName)

        pullRefreshFragment.enableLoadMore = false

        pullRefreshFragment.adapter = DrugGoodsAdapter().apply {
            setOnItemClickListener { _, position ->
                MealDetailActivity.intent(this@SearchDrugActivity, getData(position), false)
            }
        }

        pullRefreshFragment.loadData = {
            loadData()
        }

        supportFragmentManager.beginTransaction().replace(fl_content.id, pullRefreshFragment).commit()


        btn_search.setOnClickListener(this)
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            btn_search.id -> {
                keyName = et_search.text.toString()
                loadData()
            }
        }
    }

    override fun loadData() {
        presenter.searchMealGoodsList(shopId, keyName)
        hideKeyboard()
    }

    override fun close() {

    }

    override fun loadMealMenu(data: ArrayList<MealMenuBean>) {

    }

    override fun loadMealGoodsList(data: ArrayList<MealGoodsBean>) {
        pullRefreshFragment.addData(data)
    }
}