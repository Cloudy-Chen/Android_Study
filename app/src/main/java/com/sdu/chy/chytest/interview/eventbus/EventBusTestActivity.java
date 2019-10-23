package com.sdu.chy.chytest.interview.eventbus;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sdu.chy.chytest.R;

public class EventBusTestActivity extends AppCompatActivity{

    private FrameLayout fl;
    private Button btn1;
    private Button btn2;
    private Button btn3;

    private EventBusFragment1 fragment1;
    private EventBusFragment2 fragment2;
    private EventBusFragment3 fragment3;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);
        init();
    }

    public void init(){
        initView();
        initFragment();
    }

    public void initView(){
        fl = (FrameLayout) findViewById(R.id.frame_eventbus);
        btn1 = (Button) findViewById(R.id.btn1_eventbus);
        btn2 = (Button) findViewById(R.id.btn2_eventbus);
        btn3 = (Button) findViewById(R.id.btn3_eventbus);
    }

    public void initFragment(){
        fragmentManager = getSupportFragmentManager();
    }

    public void onClick(View v) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.btn1_eventbus:
                if(fragment1 == null){
                    fragment1 = new EventBusFragment1();
                    fragmentTransaction.add(R.id.frame_eventbus,fragment1);
                }
                hideFragment();
                fragmentTransaction.show(fragment1);
                fragmentTransaction.commit();
                break;
            case R.id.btn2_eventbus:
                if(fragment2 == null){
                    fragment2 = new EventBusFragment2();
                    fragmentTransaction.add(R.id.frame_eventbus,fragment2);
                }
                hideFragment();
                fragmentTransaction.show(fragment2);
                fragmentTransaction.commit();
                break;
            case R.id.btn3_eventbus:
                if(fragment3 == null){
                    fragment3 = new EventBusFragment3();
                    fragmentTransaction.add(R.id.frame_eventbus,fragment3);
                }
                hideFragment();
                fragmentTransaction.show(fragment3);
                fragmentTransaction.commit();
                break;
        }
    }

    public void hideFragment(){
        if(fragment1 != null)fragmentTransaction.hide(fragment1);
        if(fragment2 != null)fragmentTransaction.hide(fragment2);
        if(fragment3 != null)fragmentTransaction.hide(fragment3);
    }
}
