package com.anylife.fragment.scrolltextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import anylife.scrolltextview.ScrollTextView;

/**
 * Demo
 *
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScrollTextView scrollText=findViewById(R.id.scrollText);
        scrollText.setSpeed(4);
        scrollText.setText("746736547832657843625784328564832658432656478237dafdasfdsafdasf");
















        //下面的不看了🙈


        TextView titleText=findViewById(R.id.item1).findViewById(R.id.item_title);
        titleText.setText("房号");
        ImageView itemIcon=findViewById(R.id.item1).findViewById(R.id.item_icon);
        itemIcon.setImageResource(R.drawable.room_no);

        ScrollTextView scrollTextView1=findViewById(R.id.item1).findViewById(R.id.item_content);
        scrollTextView1.setText("深圳慧风雅苑净书阁X栋A12");
        scrollTextView1.setHorizontal(false);



        TextView titleText2=findViewById(R.id.item2).findViewById(R.id.item_title);
        titleText2.setText("电话");
        ImageView itemIcon2=findViewById(R.id.item2).findViewById(R.id.item_icon);
        itemIcon2.setImageResource(R.drawable.phone_no);

        ScrollTextView scrollTextView2=findViewById(R.id.item2).findViewById(R.id.item_content);
        scrollTextView2.setText("(+86)1882656207x");
        scrollTextView2.setHorizontal(true);



        TextView titleText3=findViewById(R.id.item3).findViewById(R.id.item_title);
        titleText3.setText("车位");
        ImageView itemIcon3=findViewById(R.id.item3).findViewById(R.id.item_icon);
        itemIcon3.setImageResource(R.drawable.parking_no);

        ScrollTextView scrollTextView3=findViewById(R.id.item3).findViewById(R.id.item_content);
        scrollTextView3.setText("B2-102");


        TextView titleText4=findViewById(R.id.item4).findViewById(R.id.item_title);
        titleText4.setText("车牌");
        ImageView itemIcon4=findViewById(R.id.item4).findViewById(R.id.item_icon);
        itemIcon4.setImageResource(R.drawable.car_license);

        ScrollTextView scrollTextView4=findViewById(R.id.item4).findViewById(R.id.item_content);
        scrollTextView4.setText("粤B64236");


    }
}
