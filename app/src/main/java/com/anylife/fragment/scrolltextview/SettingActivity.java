package com.anylife.fragment.scrolltextview;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;

import anylife.scrolltextview.ScrollTextView;

/**
 * 设置页面
 */
public class SettingActivity extends AppCompatActivity {

    private Button closeBtn;
    private ScrollTextView scrollTextView;
    private TextView textColorView, textBgView;

    private SeekBar textSizeSeekBar;

    private int textColor,textBgColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        scrollTextView = findViewById(R.id.scrollText);
        closeBtn = findViewById(R.id.close);

        textColorView = findViewById(R.id.text_color);
        textColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectColor(true);
            }
        });

        textBgView = findViewById(R.id.bg_color);

        textBgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectColor(false);
            }
        });

        textSizeSeekBar = findViewById(R.id.text_size_seek_bar);
        textSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scrollTextView.setTextSize(progress);
                closeBtn.setTextSize(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    /**
     * 视图初始化
     */
    private void viewsInit() {

    }

    PopupWindow mPopWindow;

    private void selectColor(final boolean isSetTextColor) {
        View contentView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.color_pop_win, null);
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        //显示PopupWindow
        View rootView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.activity_setting, null);
        mPopWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);


        final ColorPicker picker = contentView.findViewById(R.id.picker);
        Button confirm=contentView.findViewById(R.id.confirm);

        //To get the color
        picker.getColor();

        picker.setOldCenterColor(picker.getColor());

        picker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                if(isSetTextColor){
                    scrollTextView.setTextColor(picker.getColor());
                    textColor=picker.getColor();
                }else {
                    scrollTextView.setBackgroundColor(picker.getColor());
                    textBgColor=picker.getColor();
                }

            }
        });
        picker.setShowOldCenterColor(false);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow.dismiss();
            }
        });

    }

}
