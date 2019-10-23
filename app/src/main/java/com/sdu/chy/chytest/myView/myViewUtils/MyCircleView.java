package com.sdu.chy.chytest.myView.myViewUtils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.sdu.chy.chytest.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class MyCircleView extends View {

    int defalutSize = 0;

    public MyCircleView(Context context) {
        super(context);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签,即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MyCircleView);
        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defalutSize = a.getDimensionPixelSize(R.styleable.MyCircleView_default_size, 0);

        //最后记得将TypedArray对象回收
        a.recycle();
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size,则大小取最大值
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);//获取组件宽、高信息
        int width = getMySize(defalutSize,widthMeasureSpec);
        int height = getMySize(defalutSize,heightMeasureSpec);//自定义默认宽高情况

        if(width<height)height=width;
        else width=height;

        setMeasuredDimension(width,height);//自定义组件，用来决定组件大小
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//调用父View的onDraw函数，因为View这个类帮我们实现了一些基本的而绘制功能，比如绘制背景颜色、背景图片等
        int r = getMeasuredHeight() / 2;//半径
        int centerX = getLeft() + r;//圆心的横坐标为当前的View的左边起始位置+半径
        int centerY = getTop() + r; //圆心的纵坐标为当前的View的顶部起始位置+半径
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        //开始绘制
        canvas.drawCircle(centerX,centerY,r,paint);
    }
}


