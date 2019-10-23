package com.sdu.chy.chytest.myView.myAnimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.ErrnoException;
import android.system.Os;
import android.system.StructStat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sdu.chy.chytest.MainActivity;
import com.sdu.chy.chytest.MainApplication;
import com.sdu.chy.chytest.R;
import com.sdu.chy.chytest.sqlite.UserDao;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

public class MyAnimationActivity extends AppCompatActivity {

    private ImageView myAnimationView1;
    private ImageView myAnimationView2;
    private ImageView myAnimationView3;
    private ImageView myAnimationView4;
    private ImageView myAnimationView5;

    private Button rotateBtn;
    private Button alphaBtn;
    private Button setBtn;
    private Button trembleBtn;
    private Button shadowBtn;
    private Button colorChangeBtn;

    private int mCurrentRed = -1;
    private int mCurrentGreen = -1;
    private int mCurrentBlue = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_animation);

        myAnimationView1 = (ImageView) findViewById(R.id.my_animation_view1);
        myAnimationView2 = (ImageView) findViewById(R.id.my_animation_view2);
        myAnimationView3 = (ImageView) findViewById(R.id.my_animation_view3);
        myAnimationView4 = (ImageView) findViewById(R.id.my_animation_view4);
        myAnimationView5 = (ImageView) findViewById(R.id.my_animation_view5);

        rotateBtn = (Button) findViewById(R.id.rotate_btn);
        alphaBtn = (Button) findViewById(R.id.alpha_btn);
        setBtn = (Button) findViewById(R.id.set_btn);
        trembleBtn = (Button) findViewById(R.id.tremble_btn);
        shadowBtn = (Button) findViewById(R.id.shadow_btn);
        colorChangeBtn = (Button) findViewById(R.id.color_change_btn);

        initListener();
    }

    private void initListener(){
        rotateBtn.setOnClickListener(new MyClickListener());
        alphaBtn.setOnClickListener(new MyClickListener());
        setBtn.setOnClickListener(new MyClickListener());
        trembleBtn.setOnClickListener(new MyClickListener());
        shadowBtn.setOnClickListener(new MyClickListener());
        colorChangeBtn.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.rotate_btn:
                    RotateAnimation();break;
                case R.id.alpha_btn:
                    AlpahAnimation();break;
                case R.id.set_btn:
                    SetAnimation();break;
                case R.id.tremble_btn:
                    TrembleAnimation();break;
                case R.id.shadow_btn:
                    ShadowAnimator();break;
                case R.id.color_change_btn:
                    ColorChangeAnimator("#0000ff", "#ff0000");break;
            }
        }
    }

    //实现抖动动画
    private void TrembleAnimation() {
        QQTrembleAnimation tremble = new QQTrembleAnimation();
        tremble.setRepeatCount(2);//重复次数2次（不包含第一次）
        myAnimationView1.startAnimation(tremble);
    }

    //实现旋转动画
    private void RotateAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(myAnimationView1, "rotation", 0f, 360f);
        anim.setDuration(10000);
        anim.start();
    }

    //实现渐变动画
    private void AlpahAnimation() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(myAnimationView1, "alpha", 1.0f, 0.8f, 0.6f, 0.4f, 0.2f, 0.0f);
        anim.setRepeatCount(-1);
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        anim.setDuration(2000);
        anim.start();
    }

    //实现组合动画
    private void SetAnimation() {
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(myAnimationView1, "alpha", 1.0f, 0.5f, 0.8f, 1.0f);
        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(myAnimationView1, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(myAnimationView1, "scaleY", 0.0f, 2.0f);
        ObjectAnimator rotateAnim = ObjectAnimator.ofFloat(myAnimationView1, "rotation", 0, 360);
        ObjectAnimator transXAnim = ObjectAnimator.ofFloat(myAnimationView1, "translationX", 100, 400);
        ObjectAnimator transYAnim = ObjectAnimator.ofFloat(myAnimationView1, "translationY", 100, 750);
        AnimatorSet set = new AnimatorSet();
//      set.playTogether(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);//同时播放动画
        set.playSequentially(alphaAnim, scaleXAnim, scaleYAnim, rotateAnim, transXAnim, transYAnim);//按序播放动画
        set.setDuration(3000);
        set.start();
    }

    //实现影子动画
    private void ShadowAnimator() {
        //创建ObjectAnimator属性对象，参数为动画要设置的View对象、动画属性、属性值
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(myAnimationView1,"alpha",0,1);//渐变
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(myAnimationView2,"translationY",0,200F);//上移
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(myAnimationView3,"translationY",0,-200F);//下移
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(myAnimationView4,"translationX",0,200F);//右移
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(myAnimationView5,"translationX",0,-200F);//左移

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(3000);
        animatorSet.setInterpolator(new BounceInterpolator());//弹跳效果插值器
        animatorSet.playTogether(animator1,animator2,animator3,animator4,animator5);//组合动画共同播放

        animatorSet.start();
    }

    //6秒内把一个view控件的背景颜色从从红色渐变到蓝色
    private void ColorChangeAnimator(final String start,final String end) {
        final ValueAnimator animator = ValueAnimator.ofFloat(0,100f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //获取当前动画的进度值,1~100
                float currentValue = (float) valueAnimator.getAnimatedValue();
                //获取当前动画的百分比,0~1
                float fraction = valueAnimator.getAnimatedFraction();
                //调用evaluateForColor，根据百分比计算出对应的颜色
                String colorResult = evaluateForColor(fraction,start,end);
                //通过Color.parseColor解析字符串颜色值，传给ColorDrawable
                ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(colorResult));
                myAnimationView1.setBackground(colorDrawable);
                //Android视图机制中在主线程中调用它,用于触发视图的绘制刷新
                myAnimationView1.invalidate();
            }
        });
        animator.setDuration(6*1000);
        animator.start();
    }

    /**
     * evaluateForColor()计算颜色值并返回
     */
    private String evaluateForColor(float fraction, String startValue, String endValue) {

        String startColor = startValue;
        String endColor = endValue;
        int startRed = Integer.parseInt(startColor.substring(1, 3), 16);
        int startGreen = Integer.parseInt(startColor.substring(3, 5), 16);
        int startBlue = Integer.parseInt(startColor.substring(5, 7), 16);
        int endRed = Integer.parseInt(endColor.substring(1, 3), 16);
        int endGreen = Integer.parseInt(endColor.substring(3, 5), 16);
        int endBlue = Integer.parseInt(endColor.substring(5, 7), 16);

        // 初始化颜色的值
        if (mCurrentRed == -1) {
            mCurrentRed = startRed;
        }
        if (mCurrentGreen == -1) {
            mCurrentGreen = startGreen;
        }
        if (mCurrentBlue == -1) {
            mCurrentBlue = startBlue;
        }

        // 计算初始颜色和结束颜色之间的差值
        int redDiff = Math.abs(startRed - endRed);
        int greenDiff = Math.abs(startGreen - endGreen);
        int blueDiff = Math.abs(startBlue - endBlue);
        int colorDiff = redDiff + greenDiff + blueDiff;
        if (mCurrentRed != endRed) {
            mCurrentRed = getCurrentColor(startRed, endRed, colorDiff, 0, fraction);
        } else if (mCurrentGreen != endGreen) {
            mCurrentGreen = getCurrentColor(startGreen, endGreen, colorDiff, redDiff, fraction);
        } else if (mCurrentBlue != endBlue) {
            mCurrentBlue = getCurrentColor(startBlue, endBlue, colorDiff,
                    redDiff + greenDiff, fraction);
        }

        // 将计算出的当前颜色的值组装返回
        String currentColor = "#" + getHexString(mCurrentRed)
                + getHexString(mCurrentGreen) + getHexString(mCurrentBlue);
        return currentColor;
    }

    /**
     * 根据fraction值来计算当前的颜色。
     */
    private int getCurrentColor(int startColor, int endColor, int colorDiff,
                                int offset, float fraction) {
        int currentColor;
        if (startColor > endColor) {
            currentColor = (int) (startColor - (fraction * colorDiff - offset));
            if (currentColor < endColor) {
                currentColor = endColor;
            }
        } else {
            currentColor = (int) (startColor + (fraction * colorDiff - offset));
            if (currentColor > endColor) {
                currentColor = endColor;
            }
        }
        return currentColor;
    }

    /**
     * 将10进制颜色值转换成16进制。
     */
    private String getHexString(int value) {
        String hexString = Integer.toHexString(value);
        if (hexString.length() == 1) {
            hexString = "0" + hexString;
        }
        return hexString;
    }
}

