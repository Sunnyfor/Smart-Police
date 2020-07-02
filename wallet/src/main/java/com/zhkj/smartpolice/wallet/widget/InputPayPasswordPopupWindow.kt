package com.zhkj.smartpolice.wallet.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.popup_input_pay_password.view.*

@SuppressLint("InflateParams")
class InputPayPasswordPopupWindow(
    context: Context,
    title: String? = context.getString(R.string.pay_password),
    hint: String? = context.getString(R.string.pay_password_hint),
    onInputComplete: ((popupWindow: InputPayPasswordPopupWindow, payPassword: String) -> Unit)? = null
) : PopupWindow(context) {

    val view: View by lazy {
        LayoutInflater.from(context).inflate(R.layout.popup_input_pay_password, null)
    }

    private val numberViewList = arrayListOf<TextView>()
    private val passwordList = arrayListOf<String>()

    init {
        contentView = view
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        //设置SelectPicPopupWindow弹出窗体可点击
        isFocusable = true
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        view.tv_title.text = title
        view.tv_hint.text = hint
        view.recyclerview.layoutManager = GridLayoutManager(context, 3)
        view.recyclerview.addItemDecoration(InputPayPasswordItemDecoration(context))

        val numberList = arrayListOf(
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "C", "0", "←"
        )
        numberViewList.add(view.tv_number1)
        numberViewList.add(view.tv_number2)
        numberViewList.add(view.tv_number3)
        numberViewList.add(view.tv_number4)
        numberViewList.add(view.tv_number5)
        numberViewList.add(view.tv_number6)

        view.recyclerview.adapter = object : BaseRecycleAdapter<String>(numberList) {
            override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {
                holder.getView<TextView>(R.id.tv_name).text = getData(position)
            }

            override fun setLayout(parent: ViewGroup, viewType: Int): View {
                val frameLayout = FrameLayout(context)
                if (viewType != 0) {
                    frameLayout.setBackgroundResource(viewType)
                }
                val textView = TextView(context)
                textView.id = R.id.tv_name
                textView.setTextColor(ContextCompat.getColor(context, R.color.color_black))
                textView.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.sp_20)
                )
                textView.gravity = Gravity.CENTER
                frameLayout.addView(
                    textView, FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        context.resources.getDimension(R.dimen.dp_50).toInt()
                    )
                )
                return frameLayout
            }

            override fun getItemViewType(position: Int): Int {
                if (getData(position) == "C" || getData(position) == "←") {
                    return R.color.color_gray
                }
                return 0
            }
        }.apply {
            setOnItemClickListener { _, position ->
                val dataStr = getData(position)

                if (getItemViewType(position) != 0) {
                    if (dataStr == "C" && passwordList.isNotEmpty()) {
                        passwordList.clear()
                        numberViewList.forEach {
                            it.text = ""
                        }

                        return@setOnItemClickListener
                    }


                    if (dataStr == "←" && passwordList.isNotEmpty()) {
                        val index = passwordList.lastIndex
                        passwordList.removeAt(index)
                        numberViewList[index].text = ""
                        return@setOnItemClickListener
                    }
                } else {

                    if (passwordList.size != numberViewList.size) {
                        passwordList.add(dataStr)
                        numberViewList[passwordList.size - 1].text = "●"
                    }
                    if (passwordList.size == numberViewList.size) {

                        val passwordSb = StringBuilder()
                        passwordList.forEach {
                            passwordSb.append(it)
                        }
                        onInputComplete?.invoke(
                            this@InputPayPasswordPopupWindow,
                            passwordSb.toString()
                        )
                    }
                }
            }
        }
    }
}