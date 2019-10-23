package com.sdu.chy.chytest.myView.myViewUtils;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.sdu.chy.chytest.R;

public class MyViewGroup extends ViewGroup {
    //实现垂直布局案例

    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //@param changed 该参数指出当前ViewGroup的尺寸或者位置是否发生了改变
    //@param left top right bottom 当前ViewGroup相对于其父控件的坐标位置
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //对子View在ViewGroup的布局进行管理（如何摆放？）
        int childCount = getChildCount();
        //记录当前高度
        int currentHeight = 0;
        //将子View逐个摆放
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            int width = childView.getMeasuredWidth();
            //摆放子View，参数分别是子View矩形区域的左、上、右、下
            childView.layout(left,currentHeight,left+width,currentHeight+height);
            currentHeight += height;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //1.将所有的子View进行测量,这会触发每个子View的onMeasure函数,与measureChild区分,measureChild是对单个view进行测量
        //调用这个函数后，能获得后面每个子View的测量值（必加方法）
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        //2.根据子View的大小，及ViewGroup的大小，决定当前ViewGroup大小
        if(childCount==0){
            setMeasuredDimension(0,0);
        }else{
            if(widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
                //如果高度和宽度均为wrap_content，则宽度为子View中最大宽度，高度为所有子View高度和
                setMeasuredDimension(getMaxChildWidth(),getTotalHeight());
            }else if (widthMode == MeasureSpec.AT_MOST){
                //宽度是wrap_content，则设置宽度为子View最大宽度，高度为测量值
                setMeasuredDimension(getMaxChildWidth(),heightSize);
            }else if (heightMode == MeasureSpec.AT_MOST){
                //高度是wrap_content，则设置高度为所有子View高度，宽度为测量值
                setMeasuredDimension(widthSize,getTotalHeight());
            }else{
                setMeasuredDimension(widthSize,heightSize);
            }
        }
    }

    private int getMaxChildWidth(){
        //经过measureChildren(widthMeasureSpec,heightMeasureSpec);已经得到子View的测量值，可设置子View尺寸
        int childCount = getChildCount();
        int maxWidth = 0;
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            if(childView.getMeasuredWidth() > maxWidth){
                maxWidth = childView.getMeasuredWidth();
            }
        }
        return maxWidth;
    }

    private int getTotalHeight(){
        int childCount = getChildCount();
        int totalHeight = 0;
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            totalHeight += childView.getMeasuredHeight();
        }
        return totalHeight;
    }
}
