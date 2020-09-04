package com.zhkj.smartpolice.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView.OnItemClickListener
import android.widget.PopupWindow
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.zhkj.smartpolice.R
import kotlinx.android.synthetic.main.layout_custom_spinner.view.*

/**
 * 自定义Spinner
 */
class CustomSpinner : RelativeLayout {

    var customSpinnerView: View? = null
    var showTextTv: TextView? = null
    var mpopWindow: SpinnerPopWindow<String>? = null
    val textList = ArrayList<String>()

    //设置显示的提示文字
    var showText: String? = null
        private set
    private var onCustomItemCheckedListener: OnCustomItemCheckedListener? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    interface OnCustomItemCheckedListener {
        fun OnCustomItemChecked(position: Int)
    }

    fun setOnCustomItemCheckedListener(onCustomItemCheckedListener: OnCustomItemCheckedListener?) {
        this.onCustomItemCheckedListener = onCustomItemCheckedListener
    }

    fun init() {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        customSpinnerView = inflater.inflate(R.layout.layout_custom_spinner, this)
        showTextTv = custom_msg_tv
//        showTextTv!!.setOnClickListener(clickListener)
        rl_button.setOnClickListener(clickListener)

    }

    //对PopupWindow监听选中用户点击的项
    fun setSelectionItem(position: Int) {
        showTextTv!!.text = textList[position]
    }

    /**
     * 监听popupwindow取消
     */
    val dismissListener =
        PopupWindow.OnDismissListener { setTextImage(R.drawable.svg_left_arrows) }

    /**
     * popupwindow显示的ListView的item点击事件
     */
    val itemClickListener = OnItemClickListener { parent, view, position, id ->
        mpopWindow!!.dismiss()
        showTextTv!!.text = textList[position]
        showText = textList[position]

        //这个接口是用来在其他界面做点击操作的时候负责调用的
        if (onCustomItemCheckedListener != null) {
            onCustomItemCheckedListener!!.OnCustomItemChecked(position)
        }
    }

    /**
     * 显示PopupWindow
     */
    val clickListener =
        OnClickListener { v ->
            when (v.id) {
                R.id.rl_button -> {
                    mpopWindow = SpinnerPopWindow(context, textList, itemClickListener)
                    mpopWindow!!.setOnDismissListener(dismissListener)
                    mpopWindow!!.width = showTextTv!!.width
                    mpopWindow!!.showAsDropDown(showTextTv)
                    setTextImage(R.drawable.svg_down_arrows)
                }
            }
        }

    /**
     * 给TextView右边设置图片
     *
     * @param resId
     */
    fun setTextImage(resId: Int) {
        val drawable = resources.getDrawable(resId)
        drawable.setBounds(
            0,
            0,
            drawable.minimumWidth,
            drawable.minimumHeight
        ) // 必须设置图片大小，否则不显示
        custom_img.setImageDrawable(ContextCompat.getDrawable(context, resId))
//        showTextTv!!.setCompoundDrawables(null, null, drawable, null)
    }

    init {
        init()
    }
}