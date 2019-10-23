package com.sdu.chy.chytest.interview.eventbus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sdu.chy.chytest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class EventBusFragment2 extends Fragment{

    private TextView tv;
    private Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView =  inflater.inflate(R.layout.fragment_eventbus_2, container, false);
        tv = (TextView) contentView.findViewById(R.id.tv_eb_2);
        btn = (Button) contentView.findViewById(R.id.btn_eb_2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MyEvent("来自页面2的消息"));
            }
        });
        return contentView;
    }

    @Subscribe
    public void onEvent(MyEvent myEvent){
        tv.setText("页面2收到了: " + myEvent.getMessage());
    }

}
