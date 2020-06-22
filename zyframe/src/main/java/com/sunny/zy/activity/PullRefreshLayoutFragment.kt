package com.sunny.zy.activity

import android.view.View
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
abstract class PullRefreshLayoutFragment : BaseFragment() {

    var enableRefresh = true
    var enableLoadMore = true
    var page = 1

    private var pullRefreshLayout: PullRefreshLayout? = null

    override fun setLayout(): Int = R.layout.zy_layout_list

    override fun initView() {
        val recyclerView: RecyclerView? = bodyView?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = initLayoutManager()
        recyclerView?.adapter = initBaseRecyclerAdapter()

        pullRefreshLayout = bodyView?.findViewById(R.id.pullRefreshLayout)
        pullRefreshLayout?.setEnableRefresh(enableRefresh)
        pullRefreshLayout?.setEnableLoadMore(enableLoadMore)

        pullRefreshLayout?.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++

                onUpdateData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                onUpdateData()
            }

        })
    }


    override fun close() {

    }


    abstract fun initLayoutManager(): RecyclerView.LayoutManager

    abstract fun initBaseRecyclerAdapter(): BaseRecycleAdapter<*>

    abstract fun onUpdateData()


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