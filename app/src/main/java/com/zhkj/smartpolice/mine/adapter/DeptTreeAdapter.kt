package com.zhkj.smartpolice.mine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sunny.zy.base.BaseRecycleAdapter
import com.sunny.zy.base.BaseRecycleViewHolder
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.utils.dict.bean.DeptBean
import kotlinx.android.synthetic.main.item_dept_tree.view.*

/**
 * Desc 好友列表、群组列表分组
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020年8月11日 15:16:40
 */
class DeptTreeAdapter(list: ArrayList<DeptBean>) : BaseRecycleAdapter<DeptBean>(list) {

    var index = 0

    override fun setLayout(parent: ViewGroup, viewType: Int): View {
        return LayoutInflater.from(context).inflate(R.layout.item_dept_tree, parent, false)
    }

    override fun onBindViewHolder(holder: BaseRecycleViewHolder, position: Int) {

        holder.itemView.tv_name.text = getData(position).name

        val fontColor = if (position == index) R.color.color_theme else R.color.color_black
        holder.itemView.tv_name.setTextColor(ContextCompat.getColor(context, fontColor))

        setMargin(holder.itemView.tv_name, getData(position).listLevel)
    }

    private fun setMargin(textView: TextView, level: Int) {

        val levelMargin = when (level) {
            1 -> R.dimen.dp_10
            2 -> R.dimen.dp_20
            3 -> R.dimen.dp_30
            else -> R.dimen.dp_10
        }
        (textView.layoutParams as RelativeLayout.LayoutParams).leftMargin = context.resources.getDimension(levelMargin).toInt()
    }

}