package com.zhkj.smartpolice.widget.dialog

import android.content.Context
import android.view.Gravity
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunny.zy.base.BaseDialog
import com.zhkj.smartpolice.R
import com.zhkj.smartpolice.mine.adapter.DeptTreeAdapter
import com.zhkj.smartpolice.utils.dict.bean.DeptBean
import kotlinx.android.synthetic.main.dialog_mine_dept.*


/**
 * Desc 部门选择对话框
 * Author JoannChen
 * Mail yongzuo.chen@foxmail.com
 * Date 2020年12月7日 11:35:52
 */
class DeptDialog(context: Context) : BaseDialog(context, R.layout.dialog_mine_dept) {

    init {
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)
    }

    var onConfirmBtnListener: ((DeptBean) -> Unit)? = null

    var deptList = ArrayList<DeptBean>()
    var deptBean = DeptBean()


    override fun show() {
        super.show()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = DeptTreeAdapter(deptList).apply {
            setOnItemClickListener { _, position ->
                getData(position).let { bean ->
                    if (bean.open) {
                        bean.open = false
                        list.addAll(position + 1, bean.childrenList)
                    } else {
                        bean.open = true
                        list.removeAll(bean.childrenList)
                    }
                    notifyDataSetChanged()
                    index = position
                }

                deptBean = list[position]
            }
        }

        btn_confirm.setOnClickListener {
            dismiss()
            onConfirmBtnListener?.invoke(deptBean)
        }

        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }

}