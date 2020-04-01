package com.anylife.fragment.scrolltextview;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import anylife.scrolltextview.ScrollTextView;

/**
 * 跑马灯设置
 */
public class LauncherActivity extends AppCompatActivity {
    public static final String TEXT_INPUT_KEY = "textInput";
    public static final String SCROLL_SIZE_KEY = "scrollSize";
    public static final String SCROLL_SPEED_KEY = "scrollSpeed";
    public static final String TEXT_COLOR_KEY = "textColor";
    public static final String TEXT_BG_COLOR_KEY = "textBgColor";

    public static final int REQUEST_SETTING_CODE = 0001;
    private ScrollTextView scrollTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        scrollTextView = findViewById(R.id.scrollText);

        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, SettingActivity.class);

                intent.putExtra(TEXT_INPUT_KEY, scrollTextView.getText());
                intent.putExtra(SCROLL_SIZE_KEY, scrollTextView.getTextSize());
                intent.putExtra(SCROLL_SPEED_KEY, scrollTextView.getSpeed());
                intent.putExtra(TEXT_COLOR_KEY, scrollTextView.getTextColor());
                intent.putExtra(TEXT_BG_COLOR_KEY, scrollTextView.getBackGroudColor());

                startActivityForResult(intent, REQUEST_SETTING_CODE);
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_INPUT_KEY, scrollTextView.getText());
        outState.putFloat(SCROLL_SIZE_KEY, scrollTextView.getTextSize());
        outState.putInt(SCROLL_SPEED_KEY, scrollTextView.getSpeed());
        outState.putInt(TEXT_COLOR_KEY, scrollTextView.getTextColor());
        outState.putInt(TEXT_BG_COLOR_KEY, scrollTextView.getBackGroudColor());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        scrollTextView.setText(savedInstanceState.getString(TEXT_INPUT_KEY));
        scrollTextView.setTextSize(savedInstanceState.getFloat(SCROLL_SIZE_KEY));
        scrollTextView.setSpeed(savedInstanceState.getInt(SCROLL_SPEED_KEY));
        scrollTextView.setTextColor(savedInstanceState.getInt(TEXT_COLOR_KEY));
        scrollTextView.setBackgroundColor(savedInstanceState.getInt(TEXT_BG_COLOR_KEY));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SETTING_CODE && resultCode == RESULT_OK) {
            int scrollSpeed = data.getIntExtra(SCROLL_SPEED_KEY, 0);
            if (0 != scrollSpeed) {
                scrollTextView.setSpeed(scrollSpeed);
            }

            float scrollSize = data.getFloatExtra(SCROLL_SIZE_KEY, 0f);
            if (0 != scrollSize) {
                scrollTextView.setTextSize(scrollSize);
            }

            int textColor = data.getIntExtra(TEXT_COLOR_KEY, 0);
            if (0 != textColor) {
                scrollTextView.setTextColor(textColor);
            }

            int textBgColor = data.getIntExtra(TEXT_BG_COLOR_KEY, 0);
            if (0 != textColor) {
                scrollTextView.setScrollTextBackgroundColor(textBgColor);
            }

            if (!TextUtils.isEmpty(data.getStringExtra(TEXT_INPUT_KEY))) {
                scrollTextView.setText(data.getStringExtra(TEXT_INPUT_KEY));
            }

        }
    }

}
