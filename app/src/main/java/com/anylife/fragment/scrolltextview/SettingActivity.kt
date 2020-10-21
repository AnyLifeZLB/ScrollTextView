package com.anylife.fragment.scrolltextview

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.anylife.fragment.scrolltextview.SettingActivity
import com.larswerkman.holocolorpicker.ColorPicker
import com.larswerkman.holocolorpicker.ColorPicker.OnColorChangedListener
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 设置页面
 */
class SettingActivity : AppCompatActivity() {
    private var textColor = 0
    private var textBgColor = 0
    private var scrollSpeed = 5
    private var scrollSize = 20f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomUIMenu()
        setContentView(R.layout.activity_setting)
        scrollTextView.textSize = intent.getFloatExtra(LauncherActivity.SCROLL_SIZE_KEY, scrollSize)
        scrollTextView.speed = intent.getIntExtra(LauncherActivity.SCROLL_SPEED_KEY, scrollSpeed)
        scrollTextView.textColor = intent.getIntExtra(LauncherActivity.TEXT_COLOR_KEY, 0)
        scrollTextView.setScrollTextBackgroundColor(intent.getIntExtra(LauncherActivity.TEXT_BG_COLOR_KEY, 0))
        if (!TextUtils.isEmpty(intent.getStringExtra(LauncherActivity.TEXT_INPUT_KEY))) {
            scrollTextView.text = intent.getStringExtra(LauncherActivity.TEXT_INPUT_KEY)
        }
        btnClose.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.putExtra(LauncherActivity.TEXT_INPUT_KEY, et.text.toString())
            intent.putExtra(LauncherActivity.SCROLL_SIZE_KEY, scrollTextView.textSize)
            intent.putExtra(LauncherActivity.SCROLL_SPEED_KEY, scrollSpeed)
            intent.putExtra(LauncherActivity.TEXT_COLOR_KEY, textColor)
            intent.putExtra(LauncherActivity.TEXT_BG_COLOR_KEY, textBgColor)
            setResult(RESULT_OK, intent)
            finish()
        })
        val textColorView = findViewById<TextView>(R.id.text_color)
        textColorView.setOnClickListener { selectColor(true) }
        val textBgView = findViewById<TextView>(R.id.bg_color)
        textBgView.setOnClickListener { selectColor(false) }
        val textSizeSeekBar = findViewById<SeekBar>(R.id.text_size_seek_bar)
        textSizeSeekBar.progress = scrollTextView.textSize.toInt()
        textSizeSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                scrollTextView.textSize = progress.toFloat()
                scrollSize = progress.toFloat()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        val textSpeedSeekBar = findViewById<SeekBar>(R.id.text_speed_seek_bar)
        textSpeedSeekBar.progress = scrollTextView.getSpeed()
        textSpeedSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                scrollTextView.speed = progress
                scrollSpeed = progress
                Log.d(TAG, "onProgressChanged: speed $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    var mPopWindow: PopupWindow? = null
    private fun selectColor(isSetTextColor: Boolean) {
        val contentView = LayoutInflater.from(this@SettingActivity).inflate(R.layout.color_pop_win, null)
        mPopWindow = PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)

        //显示PopupWindow
        val rootView = LayoutInflater.from(this@SettingActivity).inflate(R.layout.activity_setting, null)
        mPopWindow!!.showAtLocation(rootView, Gravity.CENTER, 0, 0)
        val picker: ColorPicker = contentView.findViewById(R.id.picker)
        val confirm = contentView.findViewById<Button>(R.id.confirm)

        //To get the color
        picker.color
        picker.oldCenterColor = picker.color
        picker.onColorChangedListener = OnColorChangedListener {
            if (isSetTextColor) {
                scrollTextView!!.textColor = picker.color
                textColor = picker.color
            } else {
                textBgColor = picker.color
                scrollTextView!!.setScrollTextBackgroundColor(textBgColor)
            }
        }
        picker.showOldCenterColor = false
        confirm.setOnClickListener { mPopWindow!!.dismiss() }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        val v = this.window.decorView
        v.systemUiVisibility = View.GONE
    }

    companion object {
        private val TAG = SettingActivity::class.java.simpleName
    }
}