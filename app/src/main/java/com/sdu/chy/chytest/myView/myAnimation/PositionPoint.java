package com.sdu.chy.chytest.myView.myAnimation;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.sdu.chy.chytest.R;

public class PositionPoint {

    private float x;
    private float y;

    public PositionPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
