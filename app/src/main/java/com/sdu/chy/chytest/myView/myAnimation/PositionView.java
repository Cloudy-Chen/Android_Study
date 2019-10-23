package com.sdu.chy.chytest.myView.myAnimation;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public class PositionView extends View {

    public static final float RADIUS = 20f;
    private PositionPoint currentPoint;
    private Paint mPaint;
    private int color;

    public PositionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new PositionPoint(RADIUS, RADIUS);
            drawCircle(canvas);
            startPropertyAni();
        } else {
            drawCircle(canvas);
        }
    }

    private void drawCircle(Canvas canvas) {
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(this.color);
    }

    /**
     * 启动动画
     */
    private void startPropertyAni() {
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator animator1 = ValueAnimator.ofObject(
                new PositionEvaluator(),
                new PositionPoint(RADIUS, RADIUS),
                new PositionPoint(getWidth() - RADIUS, getHeight() - RADIUS));
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (PositionPoint) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());

        ObjectAnimator animator2 = ObjectAnimator.ofObject( this,"color",new ArgbEvaluator(),Color.RED, Color.BLUE,Color.GREEN,Color.YELLOW);

        animatorSet.playTogether(animator1,animator2);
        animatorSet.setDuration(10 * 1000);
        animatorSet.start();
    }
}
