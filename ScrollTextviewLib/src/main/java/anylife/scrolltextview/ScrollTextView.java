package anylife.scrolltextview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Android auto Scroll Text,like TV News,AD devices
 * <p>
 * NEWEST LOG :
 * 1.setText() immediately take effect (v1.3.6)
 * 2.support scroll forever            (v1.3.7)
 * <p>
 * Basic knowledge：https://www.jianshu.com/p/918fec73a24d
 *
 * @author anylife.zlb@gmail.com  2013/09/02
 */
public class ScrollTextView extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG = "ScrollTextView";
    // surface Handle onto a raw buffer that is being managed by the screen compositor.
    private SurfaceHolder surfaceHolder;   //providing access and control over this SurfaceView's underlying surface.

    private Paint paint = null;
    private boolean stopScroll = false;          // stop scroll
    private boolean pauseScroll = false;         // pause scroll

    //Default value
    private boolean clickEnable = false;    // click to stop/start
    public boolean isHorizontal = true;     // horizontal｜V
    private int speed = 1;                  // scroll-speed
    private String text = "";               // scroll text
    private float textSize = 20f;           // default text size
    private int textColor;

    private int needScrollTimes = Integer.MAX_VALUE;      //scroll times

    private int viewWidth = 0;
    private int viewHeight = 0;
    private float textWidth = 0f;
    private float textX = 0f;
    private float textY = 0f;
    private float viewWidth_plus_textLength = 0.0f;

    private ScheduledExecutorService scheduledExecutorService;

    boolean isSetNewText = false;
    boolean isScrollForever = true;

    /**
     * constructs 1
     *
     * @param context you should know
     */
    public ScrollTextView(Context context) {
        super(context);
    }

    /**
     * constructs 2
     *
     * @param context CONTEXT
     * @param attrs   ATTRS
     */
    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = this.getHolder();  //get The surface holder
        surfaceHolder.addCallback(this);
        paint = new Paint();
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollTextView);
        clickEnable = arr.getBoolean(R.styleable.ScrollTextView_clickEnable, clickEnable);
        isHorizontal = arr.getBoolean(R.styleable.ScrollTextView_isHorizontal, isHorizontal);
        speed = arr.getInteger(R.styleable.ScrollTextView_speed, speed);
        text = arr.getString(R.styleable.ScrollTextView_text);
        textColor = arr.getColor(R.styleable.ScrollTextView_text_color, Color.BLACK);
        textSize = arr.getDimension(R.styleable.ScrollTextView_text_size, textSize);
        needScrollTimes = arr.getInteger(R.styleable.ScrollTextView_times, Integer.MAX_VALUE);
        isScrollForever = arr.getBoolean(R.styleable.ScrollTextView_isScrollForever, true);

        paint.setColor(textColor);
        paint.setTextSize(textSize);

        setZOrderOnTop(true);  //Control whether the surface view's surface is placed on top of its window.
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        setFocusable(true);

        arr.recycle();
    }

    /**
     * measure text height width
     *
     * @param widthMeasureSpec  widthMeasureSpec
     * @param heightMeasureSpec heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        onMyMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    /**
     * 测量
     */
    private void onMyMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mHeight = getFontHeight(textSize);  //实际的视图高
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);

        // when layout width or height is wrap_content ,should init ScrollTextView Width/Height
        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, mHeight);
            viewHeight = mHeight;
        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, viewHeight);
        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            setMeasuredDimension(viewWidth, mHeight);
            viewHeight = mHeight;
        }
    }


    /**
     * surfaceChanged
     *
     * @param arg0 arg0
     * @param arg1 arg1
     * @param arg2 arg1
     * @param arg3 arg1
     */
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        Log.d(TAG, "arg0:" + arg0.toString() + "  arg1:" + arg1 + "  arg2:" + arg2 + "  arg3:" + arg3);
    }

    /**
     * surfaceCreated,init a new scroll thread.
     * lockCanvas
     * Draw somthing
     * unlockCanvasAndPost
     *
     * @param holder holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        stopScroll = false;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTextThread(), 100, 100, TimeUnit.MILLISECONDS);
        Log.d(TAG, "ScrollTextTextView is created");
    }

    /**
     * surfaceDestroyed
     *
     * @param arg0 SurfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        stopScroll = true;
        scheduledExecutorService.shutdownNow();
        Log.d(TAG, "ScrollTextTextView is destroyed");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /**
     * text height
     *
     * @param fontSize fontSize
     * @return fontSize`s height
     */
    private int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }

    public int getViewHeight(){

        return viewHeight;
    }

    /**
     * set scroll times
     *
     * @param times scroll times
     */
    public void setTimes(int times) {
        if (times <= 0) {
            throw new IllegalArgumentException("times was invalid integer, it must between > 0");
        } else {
            needScrollTimes = times;
            isScrollForever = false;
        }
    }


    /**
     * set scroll text size
     *
     * @param textSize scroll times
     */
    public void setTextSize(int textSize) {
        if (textSize < 15) {
            throw new IllegalArgumentException("textSize must  > 15");
        } else if (textSize > 100) {
            throw new IllegalArgumentException("textSize must  < 100");
        } else {
            //重新设置Size
            paint.setTextSize(sp2px(getContext(), textSize));
            //试图区域也要改变
            measureVarious();
            isSetNewText = true;

            int mHeight = getFontHeight(textSize);  //实际的视图高
            // when layout width or height is wrap_content ,should init ScrollTextView Width/Height
            if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                setMeasuredDimension(viewWidth, mHeight);
                viewHeight = mHeight;
            } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                setMeasuredDimension(viewWidth, viewHeight);
            } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                setMeasuredDimension(viewWidth, mHeight);
                viewHeight = mHeight;
            }
            requestLayout();
            invalidate();
        }
    }



    /**
     * Set the default text size to a given unit and value. See {@link
     * TypedValue} for the possible dimension units.
     *
     * <p>Note: if this TextView has the auto-size feature enabled than this function is no-op.
     *
     * @param size The desired size in the given units.
     * @attr ref android.R.styleable#TextView_textSize
     */
    public void setTextSize(float size) {
        setTextSizeInternal(TypedValue.COMPLEX_UNIT_SP, size, true /* shouldRequestLayout */);
    }

    private void setTextSizeInternal(int unit, float size, boolean shouldRequestLayout) {
        Context c = getContext();
        Resources r;

        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }

        setRawTextSize(TypedValue.applyDimension(unit, size, r.getDisplayMetrics()),
                shouldRequestLayout);
    }

    private void setRawTextSize(float size, boolean shouldRequestLayout) {
        if (size != paint.getTextSize()) {
            paint.setTextSize(size);

//            if (shouldRequestLayout && mLayout != null) {
//                nullLayouts();
//                requestLayout();
//                invalidate();
//            }
        }
    }


