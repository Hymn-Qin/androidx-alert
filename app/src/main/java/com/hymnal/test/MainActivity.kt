package com.hymnal.test

import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hymnal.alert.Alert
import com.hymnal.alert.alert
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start.setOnClickListener {
            val a = error.drawable as Animatable
            a.start()

            val star = star.drawable as Animatable
            star.start()

            alert {
                title = "权限说明"
                msg = "该应用需要访问存储权限，现在去设置"
//                left = "取消"
                right = "知道了"
                cancelable = true
                type = Alert.Type.NORMAL
                listener = {

                }
            }.show(supportFragmentManager, "NORMAL")
        }

    }
}
