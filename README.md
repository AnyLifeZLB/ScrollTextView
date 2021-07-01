
## 由于Jcenter 已经停止使用，现在repo 托管更改到 mavenCentral

# 关于提Issues
本库一直在持续维护中，但是能投入的时间不多，有issues 的同学说明一下以下内容
- 1.使用场景：   比如ViewPager+fragment 滑动切换
- 2.Android系统信息：  手机品牌，Android版本号


# How to use
```
    <anylife.scrolltextview.ScrollTextView
        android:id="@+id/textView2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_toRightOf="@+id/imageView2"
        android:layout_marginRight="6dp"
        android:singleLine="true"
        customAttr:clickEnable="true"
        customAttr:isHorizontal="true"
        customAttr:speed="1"
        customAttr:text="ScrollTextView Auto Scroll.1234567890,"
        customAttr:text_size="14sp"
        customAttr:text_color="#ffffffff"
        customAttr:times="567" />
```

或者在代码中

```
    scrollText = findViewById(R.id.scrollText);
    scrollText.setSpeed(4);
    scrollText.setText("new text");
    scrollText.setTextColor(0xffad43ae);

```


# Gradle 集成使用
```
 implementation 'io.github.anylifezlb:ScrollTextviewLib:2.0.0'
```

# Maven 集成使用
```
<dependency>
  <groupId>io.github.anylifezlb</groupId>
  <artifactId>ScrollTextView</artifactId>
  <version>2.0.0</version>
  <type>aar</type>
</dependency>
```

# About ScrollTextView
Class ScrollTextView extends SurfaceView implements SurfaceHolder.Callback


Android 滚动字幕，如新闻联播下面的，可以使用在广告机，机顶盒，电视App等信息发布系统。
以前是在信息发布系统上使用过，作为动态布局的一部分，题外话，如果有想做像分众传媒或者
类似的电梯门口广告的可以参考一下.

动态布局专利：http://www.google.com/patents/CN103336691A?cl=zh （科学上网）


继承SurfaceView 实现，CPU 占用低，无内存抖动，在Nexus5X，Android 7.0 上测试流畅  
Gif 图看起来卡是为了图像质量和大小的平衡减低帧率。

Any questions,please contact me at:anylife.zlb@gmail.com


![image](https://github.com/AnyLifeZLB/ScrollTextView/raw/master/GIF.gif)



