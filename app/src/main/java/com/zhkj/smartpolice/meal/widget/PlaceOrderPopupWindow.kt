package com.zhkj.smartpolice.meal.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.meal.adapter.MealOrderAdapter
import com.zhkj.smartpolice.meal.bean.MealGoodsBean
import kotlinx.android.synthetic.main.popup_place_order.view.*

class PlaceOrderPopupWindow(context: Context, list: ArrayList<MealGoodsBean>) :
    PopupWindow(context) {

    init {
        animationStyle = R.style.popWindow_animation
        contentView = View.inflate(context, R.layout.popup_place_order, null)
        setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(context, R.color.color_white)))
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = context.resources.getDimension(R.dimen.dp_350).toInt()
        isOutsideTouchable = true
        isFocusable = true

        contentView.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MealOrderAdapter(list)
            itemAnimator?.changeDuration = 0;
        }

        contentView.ll_clear.setOnClickListener {
            contentView.recyclerView.adapter = MealOrderAdapter(arrayListOf())
        }

        contentView.btn_commit.setOnClickListener {
//            context.startActivity(Intent(context, PaymentActivity::class.java))
        }
    }
}