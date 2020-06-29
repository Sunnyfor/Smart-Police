package com.zhkj.smartpolice.util

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import com.zhkj.smartpolice.R

/**
 * 自定义PopupWindow
 */
class SpinnerPopWindow<T>(
    context: Context?,
    list: List<T>,
    clickListener: OnItemClickListener
) : PopupWindow(context) {
    val inflater: LayoutInflater
    var mListView: ListView? = null
    val list: List<T>
    private var popAdapter: PopUpAdapter? = null
    fun init(clickListener: OnItemClickListener) {
        val view = inflater.inflate(R.layout.popup_spiner_window_layout, null)
        contentView = view
        width = LinearLayout.LayoutParams.WRAP_CONTENT
        height = LinearLayout.LayoutParams.WRAP_CONTENT
        isFocusable = true
        val dw = ColorDrawable(0x00)
        setBackgroundDrawable(dw)
        mListView =
            view.findViewById<View>(R.id.popup_listview) as ListView
        mListView!!.adapter = PopUpAdapter().also { popAdapter = it }
        mListView!!.onItemClickListener = clickListener
    }

    inner class PopUpAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): T {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View? {
            var mConvertView = convertView
            val viewHolder: ViewHolder
            if (mConvertView == null) {
                viewHolder = ViewHolder()
                mConvertView = inflater.inflate(R.layout.popup_up_spinner_layout_item, null)
                viewHolder.textTv =
                    mConvertView.findViewById<View>(R.id.pop_up_item_text_tv) as TextView
                mConvertView.tag = viewHolder
            } else {
                viewHolder = mConvertView.tag as SpinnerPopWindow<T>.ViewHolder
            }
            viewHolder.textTv!!.text = getItem(position).toString()
            return mConvertView
        }
    }

    inner class ViewHolder {
        var textTv: TextView? = null
    }

    init {
        inflater = LayoutInflater.from(context)
        this.list = list
        init(clickListener)
    }
}