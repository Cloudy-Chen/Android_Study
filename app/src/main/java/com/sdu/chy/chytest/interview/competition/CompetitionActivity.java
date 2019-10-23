package com.sdu.chy.chytest.interview.competition;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdu.chy.chytest.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CompetitionActivity extends AppCompatActivity {

    static String url = "http://192.168.1.109:8080/json/competition_json.json";

    OkHttpClient okHttpClient;
    ArrayList<Competition> competitionList = new ArrayList();

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
        getCompetitionListFromNet();
        //initView();
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
        competitionAdapter = new CompetitionAdapter(this,competitionList);
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
        okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("FAIL", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                competitionList = new Gson().fromJson(result,new TypeToken<ArrayList<Competition>>(){}.getType());
            }
        });
    }
}
