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
import com.zhkj.smartpolice.drugstore.model.DrugstoreContract
import com.zhkj.smartpolice.drugstore.model.DrugstorePresenter
import com.zhkj.smartpolice.meal.MealDetailActivity
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import kotlinx.android.synthetic.main.act_drugstore.*
import kotlinx.android.synthetic.main.layout_search.*
import kotlinx.coroutines.cancel

class DrugstoreActivity : BaseActivity(), DrugstoreContract.IDrugView {

    private lateinit var toolbar: Toolbar

    private val menuList = arrayListOf<MealMenuBean>()

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    private var labelId = ""

    private val shopId: String by lazy {
        intent.getStringExtra("shopId") ?: ""
    }

    private val presenter: DrugstorePresenter by lazy {
        DrugstorePresenter(this)
    }

    private val mealMenuAdapter: MealMenuAdapter by lazy {
        MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                labelId = getData(i).labelId ?: ""
                presenter.loadDrugList(pullRefreshFragment.page, shopId, labelId)
                notifyDataSetChanged()
                et_search.setText("")
                hideKeyboard()
            }
        }
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

        recyclerView_menu.layoutManager = LinearLayoutManager(this)
        recyclerView_menu.adapter = mealMenuAdapter

        pullRefreshFragment.layoutManager = LinearLayoutManager(this)
        pullRefreshFragment.adapter = DrugGoodsAdapter().apply {
            setOnItemClickListener { _, position ->
                MealDetailActivity.intent(this@DrugstoreActivity, getData(position), false)
            }
        }
        pullRefreshFragment.loadData = {
            presenter.loadDrugList(pullRefreshFragment.page, shopId, labelId)
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
                SearchDrugActivity.intent(this, shopId, et_search.text.toString())
                hideKeyboard()
            }
        }
    }

    override fun loadData() {
        presenter.loadDrugClassify(shopId)
    }

    override fun close() {
        presenter.cancel()
    }

    override fun loadDrugClassify(data: ArrayList<MealMenuBean>) {
        menuList.clear()
        menuList.addAll(data)
        mealMenuAdapter.notifyDataSetChanged()
        labelId = data[0].labelId ?: ""
        presenter.loadDrugList(1, shopId, labelId)
    }

    override fun loadDrugList(data: ArrayList<MealGoodsBean>) {
        pullRefreshFragment.addData(data)
    }
}