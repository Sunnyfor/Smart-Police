package com.zhkj.smartpolice.maintain.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.maintain.adapter.MaintainTaskInfoAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainTaskBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.frag_untreated.*


class AccomplishFragment: BaseFragment(), IMaintainView {
    private val maintainTaskInfo = ArrayList<MaintainTaskBean>()
    private val pullRefreshFragment = PullRefreshFragment<MaintainTaskBean>()

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainTaskInfoAdapter by lazy {
        MaintainTaskInfoAdapter(maintainTaskInfo, true).apply {
//            setOnItemClickListener { _, position ->
//                var intent = Intent(requireContext(), MaintainTaskInfoActivity::class.java)
//                var bundle = Bundle()
//                bundle.putSerializable("info",getData(position))
//                intent.putExtras(bundle)
//                startActivityForResult(intent, Constant.MAINTAIN_CONTENT_ANSWER)
//            }
        }
    }

    override fun setLayout(): Int = R.layout.frag_untreated

    override fun initView() {
        pullRefreshFragment.layoutManager = LinearLayoutManager(requireContext())
        pullRefreshFragment.adapter = adapter
        pullRefreshFragment.loadData = {
            loadData()
        }

        childFragmentManager.beginTransaction().replace(fl_maintain_list.id, pullRefreshFragment).commit()
    }

    override fun onClickEvent(view: View) {
    }

    override fun loadData() {
        maintainPresenter.onMaintainTask(Constant.MAINTAIN_TASK_FINISH, pullRefreshFragment.page.toString())
    }

    override fun close() {
    }

    override fun onMaintainTask(maintainTaskBean: ArrayList<MaintainTaskBean>) {
        pullRefreshFragment.addData(maintainTaskBean)
        LogUtil.i("物业管理员返回的参数===========$maintainTaskBean")
    }

}