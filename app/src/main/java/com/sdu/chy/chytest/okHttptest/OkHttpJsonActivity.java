package com.sdu.chy.chytest.okHttptest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;

public class OkHttpJsonActivity extends AppCompatActivity {

    private ListView lv_json;
    private Button btn_json_net;
    private Button btn_json_cache;
    private Context mContext;
    private List<MovieBean> movieList;
    private MovieBaseAdapter movieBaseAdapter;
    private MovieAdapter movieAdapter;

    private boolean isInitCache;

    public static final String URL = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
    public static final String URL_CACHE = "http://192.168.1.104:8080/chyWebTest/MovieServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_json);

        lv_json = (ListView) findViewById(R.id.lv_json);
        btn_json_net = (Button)findViewById(R.id.btn_json_net);
        btn_json_cache = (Button)findViewById(R.id.btn_json_cache);

        mContext = this;

        btn_json_net.setOnClickListener(new MyClickListener());
        btn_json_cache.setOnClickListener(new MyClickListener());
        movieList = new ArrayList<>();
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_json_net:
                    getMovieListByOkgo();
                    break;
                case R.id.btn_json_cache:
                    getMovieListByCache();
                    break;
            }
        }
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

                        movieAdapter = new MovieAdapter(mContext,movieList);
                        lv_json.setAdapter(movieAdapter);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getMovieListByCache(){
        //String方式回调
        Date date = new Date();
        String d = new SimpleDateFormat("yyyy-MM-dd").format(date);

        OkGo.<MovieResponse<List<MovieBean>>>get(URL_CACHE)//
                .cacheKey("movieList" + d)       //由于该fragment会被复用,必须保证key唯一,否则数据会发生覆盖
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)  //缓存模式先使用缓存,然后使用网络数据
                .execute(new MovieCallback<MovieResponse<List<MovieBean>>>() {
                    @Override
                    public void onSuccess(Response<MovieResponse<List<MovieBean>>> response) {
                        Log.i("Net", "onNetSuccess" );
                        List<MovieBean> results = response.body().movieList;
                        if (results != null) {
                            movieAdapter = new MovieAdapter(mContext,results);
                            lv_json.setAdapter(movieAdapter);
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<MovieResponse<List<MovieBean>>> response) {
                        Log.i("Cache", "onCacheSuccess" );
                        List<MovieBean> results = response.body().movieList;
                        if (results != null) {
                            movieAdapter = new MovieAdapter(mContext,results);
                            lv_json.setAdapter(movieAdapter);
                        }
                    }

                    @Override
                    public void onError(Response<MovieResponse<List<MovieBean>>> response) {
                        //网络请求失败的回调,一般会弹个Toast
                        Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                    }

                });
    }

}
