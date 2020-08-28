package com.zhkj.smartpolice.drugstore.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseActivity
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.drugstore.adapter.DrugGoodsAdapter
import com.zhkj.smartpolice.drugstore.bean.DrugBean
import com.zhkj.smartpolice.drugstore.model.DrugContract
import com.zhkj.smartpolice.drugstore.model.DrugPresenter
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import kotlinx.android.synthetic.main.act_search_drug.*
import kotlinx.android.synthetic.main.layout_search.*

class SearchDrugActivity : BaseActivity(), DrugContract.IDrugView {

    private var keyName = ""
    private var shopId = ""

    private val pullRefreshFragment = PullRefreshFragment<DrugBean>()

    private val presenter: DrugPresenter by lazy {
        DrugPresenter(this)
    }

    companion object {
        fun intent(context: Context, shopId: String?, keyName: String? = "") {
            val intent = Intent(context, SearchDrugActivity::class.java)
            intent.putExtra("shopId", shopId)
            intent.putExtra("keyName", keyName)
            context.startActivity(intent)
        }
    }

    override fun setLayout(): Int = R.layout.act_search_drug

    override fun initView() {

        defaultTitle("搜索结果")

        keyName = intent.getStringExtra("keyName") ?: ""
        shopId = intent.getStringExtra("shopId") ?: ""

        et_search.setText(keyName)

        pullRefreshFragment.enableLoadMore = false

        pullRefreshFragment.adapter = DrugGoodsAdapter().apply {
            setOnItemClickListener { _, position ->
                DrugDetailActivity.intent(this@SearchDrugActivity, getData(position))
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
        presenter.searchDrugList(shopId, keyName)
        hideKeyboard()
    }

    override fun close() {

    }

    override fun loadDrugClassify(data: ArrayList<MealMenuBean>) {

    }

    override fun loadDrugList(data: ArrayList<DrugBean>) {
        pullRefreshFragment.addData(data)
    }
}