package com.anylife.fragment.scrolltextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *垂直的滚动内存抖动会很大，尽量不要使用这种样式的
 *
 * More todo：
 *1.单行翻滚
 *2.多行滚动
 *
 *
 */
public class ScrollTextView extends SurfaceView implements SurfaceHolder.Callback {
	private final String TAG = "ScrollTextView";
	private SurfaceHolder holder;
	private Paint paint = null;             // 画笔
	private boolean bStop = false;          // 停止滚动

	private boolean clickEnable = false;    // 可以点击
	private boolean isHorizontal = true;    // 水平｜垂直
	private int speed = 1;                  // 滚动速度
	private String text = "";               // 文本内容
	private float textSize = 15f;           // 字体颜色
	private int textColor = Color.BLACK;    // 文字颜色
	private int times = Integer.MAX_VALUE;  // 滚动次数

	private int viewWidth = 0;       // 控件的长度
	private int viewHeight = 0;      // 控件的高度
	private float textWidth = 0f;    // 水平滚动时的文本长度
	private float textHeight = 0f;   // 垂直滚动时的文本高度

	private float textX = 0f;        // 文字的横坐标
	private float textY = 0f;        // 文字的纵坐标
	private float viewWidth_plus_textLength = 0.0f;// 显示总长度
	private int time = 0; // 已滚动次数


	private List<String> contxtText=new ArrayList<>();  //切割成为多段要处理现实的字幕

	private ScheduledExecutorService scheduledExecutorService; // 执行滚动线程

	public ScrollTextView(Context context) {
		super(context);
	}

	public ScrollTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		holder = this.getHolder();
		holder.addCallback(this);
		paint = new Paint();
		TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollText);
		clickEnable = arr.getBoolean(R.styleable.ScrollText_clickEnable, clickEnable);
		isHorizontal = arr.getBoolean(R.styleable.ScrollText_isHorizontal, isHorizontal);
		speed = arr.getInteger(R.styleable.ScrollText_speed, speed);
		text = arr.getString(R.styleable.ScrollText_text);
		textColor = arr.getColor(R.styleable.ScrollText_textColor, textColor);
		textSize = arr.getDimension(R.styleable.ScrollText_textSize, textSize);
		times = arr.getInteger(R.styleable.ScrollText_times, times);

		time = times;
		paint.setColor(textColor);
		paint.setTextSize(textSize);

		/*
		 * 下面两行代码配合draw()方法中的canvas.drawColor(Color.TRANSPARENT,Mode.CLEAR);
		 * 将画布填充为透明
		 */
		setZOrderOnTop(true);
		getHolder().setFormat(PixelFormat.TRANSLUCENT);

		setFocusable(true); //设置焦点
	}

	/**
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		if (isHorizontal) { // 水平滚动
			textWidth = paint.measureText(text);// measure()方法获取text的长度
			viewWidth_plus_textLength = viewWidth + textWidth;
			textY = (viewHeight - getFontHeight(textSize)) / 2 + getPaddingTop() - getPaddingBottom();
		} else { // 垂直滚动，不建议使用，会导致很多的问题，CPU 和 内存都会陡增
			textHeight = getFontHeight(textSize) * text.length();
			viewWidth_plus_textLength = viewHeight + textHeight;
			textX = (viewWidth - textSize) / 2 + getPaddingLeft() - getPaddingRight();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		bStop = false;
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTextThread(), 1000, 10, TimeUnit.MILLISECONDS);
		Log.d(TAG, "ScrollTextTextView is created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		bStop = true;
		scheduledExecutorService.shutdown();
		Log.d(TAG, "ScrollTextTextView is destroyed");
	}

	// 获取字体高度
	public int getFontHeight(float fontSize) {
		Paint paint = new Paint();
		paint.setTextSize(fontSize);
		FontMetrics fm = paint.getFontMetrics();
		return (int) Math.ceil(fm.descent - fm.ascent);
	}

	public void setTimes(int times) {
		if (times <= 0) {
			this.times = Integer.MAX_VALUE;
		} else {
			this.times = times;
			time = times;
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setSpeed(int speed) {
		if (speed > 10 || speed < 0) {
			throw new IllegalArgumentException("Speed was invalid integer, it must between 0 and 10");
		} else {
			this.speed = speed;
		}
	}

	/**
	 * 触摸停止滚动
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!clickEnable) {
			return true;
		}
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				bStop = !bStop;
				if (!bStop && time == 0) {
					time = times;
				}
				break;
		}
		return true;
	}

	/**
	 * Draw text ！
	 *
	 * @param X
	 * @param Y
	 */
	public synchronized void draw(float X, float Y) {
		Canvas canvas = holder.lockCanvas();
		canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);// 通过清屏把画布填充为透明
		if (isHorizontal) { // 水平滚动
			canvas.drawText(text, X, Y, paint);
		} else {            // 垂直滚动，这样看来内存抖动会非常的厉害
			for (int i = 0; i < text.length(); i++) {
				canvas.drawText(text.charAt(i) + "", X, Y + (i + 1) * getFontHeight(textSize), paint);
			}
		}
		holder.unlockCanvasAndPost(canvas);
	}

	class ScrollTextThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (!bStop) {
				if (isHorizontal) {
					draw(viewWidth - textX, textY);
					textX += speed;// 速度设置：1-10
					if (textX > viewWidth_plus_textLength) {
						textX = 0;
						--time;
					}
				} else {
					draw(textX, viewHeight - textY);
					textY += speed;
					if (textY > viewWidth_plus_textLength) {
						textY = 0;
						--time;
					}
				}
				if (time <= 0) {
					bStop = true;
				}
			}
		}
	} //ScrollTextThread is over !

}
