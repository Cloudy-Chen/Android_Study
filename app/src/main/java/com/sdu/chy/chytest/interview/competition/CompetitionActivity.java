package com.sdu.chy.chytest.interview.competition;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.sdu.chy.chytest.R;

import java.util.ArrayList;
import java.util.List;

public class CompetitionActivity extends AppCompatActivity {

    ArrayList<Competition> dataList = new ArrayList();
    RefreshListView refreshListView;
    CompetitionAdapter competitionAdapter;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        init();
    }

    public void init(){
        initView();
        getCompetitionListFromNet();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch ((Integer) msg.obj){
                    case 1:
                        refreshListView.onRefreshComplete();break;
                    case 2:
                        refreshListView.onLoadComplete();break;
                }
                competitionAdapter.notifyDataSetChanged();
            }
        };
    }

    public void initView(){
        refreshListView = (RefreshListView) findViewById(R.id.listview_refresh);
        competitionAdapter = new CompetitionAdapter(this,dataList);
        refreshListView.setAdapter(competitionAdapter);
        refreshListView.setRefreshListener(new RefreshListView.RefreshingListener() {
            @Override
            public void refresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            // 模拟刷新，休眠2秒
                            sleep(2000);
                            Message msg = Message.obtain();
                            msg.obj = 1;
                            mHandler.sendMessage(msg);
                        }catch (Exception e){e.printStackTrace();}
                    }
                }.start();
            }

            @Override
            public void load(){
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(2000);
                            Message msg = Message.obtain();
                            msg.obj = 2;
                            mHandler.sendMessage(msg);
                        }catch (Exception e){e.printStackTrace();}
                    }
                }.start();
            }
        });
    }

    public void getCompetitionListFromNet(){

    }
}
