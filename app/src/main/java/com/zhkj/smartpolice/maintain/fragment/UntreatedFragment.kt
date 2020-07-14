package com.zhkj.smartpolice.maintain.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.base.BaseFragment
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.frag_untreated.*

class UntreatedFragment: BaseFragment() {
    override fun setLayout(): Int = R.layout.frag_untreated

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun initView() {
        rv_maintain_list.layoutManager = linearLayoutManager
    }

    override fun onClickEvent(view: View) {
    }

    override fun loadData() {
    }

    override fun close() {
    }
}