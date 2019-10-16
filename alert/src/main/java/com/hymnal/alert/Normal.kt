package com.hymnal.alert

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.view.WindowManager

internal class Normal : DialogFragment() {

    lateinit var creation: (Context.() -> Dialog)

    var dialogInterface: DialogInterface? = null
    var dimAmount = 0.5f

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return creation.let { context!!.it() }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setWindowAnimations(R.style.AlertPageAnim)
            //消除白边window.setBackgroundDrawable(BitmapDrawable())

            setBackgroundDrawableResource(android.R.color.transparent)
            val windowParams: WindowManager.LayoutParams = attributes
            val dm = context!!.resources.displayMetrics
            //屏幕状态
            when (context!!.resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> windowParams.width = (dm.heightPixels * 0.78).toInt()
                Configuration.ORIENTATION_PORTRAIT -> windowParams.width = (dm.widthPixels * 0.78).toInt()
            }

            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            windowParams.dimAmount = dimAmount //遮罩透明度
            attributes = windowParams
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dialogInterface?.cancel()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dialogInterface?.dismiss()
    }
}