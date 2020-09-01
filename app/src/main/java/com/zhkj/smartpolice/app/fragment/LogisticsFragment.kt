package com.zhkj.smartpolice.app.fragment

import android.content.Intent
import android.view.View
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
import com.zhkj.smartpolice.mine.activity.RepairRecordActivity
import com.zhkj.smartpolice.mine.activity.ReserveRecordActivity
import com.zhkj.smartpolice.notice.ConsumeRecordActivity
import com.zhkj.smartpolice.physiotherapy.activity.PhysiotherapyActivity
import com.zhkj.smartpolice.shuttle.ShuttleBusActivity
import com.zhkj.smartpolice.stadium.StadiumActivity
import kotlinx.android.synthetic.main.frag_logistics.*
import kotlinx.coroutines.cancel


class LogisticsFragment : BaseFragment(), MerchantContract.IMerchantListView {

    private val merchantViewModel: MerchantViewModel by lazy {
        ViewModelProvider(getBaseActivity()).get(MerchantViewModel::class.java)
    }

    private val presenter: MerchantPresenter by lazy {
        MerchantPresenter(this)
    }

    override fun setLayout(): Int = R.layout.frag_logistics

    override fun initView() {

        getBaseActivity().simpleTitle("后勤")

        setOnClickListener(
            tv_restaurant, tv_haircut, tv_drugstore, tv_shuttle_bus,
            tv_laundry, tv_stadium, tv_maintain, tv_physical_therapy,
            ll_meal, ll_repair, ll_reserve, ll_consume,
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
                        val intent = when (UserManager.getUserBean().position) {
                            "1", "2" -> Intent(
                                requireContext(),
                                BarberListActivity::class.java
                            )      //领导
                            else -> Intent(
                                requireContext(),
                                HaircutOrderDetailActivity::class.java
                            )  //警员
                        }
                        intent.putExtra("shopId", this?.shopId)
                        startActivity(intent)
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
                    3 -> startActivity(
                        Intent(
                            requireContext(),
                            PoliceMaintainActivity::class.java
                        )
                    ) //普通警员
                    117 -> startActivity(
                        Intent(
                            requireContext(),
                            ApplyMaintainListActivity::class.java
                        )
                    ) //维修管理员
                    115 -> startActivity(
                        Intent(
                            requireContext(),
                            PropertyManageActivity::class.java
                        )
                    ) //物业管理
                    116 -> startActivity(
                        Intent(
                            requireContext(),
                            MaintainTaskActivity::class.java
                        )
                    ) //维修工人
                    else -> ToastUtil.show("你当前不是警员")
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
//                ToastUtil.show()
            }

            ll_meal.id -> startActivity(Intent(requireContext(), MealRecordActivity::class.java))
            ll_repair.id -> startActivity(
                Intent(
                    requireContext(),
                    RepairRecordActivity::class.java
                )
            )
            ll_reserve.id -> startActivity(
                Intent(
                    requireContext(),
                    ReserveRecordActivity::class.java
                )
            )
            ll_consume.id -> startActivity(
                Intent(
                    requireContext(),
                    ConsumeRecordActivity::class.java
                )
            )

            tv_office_supplies.id -> ToastUtil.show()
            tv_vehicle_apply.id -> ToastUtil.show()
        }
    }

    override fun loadData() {
        if (merchantViewModel.list.isEmpty()) {
            presenter.loadMerchantList("1", "") //默认加载全部商家
        }
    }

    override fun close() {
        presenter.cancel()
    }

    override fun showMerchantList(data: ArrayList<MerchantBean>) {
        merchantViewModel.list = data
        ZyFrameStore.setData("merchantList", data)
    }

}