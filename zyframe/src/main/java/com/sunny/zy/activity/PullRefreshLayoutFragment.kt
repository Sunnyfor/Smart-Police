package com.sunny.zy.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.zy.R
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.widget.PullRefreshLayout

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/4 16:05
 */
class PullRefreshLayoutFragment : BaseFragment() {

    private var recyclerView: RecyclerView? = null
    private var pullRefreshLayout: PullRefreshLayout? = null

    var enableRefresh = true
    var enableLoadMore = true
    var page = 1
    var loadData: (() -> Unit)? = null


    override fun setLayout(): Int = R.layout.zy_layout_list

    override fun initView() {

        recyclerView = bodyView?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        pullRefreshLayout = bodyView?.findViewById(R.id.pullRefreshLayout)
        pullRefreshLayout?.setEnableRefresh(enableRefresh)
        pullRefreshLayout?.setEnableLoadMore(enableLoadMore)

        pullRefreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++
                loadData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                loadData()
            }
        })
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {
        loadData?.invoke()
    }

    override fun close() {

    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recyclerView?.layoutManager = layoutManager
    }

    fun <T> setBaseRecyclerAdapter(adapter: BaseRecycleAdapter<T>) {
        recyclerView?.adapter = adapter
    }

    fun <T> addData(data: ArrayList<T>) {

        var list = ArrayList<T>()

        recyclerView?.adapter?.let {
            if (it is BaseRecycleAdapter<*>) {
                list = it.list as ArrayList<T>
            }
        }

        if (page == 1) {
            list.clear()
            finishRefresh()
        } else {
            finishLoadMore()
        }

        if (data.isNotEmpty()) {
            list.addAll(data)
            hideEmptyView()
        } else {
            if (page == 1) {
                showEmptyView()
            }
        }
        recyclerView?.adapter?.notifyDataSetChanged()
    }

    fun finishLoadMore() {
        pullRefreshLayout?.finishLoadMore()
    }

    fun finishRefresh() {
        pullRefreshLayout?.finishRefresh()
    }

    fun showEmptyView() {
        bodyView?.findViewById<View>(R.id.empty_view)?.visibility = View.VISIBLE
    }

    fun hideEmptyView() {
        bodyView?.findViewById<View>(R.id.empty_view)?.visibility = View.GONE
    }
}