package com.anylife.fragment.scrolltextview;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import anylife.scrolltextview.ScrollTextView;

/**
 * 跑马灯设置
 *
 */
public class LauncherActivity extends AppCompatActivity {
    private static final int REQUEST_SETTING_CODE = 0001;
    private ScrollTextView scrollTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        scrollTextView = findViewById(R.id.scrollText);

        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LauncherActivity.this, SettingActivity.class), REQUEST_SETTING_CODE);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SETTING_CODE && resultCode == RESULT_OK) {
            // 处理回调信息
//            scrollTextView.setSpeed();
//            scrollTextView.setText();
//            scrollTextView.setTextColor();
        }
    }


}
