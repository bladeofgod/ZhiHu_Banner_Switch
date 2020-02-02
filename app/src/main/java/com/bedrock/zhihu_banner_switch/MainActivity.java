package com.bedrock.zhihu_banner_switch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        screenWidth = wm.getDefaultDisplay().getWidth();
        WindowManager wm = getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;

        ivOrange = findViewById(R.id.iv_orange);
        ivPurple = findViewById(R.id.iv_purple);

        ivPurple.setDrawingCacheEnabled(true);
        ivOrange.setDrawingCacheEnabled(true);

        bmPurple = ivPurple.getDrawingCache();
        bmOrange = ivOrange.getDrawingCache();

        ivPurple.setDrawingCacheEnabled(false);
        ivOrange.setDrawingCacheEnabled(false);


        scrollView = findViewById(R.id.scroll_view);

        findViewById(R.id.btn_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivOrange.setImageBitmap(cropBitmapInCircleWay(bmOrange,screenWidth/ratio));
                ratio ++;
            }
        });

    }
    private int ratio = 2;
    private int screenWidth;
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
    //

    /**
     * @param origin 待裁剪
     * @param radius 圆形半径
     *
     *  从原图（矩形）左上角开始为圆心，进行裁剪，
     *  不断扩大半径
     */

    private Bitmap cropBitmapInCircleWay(Bitmap origin,float radius){
        if(origin == null){
            return null;
        }

        //创建一个原图 备份
        Bitmap circleBitmap = Bitmap.createBitmap(origin.getWidth(), origin.getHeight(),
                Bitmap.Config.RGB_565);
        try {

            //画布
            Canvas canvas = new Canvas(circleBitmap);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0,0,origin.getWidth(),origin.getHeight());
            final RectF rectF = new RectF(new Rect(0,0,origin.getWidth(),origin.getHeight()));

            paint.setAntiAlias(true);
            //设置画布模式
            canvas.drawARGB(0,0,0,0);
            paint.setColor(Color.WHITE);
            canvas.drawRoundRect(rectF, radius, radius, paint);
            //遮罩模式  具体可以参考这个网址
            //https://www.cnblogs.com/tianzhijiexian/p/4297172.html
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0,0,(int)radius,(int)radius);
            canvas.drawBitmap(origin, src,rect,paint);
            return circleBitmap;

        }catch (Exception e){
            return origin;
        }

    }


}






















