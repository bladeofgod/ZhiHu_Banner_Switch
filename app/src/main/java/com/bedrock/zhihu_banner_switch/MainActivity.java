package com.bedrock.zhihu_banner_switch;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
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

//        ivPurple.setDrawingCacheEnabled(true);
//        ivOrange.setDrawingCacheEnabled(true);
//
//        bmPurple = ivPurple.getDrawingCache();
//        bmOrange = ivOrange.getDrawingCache();
//
//        ivPurple.setDrawingCacheEnabled(false);
//        ivOrange.setDrawingCacheEnabled(false);
        bmPurple = ((BitmapDrawable)ivPurple.getDrawable()).getBitmap();
        bmOrange = ((BitmapDrawable)ivOrange.getDrawable()).getBitmap();


        scrollView = findViewById(R.id.scroll_view);

        findViewById(R.id.btn_restore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivOrange.setImageBitmap(bmOrange);
            }
        });

        findViewById(R.id.btn_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivOrange.setImageBitmap(circleBitmap(bmOrange, screenWidth/ratio));
                //ivOrange.setImageBitmap(cropBitmapInCircleWay(bmOrange,screenWidth/ratio));
                ratio ++;
            }
        });

    }
    private int ratio = 2;
    private float screenWidth;
    private Bitmap bmPurple,bmOrange;
    private float threshold = 500;

    private void initListener(){
        scrollView.setListener(new MyScrollView.onScrollListener() {
            @Override
            public void onScroll(int l, int t, int oldl, int oldt) {
                Log.i("scrollview",
                        "l : " + l + "   t :" + t + "   oldl :" + oldl+ "  oldt: " + oldt);
                float curY = (float)t;

                //暂时滚动高度阈值定位500
                if(t > oldt){
                    //scroll down
                    isScrollUp = false;
                    float r = curY / threshold ;
                    Log.i("crop", "ratio :" + r);
                    Log.i("crop", "radius :" + (r * screenWidth));
                    ivOrange.setImageBitmap(circleBitmap(bmOrange, r * screenWidth));
                }else{
                    isScrollUp = true;
                    float r = curY / threshold ;
                    Log.i("crop", "ratio :" + r);
                    Log.i("crop", "radius :" + (r * screenWidth));
                    ivOrange.setImageBitmap(circleBitmap(bmOrange, r * screenWidth));
                }

            }
        });
    }

    //只需要裁剪上层 也就是 iv orange
    //

    private Bitmap clipPath(Bitmap o,float radius){
        if(o == null){
            return null;
        }
        Bitmap origin = Bitmap.createBitmap(o).copy(Bitmap.Config.RGB_565,true);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(origin);
        canvas.save();
        Path path = new Path();
        path.moveTo(0,0);
        path.addCircle(0,0,200, Path.Direction.CW);
        canvas.clipPath(path);
        canvas.drawBitmap(origin, 0,0,paint);
        canvas.restore();
        return origin;

    }

    /**
     * @param o 待裁剪
     * @param radius 圆形半径
     *
     *  从原图（矩形）左上角开始为圆心，进行裁剪，
     *  不断扩大半径
     */

    private Bitmap circleBitmap(Bitmap o,float radius){
        //Log.i("crop", "radius :" + radius);
        Bitmap outputBm = Bitmap.createBitmap(o.getWidth(),o.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBm);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);

        final Rect rect = new Rect(0,0,o.getWidth(),o.getHeight());
        canvas.drawARGB(0,0,0,0);
        canvas.drawCircle(0,0,radius,paint );
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(o,rect,rect,paint);
        return outputBm;
    }

    private Bitmap cropBitmapInCircleWay(Bitmap o,float radius){
        if(o == null){
            return null;
        }

        Bitmap origin = Bitmap.createBitmap(o);

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
            //canvas.drawARGB(0,0,0,0);
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






















