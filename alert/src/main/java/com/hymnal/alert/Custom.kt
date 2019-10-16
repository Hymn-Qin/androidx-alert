package com.hymnal.alert

import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI

internal class Custom : DialogFragment() {

    var run: ((View) -> Unit)? = null
    var title: String? = null
    lateinit var viewCustom: View
    var left: String? = null
    var right: String? = null
    var listener: ((Boolean) -> Unit)? = null

    var dialogInterface: DialogInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dm = context!!.resources.displayMetrics
        val width = if (dm.widthPixels < dm.heightPixels) dm.widthPixels else dm.heightPixels
        val height = if ((width / 10) > 16 * 9 ) width / 10 else 16 * 9
        return UI {
            verticalLayout {

                if (!title.isNullOrEmpty()) {
                    textView {
                        gravity = Gravity.CENTER
                        background = context.drawable(R.drawable.dialog_line_dash_btm)
                        text = title
                        textSize = 16f
                        textColor = context.color(R.color.dialog_msg_text_block)
                    }.lparams(width = matchParent, height = height) {
                        marginStart = 20
                        marginEnd = 20
                    }
                }

                verticalLayout {
                    gravity = Gravity.CENTER
                    addView(viewCustom)
                    background = context.drawable(R.drawable.dialog_line_dash_btm)
                    padding = 20
                }.lparams(width = matchParent, height = wrapContent) {
                    marginStart = 20
                    marginEnd = 20
                }
                verticalLayout {
                    orientation = LinearLayout.HORIZONTAL
                    if (!this@Custom.left.isNullOrEmpty() && !this@Custom.right.isNullOrEmpty()) {
                        textView {
                            background = context.drawable(R.drawable.dialog_btn_left_bg_pressed)
                            gravity = Gravity.CENTER
                            setOnClickListener {
                                dismiss()
                                listener?.invoke(false)
                            }

                            text = this@Custom.left
                        }.lparams(width = matchParent, height = height, weight = 1f)

                        view {
                            background = context.drawable(R.drawable.dialog_line_dash_ver)
                        }.lparams(width = 1, height = height - 40) {
                            gravity = Gravity.CENTER
                        }
                    }
                    textView {
                        gravity = Gravity.CENTER
                        background =
                            if (!this@Custom.left.isNullOrEmpty() && !this@Custom.right.isNullOrEmpty()) {
                                context.drawable(R.drawable.dialog_btn_right_bg_pressed)
                            } else {
                                context.drawable(R.drawable.dialog_btn_bottom_bg_pressed)

                            }
                        setOnClickListener {
                            dismiss()
                            listener?.invoke(true)
                        }
                        text = this@Custom.right
                        textColor = context.color(R.color.base_dialog_text)
                    }.lparams(width = matchParent, height = height, weight = 1f)
                }
            }.apply {
                layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
                background = context.drawable(R.drawable.dialog_bg)
            }

        }.view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            setWindowAnimations(R.style.AlertPageAnim)
            //消除白边window.setBackgroundDrawable(BitmapDrawable())
            setBackgroundDrawableResource(android.R.color.transparent)
            val windowParams: WindowManager.LayoutParams = attributes
            val dm = context!!.resources.displayMetrics
            //屏幕状态
            when (context!!.resources.configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> windowParams.width =
                    (dm.heightPixels * 0.8).toInt()
                Configuration.ORIENTATION_PORTRAIT -> windowParams.width =
                    (dm.widthPixels * 0.8).toInt()
            }

            windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            windowParams.dimAmount = 0.5f //遮罩透明度
            attributes = windowParams
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        run?.invoke(view)
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