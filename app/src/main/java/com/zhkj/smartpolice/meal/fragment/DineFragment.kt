package com.zhkj.smartpolice.meal.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.MealDetailActivity
import com.zhkj.smartpolice.meal.adapter.MealGoodsAdapter
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import kotlinx.android.synthetic.main.frag_restaurant_meal.*
import kotlinx.coroutines.cancel

/**
 * 就餐列表
 */
class DineFragment : BaseFragment(), MealContract.IMealMenuView {

    private val menuList = arrayListOf<MealMenuBean>()
    private val goodsList = arrayListOf<MealGoodsBean>()

    private val pullRefreshFragment = PullRefreshFragment<MealGoodsBean>()

    private val shopId: String by lazy {
        requireActivity().intent.getStringExtra("shopId") ?: ""
    }

    private val mealMenuAdapter: MealMenuAdapter by lazy {
        MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                presenter.loadMealGoodsList(pullRefreshFragment.page, shopId, getData(i).labelId ?: "")
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

        }).apply {
            setOnItemClickListener { _, i ->
                MealDetailActivity.intent(requireContext(), getData(i))
            }
        }
    }

    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }


    override fun setLayout(): Int = R.layout.frag_restaurant_dine

    override fun initView() {

        menuList.add(MealMenuBean("", "1", "早餐", "", "", "", "", "", "", "", "", ""))
        menuList.add(MealMenuBean("", "2", "中餐", "", "", "", "", "", "", "", "", ""))
        menuList.add(MealMenuBean("", "3", "晚餐", "", "", "", "", "", "", "", "", ""))

        recyclerView_menu.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_menu.adapter = mealMenuAdapter

        pullRefreshFragment.layoutManager = GridLayoutManager(requireContext(), 2)
        pullRefreshFragment.adapter = mealGoodsAdapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        childFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshFragment).commit()

        ZyFrameStore.setData("MealGoodsBeanList", goodsList)

    }


    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        presenter.loadMealGoodsList(pullRefreshFragment.page, shopId, menuList[0].labelId ?: "")
    }

    override fun close() {
        presenter.cancel()
    }

    override fun loadMealMenu(data: ArrayList<MealMenuBean>) {

    }

    override fun loadMealGoodsList(data: ArrayList<MealGoodsBean>) {
        pullRefreshFragment.addData(data)
    }

}