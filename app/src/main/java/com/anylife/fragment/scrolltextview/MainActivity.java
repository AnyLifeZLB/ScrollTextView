package com.anylife.fragment.scrolltextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import anylife.scrolltextview.ScrollTextView;

/**
 * Demo
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollTextView scrollingView2 = (ScrollTextView) findViewById(R.id.scrollView2);
        scrollingView2.setText("静1234567890ABCDEFGHIJKLMNOPQRESUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz-VANKE SERVICE");
        scrollingView2.setClickable(true);
        scrollingView2.setSpeed(2);
        scrollingView2.setTimes(1314167);

//        getLifecycle().addObserver(scrollingView2);

    }
}
