package com.bedrock.zhihu_banner_switch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {


    private MyScrollView scrollView;
    private ImageView ivPurple,ivOrange;

    private boolean isScrollUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initListener();

    }

    private void initView(){
        ivOrange = findViewById(R.id.iv_orange);
        ivPurple = findViewById(R.id.iv_purple);

        ivPurple.setDrawingCacheEnabled(true);
        ivOrange.setDrawingCacheEnabled(true);

        bmPurple = ivPurple.getDrawingCache();
        bmOrange = ivOrange.getDrawingCache();

        ivPurple.setDrawingCacheEnabled(false);
        ivOrange.setDrawingCacheEnabled(false);


        scrollView = findViewById(R.id.scroll_view);
    }
    private Bitmap bmPurple,bmOrange;

    private void initListener(){
        scrollView.setListener(new MyScrollView.onScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                Log.i("scrollview",
                        "l : " + l + "   t :" + t + "   oldl :" + oldl+ "  oldt: " + oldt);
                if(t > oldt){
                    //scroll down
                    isScrollUp = false;
                }else{
                    isScrollUp = true;
                }

            }
        });
    }

    //只需要裁剪上层 也就是 iv orange
    private void cropBitmap(){}


}






















