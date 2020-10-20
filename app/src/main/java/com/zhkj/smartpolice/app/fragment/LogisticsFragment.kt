package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.sunny.zy.ZyFrameStore
import com.sunny.zy.base.BaseFragment
import com.sunny.zy.utils.LogUtil
import com.sunny.zy.utils.ToastUtil
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.app.data.MerchantViewModel
import com.zhkj.smartpolice.base.UserManager
import com.zhkj.smartpolice.drugstore.DrugstoreActivity
import com.zhkj.smartpolice.haircut.BarberListActivity
import com.zhkj.smartpolice.haircut.HaircutOrderDetailActivity
import com.zhkj.smartpolice.haircut.activity.AgencyHaircutSelectActivity
import com.zhkj.smartpolice.laundry.activity.LaundryApplyActivity
import com.zhkj.smartpolice.maintain.activity.ApplyMaintainListActivity
import com.zhkj.smartpolice.maintain.activity.MaintainTaskActivity
import com.zhkj.smartpolice.maintain.activity.PoliceMaintainActivity
import com.zhkj.smartpolice.maintain.activity.PropertyManageActivity
import com.zhkj.smartpolice.meal.DiningRoomActivity
import com.zhkj.smartpolice.meal.activity.MealRecordActivity
import com.zhkj.smartpolice.merchant.MerchantBean
import com.zhkj.smartpolice.merchant.MerchantListActivity
import com.zhkj.smartpolice.merchant.model.MerchantContract
import com.zhkj.smartpolice.merchant.model.MerchantPresenter
import com.zhkj.smartpolice.mine.activity.ConsumeRecordActivity
import com.zhkj.smartpolice.mine.activity.RepairRecordActivity
import com.zhkj.smartpolice.mine.activity.ReserveRecordActivity
import com.zhkj.smartpolice.notice.NoticeActivity
import com.zhkj.smartpolice.notice.bean.NoticeBean
import com.zhkj.smartpolice.notice.contract.NoticeContract
import com.zhkj.smartpolice.notice.presenter.NoticePresenter
import com.zhkj.smartpolice.physiotherapy.activity.PhysiotherapyActivity
import com.zhkj.smartpolice.shuttle.ShuttleBusActivity
import com.zhkj.smartpolice.stadium.StadiumActivity
import com.zhkj.smartpolice.widget.TextSwitcherAnimation
import kotlinx.android.synthetic.main.frag_logistics.*
import kotlinx.coroutines.cancel


class LogisticsFragment : BaseFragment(), MerchantContract.IMerchantListView, NoticeContract.IAnnouncementView {

    private var textList = ArrayList<String>()

    private val merchantViewModel: MerchantViewModel by lazy {
        ViewModelProvider(getBaseActivity()).get(MerchantViewModel::class.java)
    }

    private val merchantPresenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    private val noticePresenter: NoticePresenter by lazy {
        NoticePresenter(this)
    }

    override fun setLayout(): Int = R.layout.frag_logistics

    override fun initView() {

        getBaseActivity().simpleTitle("后勤")

        // 跑马灯效果
        textSwitcher.setFactory {
            val tv = TextView(requireContext())
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimensionPixelSize(R.dimen.sp_12).toFloat())
            tv
        }

