package com.zhkj.wallet.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zhkj.wallet.R

class InputPayPasswordItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val mPaint: Paint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, R.color.color_line)
            style = Paint.Style.FILL
        }
    }
    private val mDividerHeight: Int by lazy {
        context.resources.getDimension(R.dimen.dp_1).toInt()
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }


    private fun drawHorizontal(c: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val left = child.left - params.leftMargin
            val right = child.right + params.rightMargin + mDividerHeight
            val top = child.top + mDividerHeight
            val bottom = top + mDividerHeight
            c.drawRect(
                left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint
            )
        }
    }


    private fun drawVertical(c: Canvas, parent: RecyclerView) {

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = left + mDividerHeight
            c.drawRect(
                left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint
            )
        }
    }
}