//    /**
//     *
//     */
//    private void setViewSize(ImageView view) {
//        MarginLayoutParams margin = new MarginLayoutParams(view.getLayoutParams());
//        int dpTop = dp2px( 10);
//        int dpRight = dp2px( 10);
//        int dpLeft=dp2px( 10);
//
//        margin.setMargins(dpLeft, dpTop, dpRight, 0);
//
//        DisplayMetrics metric = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metric);
//
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
//        layoutParams.width = 500;
//        layoutParams.height= 400;
//        view.setLayoutParams(layoutParams);
//    }


    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * isHorizontal or vertical
     *
     * @param horizontal isHorizontal or vertical
     */
    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    /**
     * set scroll text
     *
     * @param newText scroll text
     */
    public void setText(String newText) {
        isSetNewText = true;
        stopScroll = false;
        this.text = newText;
        measureVarious();
    }


    /**
     * Set the text color
     *
     * @param color A color value in the form 0xAARRGGBB.
     */
    public void setTextColor(@ColorInt int color) {
        textColor = color;
        paint.setColor(textColor);
    }


    /**
     * set scroll speed
     *
     * @param speed SCROLL SPEED [0,10] ///// 0?
     */
    public void setSpeed(int speed) {
        if (speed > 10 || speed < 0) {
            throw new IllegalArgumentException("Speed was invalid integer, it must between 0 and 10");
        } else {
            this.speed = speed;
        }
    }


    /**
     * scroll text forever
     *
     * @param scrollForever scroll forever or not
     */
    public void setScrollForever(boolean scrollForever) {
        isScrollForever = scrollForever;
    }

    /**
     * touch to stop / start
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!clickEnable) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pauseScroll = !pauseScroll;
                break;
        }
        return true;
    }


    /**
     * scroll text vertical
     */
    private void drawVerticalScroll() {
        List<String> strings = new ArrayList<>();
        int start = 0, end = 0;
        while (end < text.length()) {
            while (paint.measureText(text.substring(start, end)) < viewWidth && end < text.length()) {
                end++;
            }
            if (end == text.length()) {
                strings.add(text.substring(start, end));
                break;
            } else {
                end--;
                strings.add(text.substring(start, end));
                start = end;
            }
        }

        float fontHeight = paint.getFontMetrics().bottom - paint.getFontMetrics().top;

        FontMetrics fontMetrics = paint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        float baseLine = viewHeight / 2 + distance;

        for (int n = 0; n < strings.size(); n++) {

            for (float i = viewHeight + fontHeight; i > -fontHeight; i = i - 3) {

                if (stopScroll || isSetNewText) {
                    return;
                }

                if (pauseScroll) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }
                    continue;
                }

                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
                canvas.drawText(strings.get(n), 0, i, paint);
                surfaceHolder.unlockCanvasAndPost(canvas);

                if (i - baseLine < 4 && i - baseLine > 0) {
                    if (stopScroll) {
                        return;
                    }
                    try {
                        Thread.sleep(speed * 1000);
                    } catch (InterruptedException e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        }
    }

    /**
     * Draw text
     *
     * @param X X
     * @param Y Y
     */
    private synchronized void draw(float X, float Y) {
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
        canvas.drawText(text, X, Y, paint);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }


    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        this.setVisibility(visibility);
    }

    /**
     * measure tex
     */
    private void measureVarious() {
        textWidth = paint.measureText(text);
        viewWidth_plus_textLength = viewWidth + textWidth;
        textX = viewWidth - viewWidth / 5;

        //baseline measure
        FontMetrics fontMetrics = paint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        textY = viewHeight / 2 + distance;
    }


    /**
     * Scroll thread
     */
    class ScrollTextThread implements Runnable {
        @Override
        public void run() {

            measureVarious();

            while (!stopScroll) {

                // NoNeed Scroll，短文不滚动
//                if (textWidth < getWidth()) {
//                    draw(1, textY);
//                    stopScroll = true;
//                    break;
//                }

                if (isHorizontal) {
                    if (pauseScroll) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.toString());
                        }
                        continue;
                    }

                    draw(viewWidth - textX, textY);
                    textX += speed;
                    if (textX > viewWidth_plus_textLength) {
                        textX = 0;
                        --needScrollTimes;
                    }
                } else {
                    drawVerticalScroll();
                    isSetNewText = false;
                    --needScrollTimes;
                }

                if (needScrollTimes <= 0 && isScrollForever) {
                    stopScroll = true;
                }

            }
        }
    }

}