        setOnClickListener(
            tv_restaurant, tv_haircut, tv_drugstore, tv_shuttle_bus,
            tv_laundry, tv_stadium, tv_maintain, tv_physical_therapy,
            ll_meal, ll_repair, ll_reserve, ll_consume, ll_notice,
            tv_office_supplies, tv_vehicle_apply
        )
    }

    override fun onClickEvent(view: View) {
        when (view.id) {
            tv_restaurant.id -> {
//            startActivity(Intent(requireContext(), MealChoiceActivity::class.java))
                merchantViewModel.list.find { it.shopType == MerchantListActivity.TYPE_RESTAURANT }
                    .apply {
                        DiningRoomActivity.intent(requireContext(), this?.shopId)
                    }
            }
            tv_haircut.id -> {
                merchantViewModel.list.find { it.shopType == MerchantListActivity.TYPE_HAIRCUT }
                    .apply {
                        if (UserManager.getUserBean().leaderId != null) {
                            val intent = Intent(requireContext(), AgencyHaircutSelectActivity::class.java)
                            intent.putExtra("shopId", this?.shopId)
                            startActivity(intent)
                        } else {
                            val intent = when (UserManager.getUserBean().position) {
                                "1", "2" -> Intent(requireContext(), BarberListActivity::class.java)      //领导
                                else -> Intent(requireContext(), HaircutOrderDetailActivity::class.java)  //警员
                            }
                            intent.putExtra("shopId", this?.shopId)
                            startActivity(intent)
                        }
                    }
            }
            tv_drugstore.id -> {
                merchantViewModel.list.find { it.shopType == MerchantListActivity.TYPE_DRUGSTORE }
                    .apply {
                        DrugstoreActivity.intent(requireContext(), this?.shopId)
                    }
            }
            tv_shuttle_bus.id -> {
                ShuttleBusActivity.intent(requireContext(), MerchantListActivity.TYPE_SHUTTLE_BUS)
            }
            tv_laundry.id -> {
                merchantViewModel.list.find { it.shopType == MerchantListActivity.TYPE_LAUNDRY }
                    .apply {
                        val intent = Intent(requireContext(), LaundryApplyActivity::class.java)
                        intent.putExtra("shopId", this?.shopId)
                        intent.putExtra("selfQuota", this?.selfQuota)
                        startActivity(intent)
                    }
            }
            tv_stadium.id -> {
                merchantViewModel.list.find { it.shopType == MerchantListActivity.TYPE_STADIUM }
                    .apply {
                        val intent = Intent(requireContext(), StadiumActivity::class.java)
                        intent.putExtra("shopId", this?.shopId)
                        startActivity(intent)
                    }
            }
            tv_maintain.id -> {
                val userInfoBean = UserManager.getInfo()
                LogUtil.i("进来人的身份=======${userInfoBean.roleId} ${userInfoBean.roleName}")
                when (userInfoBean.roleId) {
                    3 -> startActivity(Intent(requireContext(), PoliceMaintainActivity::class.java)) //普通警员
                    117 -> startActivity(Intent(requireContext(), ApplyMaintainListActivity::class.java)) //维修管理员
                    115 -> startActivity(Intent(requireContext(), PropertyManageActivity::class.java)) //物业管理
                    116 -> startActivity(Intent(requireContext(), MaintainTaskActivity::class.java)) //维修工人
//                    else -> Intent(requireContext(), PoliceMaintainActivity::class.java)
                }
            }
            //理疗
            tv_physical_therapy.id -> {
                merchantViewModel.list.find { it.shopType == MerchantListActivity.TYPE_PHYSIOTHERAPY }
                    .apply {
                        val intent = Intent(requireContext(), PhysiotherapyActivity::class.java)
                        intent.putExtra("shopId", this?.shopId)
                        startActivity(intent)
                    }
            }

            ll_meal.id -> startActivity(Intent(requireContext(), MealRecordActivity::class.java))
            ll_repair.id -> startActivity(Intent(requireContext(), RepairRecordActivity::class.java))
            ll_reserve.id -> startActivity(Intent(requireContext(), ReserveRecordActivity::class.java))
            ll_consume.id -> startActivity(Intent(requireContext(), ConsumeRecordActivity::class.java))
            ll_notice.id -> startActivity(Intent(requireContext(), NoticeActivity::class.java))

            tv_office_supplies.id -> ToastUtil.show()
            tv_vehicle_apply.id -> ToastUtil.show()
        }
    }

    override fun loadData() {
        if (merchantViewModel.list.isEmpty()) {
            merchantPresenter.loadMerchantList("1", "") //默认加载全部商家
        }

        noticePresenter.loadAnnouncementList()
    }

    override fun close() {
        merchantPresenter.cancel()
        noticePresenter.cancel()
    }

    override fun showMerchantList(data: ArrayList<MerchantBean>) {
        merchantViewModel.list = data
        ZyFrameStore.setData("merchantList", data)
    }

    override fun loadAnnouncementList(data: ArrayList<NoticeBean>) {
        if (data.size > 0) {
            textList.clear()
            data.forEachIndexed { index, noticeHornInfo ->
                textList.add("${index + 1}. ${noticeHornInfo.noticeValue}")
            }
            TextSwitcherAnimation(textSwitcher, textList, false).create()
            textSwitcher.setOnClickListener(this)
        } else {
            ll_announcement.visibility = View.GONE
        }
    }

}