package com.zhkj.smartpolice.meal.fragment

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.activity.MealDetailActivity
import com.zhkj.smartpolice.meal.activity.MealOrderActivity
import com.zhkj.smartpolice.meal.adapter.MealGoodsAdapter
import com.zhkj.smartpolice.meal.adapter.MealMenuAdapter
import com.zhkj.smartpolice.meal.bean.MealBean
import com.zhkj.smartpolice.meal.bean.MealMenuBean
import com.zhkj.smartpolice.meal.model.MealContract
import com.zhkj.smartpolice.meal.model.MealPresenter
import kotlinx.android.synthetic.main.frag_restaurant_meal.*
import kotlinx.coroutines.cancel

/**
 * 订餐列表
 */
class MealFragment : BaseFragment(), MealContract.IMealMenuView {

    private val menuList = arrayListOf<MealMenuBean>()
    private val goodsList = arrayListOf<MealBean>()

    private val pullRefreshFragment = PullRefreshFragment<MealBean>()

    private val shopId: String by lazy {
        requireActivity().intent.getStringExtra("shopId") ?: ""
    }

    private val presenter: MealPresenter by lazy {
        MealPresenter(this)
    }

    private val mealMenuAdapter: MealMenuAdapter by lazy {
        MealMenuAdapter(menuList).apply {
            setOnItemClickListener { _, i ->
                this.index = i
                presenter.loadMealList(pullRefreshFragment.page, false, getData(i).labelId ?: "")
                notifyDataSetChanged()
            }
        }
    }

    private val mealGoodsAdapter: MealGoodsAdapter by lazy {
        MealGoodsAdapter(View.OnClickListener {
            val bean = it.tag as MealBean
            if (goodsList.contains(bean)) {
                goodsList[goodsList.indexOf(bean)].count++
            } else {
                goodsList.add(bean)
            }

            updateShoppingHit()

        },true).apply {
            setOnItemClickListener { _, i ->
                MealDetailActivity.intent(requireContext(), getData(i))
            }
        }
    }

    override fun setLayout(): Int = R.layout.frag_restaurant_meal

    override fun initView() {

        recyclerView_menu.layoutManager = LinearLayoutManager(requireContext())
        recyclerView_menu.adapter = mealMenuAdapter

        pullRefreshFragment.layoutManager = GridLayoutManager(requireContext(), 2)
        pullRefreshFragment.adapter = mealGoodsAdapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        childFragmentManager.beginTransaction().replace(fl_container.id, pullRefreshFragment).commit()

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
                MealOrderActivity.intent(requireContext(), shopId)
            }
        }
    }

    override fun loadData() {
        presenter.loadMealMenu()
    }

    override fun close() {
        presenter.cancel()
    }

    override fun loadMealMenu(data: ArrayList<MealMenuBean>) {
        menuList.clear()
        menuList.addAll(data)
        mealMenuAdapter.notifyDataSetChanged()
        if(data.isNotEmpty()){
            presenter.loadMealList(pullRefreshFragment.page, false, data[0].labelId ?: "")
        }
    }

    override fun loadMealList(data: ArrayList<MealBean>) {
        pullRefreshFragment.addData(data)
    }

    private fun updateShoppingHit() {
        if (goodsList.isEmpty()) {
            tv_shopping_hit.text = "购物车空的哦"
            tv_shopping_hit.setTextColor(ContextCompat.getColor(requireContext(), R.color.font_gray))
            iv_shopping_cart.setImageResource(R.drawable.svg_meal_shopping_cart_null)
            tv_commit.setBackgroundColor(Color.parseColor("#CBCBCB"))

        } else {
            var total = 0F
            goodsList.filter { it.isChecked }.forEach {
                it.shopGoodsEntity?.price?.let { price ->
                    total += price.toFloat() * it.count
                }
            }
            tv_shopping_hit.setTextColor(ContextCompat.getColor(requireContext(), R.color.font_red))
            tv_shopping_hit.text = ("合计：¥${total}")
            iv_shopping_cart.setImageResource(R.drawable.svg_meal_shopping_cart_full)
            tv_commit.setBackgroundResource(R.color.font_orange)
        }
    }
}