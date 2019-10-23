package com.sdu.chy.chytest.myView.myAnimation;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

public class QQTrembleAnimation extends Animation{

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        setDuration(1000);//设置默认时长1秒
        setFillAfter(true);//保持动画结束状态
        setInterpolator(new LinearInterpolator());//设置线性插值器
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        //自定义动画的核心，在动画的执行过程中会不断回调此方法，并且每次回调interpolatedTime值都在不断变化(0----1)
        //Matrix.setTranslate(dx,dy)中dx、dy表示移动距离，是根据interpolatedTime计算出正弦值，实现了抖动
        t.getMatrix().setTranslate(
                (float)Math.sin(interpolatedTime * 50)*8,
                (float)Math.sin(interpolatedTime * 50)*8
        );
        super.applyTransformation(interpolatedTime, t);
    }
}
