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
    scrollText.setText("new text");  //已经修改setText() 没能动态生效的问题
```


# Gradle 集成使用
```
 implementation 'anylife.scrolltextview:ScrollTextviewLib:1.3.7'   [new]
```

# Maven 集成使用
```
<dependency>
  <groupId>anylife.scrolltextview</groupId>
  <artifactId>ScrollTextviewLib</artifactId>
  <version>1.3.7</version>
  <type>pom</type>
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



