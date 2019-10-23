package com.sdu.chy.chytest;

import android.app.Application;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.lzy.okgo.OkGo;


public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);

    }
}

