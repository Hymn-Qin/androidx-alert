package com.hymnal.alert

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import org.jetbrains.anko.*


fun Context.drawable(@DrawableRes id: Int): Drawable =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        getDrawable(id)
    } else {
        resources.getDrawable(id)
    }

fun Context.color(@ColorRes id: Int) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(id)
    } else {
        resources.getColor(id)
    }


fun Context.createAlertDialog(
    title: String?,
    msg: String?,
    leftString: String?,
    rightString: String?,
    listener: ((Boolean) -> Unit)?
): AlertDialog {

    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val dialog = builder.create()
    dialog.show()

    val dm = this.resources.displayMetrics
    val width = if (dm.widthPixels < dm.heightPixels) dm.widthPixels else dm.heightPixels

    val height = if ((width / 10) > 16 * 9 ) width / 10 else 16 * 9
    val view = this.verticalLayout {

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
        if (!msg.isNullOrEmpty()) {
            textView {
                gravity = Gravity.CENTER
                if (!leftString.isNullOrEmpty() || !rightString.isNullOrEmpty()) {
                    background = context.drawable(R.drawable.dialog_line_dash_btm)
                }
                text = msg
                textSize = 16f
                textColor = context.color(R.color.dialog_msg_text_block)
                padding = 20
            }.lparams(width = matchParent, height = width / 4) {
                marginStart = 20
                marginEnd = 20
            }
        }

        verticalLayout {
            orientation = LinearLayout.HORIZONTAL

            if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                textView {
                    background =
                        context.drawable(R.drawable.dialog_btn_left_bg_pressed)
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        listener?.invoke(false)
                        dialog.dismiss()
                    }

                    text = leftString

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
                    if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                        context.drawable(R.drawable.dialog_btn_right_bg_pressed)
                    } else {
                        context.drawable(R.drawable.dialog_btn_bottom_bg_pressed)

                    }
                setOnClickListener {
                    listener?.invoke(true)
                    dialog.dismiss()
                }

                text = rightString
                textColor = context.color(R.color.base_dialog_text)
            }.lparams(width = matchParent, height = height, weight = 1f)
        }
    }.apply {
        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        background = context.drawable(R.drawable.dialog_bg)
    }

    dialog.setContentView(view)

    return dialog
}

fun Context.createImageAlertDialog(
    type: Alert.Type,
    msg: String?,
    leftString: String?,
    rightString: String?,
    listener: ((Boolean) -> Unit)?
): AlertDialog {

    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val dialog = builder.create()
    dialog.show()

    val dm = this.resources.displayMetrics
    val width = if (dm.widthPixels < dm.heightPixels) dm.widthPixels else dm.heightPixels
    val height = if ((width / 10) > 16 * 9 ) width / 10 else 16 * 9
    val view = this.verticalLayout {

        imageView {
            val id = when (type) {
                Alert.Type.SUCCESS -> R.drawable.success_in
                Alert.Type.ERROR -> R.drawable.error_in
                Alert.Type.WARNING -> R.drawable.sigh_in
                else -> R.drawable.error_in
            }
            setImageResource(id)
            scaleType = ImageView.ScaleType.CENTER
            (drawable as Animatable).start()
        }.lparams(width = matchParent, height = wrapContent) {
            margin = 20
        }

        if (!msg.isNullOrEmpty()) {
            textView {
                gravity = Gravity.CENTER
                if (!leftString.isNullOrEmpty() || !rightString.isNullOrEmpty()) {
                    background = context.drawable(R.drawable.dialog_line_dash_btm)
                }
                text = msg
                textSize = 16f
                textColor = context.color(R.color.dialog_msg_text_block)
                padding = 20
            }.lparams(width = matchParent, height = wrapContent) {
                marginStart = 20
                marginEnd = 20
            }
        }
        if (leftString.isNullOrEmpty() && rightString.isNullOrEmpty()) return@verticalLayout

        verticalLayout {
            orientation = LinearLayout.HORIZONTAL

            if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                textView {
                    background = context.drawable(R.drawable.dialog_btn_left_bg_pressed)
                    gravity = Gravity.CENTER
                    setOnClickListener {
                        listener?.invoke(false)
                        dialog.dismiss()
                    }

                    text = leftString
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
                    if (!leftString.isNullOrEmpty() && !rightString.isNullOrEmpty()) {
                        context.drawable(R.drawable.dialog_btn_right_bg_pressed)
                    } else {
                        context.drawable(R.drawable.dialog_btn_bottom_bg_pressed)

                    }
                setOnClickListener {
                    listener?.invoke(true)
                    dialog.dismiss()
                }

                text = rightString
                textColor = context.color(R.color.base_dialog_text)
            }.lparams(width = matchParent, height = height, weight = 1f)
        }
    }.apply {
        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        background = context.drawable(R.drawable.dialog_bg)
    }

    dialog.setContentView(view)

    return dialog
}

fun Context.createProgressDialog(msg: String?): AlertDialog {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    val dialog = builder.create()
    dialog.show()

    val dm = this.resources.displayMetrics
    val width = if (dm.widthPixels < dm.heightPixels) dm.widthPixels else dm.heightPixels

    val view = this.verticalLayout {

        themedProgressBar(R.style.MyProgressBar) {

        }.lparams(width = matchParent, height = wrapContent) {
            margin = 24

        }

        if (!msg.isNullOrEmpty()) {
            textView {
                gravity = Gravity.CENTER
                text = msg
                textSize = 16f
                textColor = context.color(R.color.dialog_msg_text_block)
                padding = 20
            }.lparams(width = matchParent, height = wrapContent) {
                marginStart = 20
                marginEnd = 20
            }
        }

    }.apply {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutParams = ViewGroup.LayoutParams(matchParent, wrapContent)
        background = context.drawable(R.drawable.dialog_bg)
    }

    dialog.setContentView(view)
    return dialog
}
