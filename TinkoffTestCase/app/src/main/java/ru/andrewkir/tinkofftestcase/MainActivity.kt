package ru.andrewkir.tinkofftestcase

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun hideButtons() {
        val button1 = findViewById<Button>(R.id.button)
        button1.visibility = View.GONE
        val button2 = findViewById<Button>(R.id.button2)
        button2.visibility = View.GONE
    }

    fun showButtons() {
        val button1 = findViewById<Button>(R.id.button)
        button1.visibility = View.VISIBLE
        val button2 = findViewById<Button>(R.id.button2)
        button2.visibility = View.VISIBLE
    }
}