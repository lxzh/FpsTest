package com.lxzh123.fpstest;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * description $desc$
 * author      Created by lxzh
 * date        2019/2/1
 */
public class FpsView extends AppCompatTextView {

    private long count;


    public FpsView(Context context) {
        super(context);
        count=0;
    }

    public FpsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        count=0;
    }

    public FpsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        count=0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        count++;
        canvas.drawText("" + count, 1, 50, getPaint());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
