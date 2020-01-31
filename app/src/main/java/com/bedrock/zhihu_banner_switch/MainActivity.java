package com.bedrock.zhihu_banner_switch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {


    private MyScrollView scrollView;
    private ImageView ivPurple,ivOrange;

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

        scrollView = findViewById(R.id.scroll_view);
    }

    private void initListener(){
        scrollView.setListener(new MyScrollView.onScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {

            }
        });
    }


}






















