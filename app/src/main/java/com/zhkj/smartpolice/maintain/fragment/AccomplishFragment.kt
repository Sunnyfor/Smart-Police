package com.zhkj.smartpolice.maintain.fragment

import android.content.Intent
import android.view.View
import android.view.animation.Animation
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.activity.PullRefreshFragment
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.LogUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.Constant
import com.zhkj.smartpolice.maintain.activity.AdministratorActivity
import com.zhkj.smartpolice.maintain.adapter.MaintainTaskInfoAdapter
import com.zhkj.smartpolice.maintain.bean.MaintainTaskBean
import com.zhkj.smartpolice.maintain.presenter.MaintainPresenter
import com.zhkj.smartpolice.maintain.view.IMaintainView
import kotlinx.android.synthetic.main.frag_untreated.*


class AccomplishFragment : BaseFragment(), IMaintainView {
    private val maintainTaskInfo = ArrayList<MaintainTaskBean>()
    private val pullRefreshFragment = PullRefreshFragment<MaintainTaskBean>()
    private var isGetData: Boolean = false

    private val maintainPresenter: MaintainPresenter by lazy {
        MaintainPresenter(this)
    }

    private val adapter: MaintainTaskInfoAdapter by lazy {
        MaintainTaskInfoAdapter(maintainTaskInfo, true).apply {
            setOnItemClickListener { _, position ->
                var intent = Intent(requireContext(), AdministratorActivity::class.java)
                intent.putExtra("applyContent", getData(position).applyContent)
                intent.putExtra("petitioner", getData(position).petitioner)
                intent.putExtra("petitionerPhone", getData(position).petitionerPhone)
                intent.putExtra("deptName", getData(position).deptName)
                intent.putExtra("applyDate", getDate(getData(position).applyDate))
                intent.putExtra("repairType",getData(position).repairType)
                intent.putExtra("isType", false)
                getData(position).shopGoodsEntityList?.let {
                    intent.putExtra("goodsName", it[0].goodsName)
                }
                intent.putExtra("attachmentGroupId",getData(position).attachmentGroupId)
                startActivityForResult(intent, Constant.MAINTAIN_CONTENT_ANSWER)
            }
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

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter && !isGetData) {
            isGetData = true
            loadData()
        } else {
            isGetData = false
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onPause() {
        super.onPause()
        isGetData = false
    }
}