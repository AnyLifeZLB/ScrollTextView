package com.anylife.fragment.scrolltextview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.goodjia.ScrollListener
import kotlinx.android.synthetic.main.activity_launcher.*
import kotlin.random.Random

/**
 * 跑马灯设置
 */
class LauncherActivity : AppCompatActivity() {
    companion object {
        val TAG: String = LauncherActivity::class.java.simpleName
        const val TEXT_INPUT_KEY = "textInput"
        const val SCROLL_SIZE_KEY = "scrollSize"
        const val SCROLL_SPEED_KEY = "scrollSpeed"
        const val TEXT_COLOR_KEY = "textColor"
        const val TEXT_BG_COLOR_KEY = "textBgColor"
        const val REQUEST_SETTING_CODE = 1
    }

    val randomColor
        get() = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
    private val contentList = listOf(
            "〔即時新聞／綜合報導〕日本官房長官菅義偉在14日當選自民黨總裁，預計將在今日經過國會指名成為日本第99任首相，至於安倍晉三卸任總理後，學者認為安倍晉三可望扮演日本「外交活棋」角色。\n",
            "海軍陸戰隊99旅膠艇7月3日在左營海域進行聯合登陸作戰操演時翻覆，當時造成2死1命危，其中原住民籍中士阿瑪勒．道卡度，受創以來用葉克膜續命。今早突然心律不整，病情急轉直下，家屬同意放棄急救，上午9時40分離開人世。",
            "藝人小鬼（黃鴻升）驟逝，引發眾人不捨，去年才透露要力拚45歲前，還完千萬房貸，未料發生憾事，經查，其北投住家去年1月才以總價4280萬元、單價65.9萬元，買下84坪住家，經查買方黃姓自然人，推測應是小鬼名下。\n",
            "轉換生活型態將大樓產品售出、「升級」成別墅，換取更宜居的居家生活型態。",
            "「這到底是什麼巫術？球竟然可以從這一邊打擊區飛到另一邊，而且球速還高達96 mph！」上週達比修先發對紅人吞下最近七連勝後的首敗，但這顆「噴射球」的超噁軌跡卻讓美國網友瞠目結舌！"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideBottomUIMenu()
        setContentView(R.layout.activity_launcher)
        scrollTextView.run {
            setTimes(1)
            addScrollListener(object : ScrollListener {
                override fun onLoopCompletion(count: Int) {
                    Log.d(TAG, "onLoopCompletion: $count")
                }

                override fun onFinished() {
                    Log.d(TAG, "onFinished: ")
                    postDelayed({
                        val playTime = if (Random.nextBoolean()) null else Random.nextInt(20) + 1
                        val repeatTimes = if (Random.nextBoolean() && playTime != null) null else Random.nextInt(5) + 1
                        Log.d(TAG, "show play time $playTime, repeat time $repeatTimes")
                        show(content = contentList[Random.nextInt(contentList.size)].replace("\n", ""),
                                speed = Random.nextInt(10) + 1,
                                textSize = Random.nextInt(50) + 15,
                                textColor = randomColor,
                                bgColor = randomColor,
                                letterSpacing = Random.nextFloat() + 0.1f, playTime = playTime, repeatTimes = repeatTimes)
                    }, Random.nextInt(10) * 1000L)
                }
            })
        }
        btnSetting.setOnClickListener {
            val intent = Intent(this@LauncherActivity, SettingActivity::class.java)
            intent.putExtra(TEXT_INPUT_KEY, scrollTextView.text)
            intent.putExtra(SCROLL_SIZE_KEY, scrollTextView.textSize)
            intent.putExtra(SCROLL_SPEED_KEY, scrollTextView.speed)
            intent.putExtra(TEXT_COLOR_KEY, scrollTextView.textColor)
            intent.putExtra(TEXT_BG_COLOR_KEY, scrollTextView.backgroundColor)
            startActivityForResult(intent, REQUEST_SETTING_CODE)
        }
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        val v = this.window.decorView
        v.systemUiVisibility = View.GONE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TEXT_INPUT_KEY, scrollTextView.text)
        outState.putFloat(SCROLL_SIZE_KEY, scrollTextView.textSize)
        outState.putInt(SCROLL_SPEED_KEY, scrollTextView.speed)
        outState.putInt(TEXT_COLOR_KEY, scrollTextView.textColor)
        outState.putInt(TEXT_BG_COLOR_KEY, scrollTextView.backgroundColor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        scrollTextView.text = savedInstanceState.getString(TEXT_INPUT_KEY)
        scrollTextView.textSize = savedInstanceState.getFloat(SCROLL_SIZE_KEY)
        scrollTextView.speed = savedInstanceState.getInt(SCROLL_SPEED_KEY)
        scrollTextView.textColor = savedInstanceState.getInt(TEXT_COLOR_KEY)
        scrollTextView.backgroundColor = savedInstanceState.getInt(TEXT_BG_COLOR_KEY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_SETTING_CODE && resultCode == RESULT_OK) {
            data ?: return
            val scrollSpeed = data.getIntExtra(SCROLL_SPEED_KEY, 0)
            if (0 != scrollSpeed) {
                scrollTextView.speed = scrollSpeed
            }
            val scrollSize = data.getFloatExtra(SCROLL_SIZE_KEY, 0f)
            if (0f != scrollSize) {
                scrollTextView.textSize = scrollSize
            }
            val textColor = data.getIntExtra(TEXT_COLOR_KEY, 0)
            if (0 != textColor) {
                scrollTextView.textColor = textColor
            }
            val textBgColor = data.getIntExtra(TEXT_BG_COLOR_KEY, 0)
            if (0 != textColor) {
                scrollTextView.setScrollTextBackgroundColor(textBgColor)
            }
            if (!TextUtils.isEmpty(data.getStringExtra(TEXT_INPUT_KEY))) {
                scrollTextView.text = data.getStringExtra(TEXT_INPUT_KEY)
            }
        }
    }
}