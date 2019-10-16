package com.hymnal.alert

import android.content.DialogInterface
import android.view.View

class Alert : DialogInterface {

    override fun dismiss() {
        dismiss?.invoke()
    }

    override fun cancel() {
        cancel?.invoke()
    }

    var title: String? = null

    var msg: String? = null

    var left: String? = null

    var right: String? = null

    var view: View? = null

    var run: ((View) -> Unit)? = null

    var type = Type.NORMAL

    var listener: ((Boolean) -> Unit)? = null

    var cancelable = true

    var dismiss: (() -> Unit)? = null

    var cancel: (() -> Unit)? = null

    fun builder(): androidx.fragment.app.DialogFragment {
        return when (type) {
            Type.IMAGE -> TODO()

            Type.CUSTOM -> custom{
                isCancelable = cancelable
                dialogInterface = this@Alert
            }
            Type.NORMAL, Type.PROGRESS, Type.ERROR, Type.WARNING, Type.SUCCESS  -> normal {
                dimAmount = 0.5f
                isCancelable = cancelable
                dialogInterface = this@Alert
            }
        }
    }

    private fun normal(creation: Normal.() -> Unit) = Normal().apply(creation).let {
        it.creation = {
            when (type) {
                Type.ERROR, Type.SUCCESS, Type.WARNING -> createImageAlertDialog(type, msg, left, right, listener)
                Type.PROGRESS -> createProgressDialog(msg)
                Type.NORMAL -> createAlertDialog(title, msg, left, right, listener)
                else -> throw Exception("")
            }
        }
        it
    }

    private fun custom(creation: Custom.() -> Unit) = Custom().apply(creation).let{
        it.run = run
        it.title = title
        it.viewCustom = view ?: throw Exception("")
        it.left = left ?: "取消"
        it.right = right ?: "确定"
        it.listener = listener
        it.isCancelable = cancelable
        it
    }

    enum class Type {
        SUCCESS,
        ERROR,
        WARNING,
        IMAGE,
        PROGRESS,
        CUSTOM,
        NORMAL

    }
}

fun alert(creation: Alert.() -> Unit) = Alert().apply(creation).let { it.builder() }
