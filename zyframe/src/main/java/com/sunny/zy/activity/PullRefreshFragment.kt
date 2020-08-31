package com.sunny.zy.activity

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.widget.PullRefreshRecyclerLayout

/**
 * Desc
 * Author 张野
 * Mail zhangye98@foxmail.com
 * Date 2020/6/4 16:05
 */
open class PullRefreshFragment<T> : BaseFragment() {
    var layoutManager: RecyclerView.LayoutManager? = null
    var adapter: BaseRecycleAdapter<T>? = null
    var page = 1
    var loadData: (() -> Unit)? = null
    var enableRefresh: Boolean = true
    var enableLoadMore: Boolean = true

    private val pullRefreshLayout: PullRefreshRecyclerLayout by lazy {
        PullRefreshRecyclerLayout(context)
    }

    override fun setLayout(): Int = 0

    override fun initView() {

        setLayoutView(pullRefreshLayout)
        pullRefreshLayout.setUnEnableRefreshAndLoad(enableRefresh, enableLoadMore)
        pullRefreshLayout.recyclerView.layoutManager = layoutManager ?: LinearLayoutManager(context)
        pullRefreshLayout.recyclerView.adapter = adapter

        pullRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                page++
                loadData?.invoke()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                page = 1
                loadData?.invoke()
            }
        })
    }

    override fun onClickEvent(view: View) {

    }

    override fun loadData() {

    }

    override fun close() {

    }


    fun addData(data: ArrayList<T>) {
        if (page == 1) {
            adapter?.clearData()
            if (data.isEmpty()) {
                pullRefreshLayout.finishRefreshWithNoMoreData()
                pullRefreshLayout.showEmptyView()
            } else {
                pullRefreshLayout.finishRefresh()
                pullRefreshLayout.hideEmptyView()
            }
        } else {
            if (data.isEmpty()) {
                page--
                pullRefreshLayout.finishLoadMoreWithNoMoreData()
            } else {
                pullRefreshLayout.finishLoadMore()
            }
        }
        adapter?.addData(data)
        adapter?.notifyDataSetChanged()
    }


    fun deleteData(index: Int) {
        adapter?.deleteData(index)
    }

    fun deleteData(data: T) {
        adapter?.deleteData(data)
    }

    fun getAllData() = adapter?.getAllData()
}