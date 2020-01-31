package com.bedrock.zhihu_banner_switch;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(listener != null){
            listener.onScroll(l, t, oldl, oldt);
        }
    }

    private onScrollListener listener;

    public void setListener(onScrollListener listener) {
        this.listener = listener;
    }

    public interface onScrollListener{
        void onScroll(int l, int t, int oldl, int oldt);
    }

}

















