package com.zackdk.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by Administrator on 2018/2/28.
 */

public class LoadingView extends View {

    private Paint paint;
    private int mWidth;
    private int mHeight;
    private float startAngle ;
    private float endAngle ;
    private float strokeWidth ;
    public LoadingView(Context context) {
        super(context);
    }
    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GRAY);
        mWidth = 0;
        mHeight = 0;
        startAngle = 180;
        endAngle = 180;
        strokeWidth = 5;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,720);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if(value<=360){
                    endAngle = value;
                }else{
                    startAngle = value-360;
                    endAngle = 360-startAngle;
                }
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(-1);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(w<h){
            mWidth = w;
            mHeight = w;
        }else{
            mWidth = h;
            mHeight = h;
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //调用父View的onDraw函数，因为View这个类帮我们实现了一些
        // 基本的而绘制功能，比如绘制背景颜色、背景图片等
        super.onDraw(canvas);
        RectF oval = new RectF(strokeWidth,strokeWidth,mWidth-strokeWidth,mHeight-strokeWidth);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.LTGRAY);
        canvas.drawArc(oval,startAngle,endAngle,false,paint);
    }

}
