package com.loan.testapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyProgressBar extends View {

    private Paint mPaint = new Paint();
    private int progressColor = Color.BLUE; //进度颜色
    private int progressBackgroundColor = Color.GRAY; //进度轨道颜色
    private float round = 10; //进度轮廓半径
    private float progressWidth = 30; //进度轮廓宽度
    private int max = 100; //最大值
    private int progress = 50; //进度值
    private OnProgressChangeListener mListener; //回调接口

    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        progressColor = typedArray.getColor(R.styleable.MyProgressBar_progress_color, progressColor);
        progressBackgroundColor = typedArray.getColor(R.styleable.MyProgressBar_progress_background_color, progressBackgroundColor);
        round = typedArray.getDimension(R.styleable.MyProgressBar_round, round);
        progressWidth = typedArray.getDimension(R.styleable.MyProgressBar_progress_width, progressWidth);
        max = typedArray.getInt(R.styleable.MyProgressBar_max, max);
        progress = typedArray.getInt(R.styleable.MyProgressBar_progress, progress);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true); //消除锯齿

        //绘制进度轨道
        mPaint.setColor(progressBackgroundColor);
        RectF rectF = new RectF(0, 0, getWidth(), getHeight()); //绘制矩形
        canvas.drawRoundRect(rectF, round, round, mPaint); //以矩形绘制进度轨道

        //绘制进度条
        mPaint.setColor(progressColor);
        rectF = new RectF(0, 0, getProgressWidth(), getHeight()); //绘制矩形
        canvas.drawRoundRect(rectF, round, round, mPaint); //以矩形绘制进度条

    }

    private int getProgressWidth() {
        return (int) (getWidth() * progress / (max * 1.0f));
    }

    public void setOnProgressChangeListener(OnProgressChangeListener listener) {
        mListener = listener;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public int getMax() {
        return max;
    }

    // 判断手指是否在进度条上
    private boolean isTouchOnProgress(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int offset = (int) progressWidth / 2 + 10;
        return y >= 0 && y <= getHeight() && x >= 0 && x <= getWidth() && x >= (getProgressWidth() - offset) && x <= (getProgressWidth() + offset);
    }

    public interface OnProgressChangeListener {
        void onProgressChange(int progress);
    }

    // 移动进度变化
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!isTouchOnProgress(event)) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // 设置进度以10为单位，例：进度：30,40,50...
                int progress = (int) ((Math.round(event.getX() / getWidth()*10))* getMax()/10);
                setProgress(progress);
                if (mListener != null) {
                    mListener.onProgressChange(progress);
                }
                break;
        }
        invalidate();
        return true;
    }
}