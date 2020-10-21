package com.goodjia

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import anylife.scrolltextview.ScrollTextView

class ScrollTextSurfaceView(context: Context?, attrs: AttributeSet?) : ScrollTextView(context, attrs), ScrollListener {
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addScrollListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeScrollListener(this)
    }

    @JvmOverloads
    fun show(content: String? = null, speed: Int? = null, textSize: Int? = null, @ColorInt textColor: Int? = null, @ColorInt bgColor: Int? = null, letterSpacing: Float? = null, playTime: Int? = null, repeatTimes: Int? = null) {
        playTime?.let {
            setScrollTimePeriod(it)
        } ?: repeatTimes?.let {
            setTimes(if (it > 0) it else Int.MAX_VALUE)
        } ?: resetParam()
        speed?.let { this.speed = it }
        bgColor?.let { setScrollTextBackgroundColor(it) }
        textColor?.let { setTextColor(it) }
        textSize?.let { setTextSize(it.toFloat()) }
        letterSpacing?.let { this.letterSpacing = it }
        text = content ?: text.toString()
        visibility = View.VISIBLE
    }

    fun dismiss() {
        post { visibility = View.GONE }
    }

    private fun resetParam() {
        scrollPeriodScheduledFuture?.cancel(true)
        needScrollTimes = Int.MAX_VALUE
        scrollTimePeriod = Int.MIN_VALUE
    }

    override fun onLoopCompletion(count: Int) {}

    override fun onFinished() {
        post { visibility = GONE }
    }
}