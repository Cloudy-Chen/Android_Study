package com.sdu.chy.chytest.LazyList;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdu.chy.chytest.R;
import com.sdu.chy.chytest.okHttptest.MovieAdapter;
import com.sdu.chy.chytest.okHttptest.MovieBaseAdapter;
import com.sdu.chy.chytest.okHttptest.MovieBean;
import com.sdu.chy.chytest.okHttptest.MovieCallback;
import com.sdu.chy.chytest.okHttptest.MovieResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LazyListActivity extends AppCompatActivity {

    private ListView lazy_list;
    private Context mContext;
    private List<MovieBean> movieList;
    private LazyAdapter lazyAdapter;

    public static final String URL = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
    public static final String URL_CACHE = "http://192.168.1.104:8080/chyWebTest/MovieServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lazy_list);
        mContext = this;
        initView();
        getMovieListByOkgo();

        movieList = new ArrayList<>();
    }

    private void initView(){
        lazy_list = (ListView)findViewById(R.id.lazy_list);
    }

    private void getMovieListByOkgo(){
        //String方式回调
        OkGo.<String>get(URL)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        Gson gson = new GsonBuilder().create();
                        JsonObject returnData = new JsonParser().parse(body).getAsJsonObject();
                        JsonArray returnArray = returnData.getAsJsonArray("trailers");
                        movieList = gson.fromJson(returnArray,new TypeToken<ArrayList<MovieBean>>(){}.getType());

                        lazyAdapter = new LazyAdapter(mContext,movieList);
                        lazy_list.setAdapter(lazyAdapter);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
