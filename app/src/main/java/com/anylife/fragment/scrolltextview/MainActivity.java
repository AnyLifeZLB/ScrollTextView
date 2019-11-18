package com.anylife.fragment.scrolltextview;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import anylife.scrolltextview.ScrollTextView;

/**
 * Demo
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ScrollTextView scrollText;

    private ImageView imageView;

    String tempStr="";




    @Override
    public void onClick(View view) {
        tempStr=tempStr+SystemClock.currentThreadTimeMillis();
        scrollText.setText(tempStr+"-");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollText = findViewById(R.id.scrollText);
        scrollText.setSpeed(4);
        scrollText.setHorizontal(false);
        scrollText.setText("你打扰到我学习了1234567890AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz");

        imageView=findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollText.setTextColor(0xffaa3456);
            }
        });  //测试动态改变TextView












        //下面的不看了🙈

        TextView titleText = findViewById(R.id.item1).findViewById(R.id.item_title);
        titleText.setText("房号");
        ImageView itemIcon = findViewById(R.id.item1).findViewById(R.id.item_icon);
        itemIcon.setImageResource(R.drawable.room_no);

        ScrollTextView scrollTextView1 = findViewById(R.id.item1).findViewById(R.id.item_content);
        scrollTextView1.setText("深圳慧风雅苑净书阁X栋A12");
        scrollTextView1.setHorizontal(true);


        TextView titleText2 = findViewById(R.id.item2).findViewById(R.id.item_title);
        titleText2.setText("电话");
        ImageView itemIcon2 = findViewById(R.id.item2).findViewById(R.id.item_icon);
        itemIcon2.setImageResource(R.drawable.phone_no);

        ScrollTextView scrollTextView2 = findViewById(R.id.item2).findViewById(R.id.item_content);
        scrollTextView2.setText("(+86)1882656207x");
        scrollTextView2.setHorizontal(true);


        TextView titleText3 = findViewById(R.id.item3).findViewById(R.id.item_title);
        titleText3.setText("车位");
        ImageView itemIcon3 = findViewById(R.id.item3).findViewById(R.id.item_icon);
        itemIcon3.setImageResource(R.drawable.parking_no);

        ScrollTextView scrollTextView3 = findViewById(R.id.item3).findViewById(R.id.item_content);
        scrollTextView3.setText("B2-102");


        TextView titleText4 = findViewById(R.id.item4).findViewById(R.id.item_title);
        titleText4.setText("车牌");
        ImageView itemIcon4 = findViewById(R.id.item4).findViewById(R.id.item_icon);
        itemIcon4.setImageResource(R.drawable.car_license);

        ScrollTextView scrollTextView4 = findViewById(R.id.item4).findViewById(R.id.item_content);
        scrollTextView4.setText("粤B64236");


    }
}
