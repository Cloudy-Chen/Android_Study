package com.lzy.demo.okgo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.lzy.demo.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpTestActivity extends Activity {

    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_test);
        okHttpTest();
    }

    public void okHttpTest(){
        //采用构建者模式
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().
                url("https://github.com/cozing").
                build();
        Call call = client.newCall(request);
        try {
            //1.同步请求调用的方法是call.execute()，内部采用的是线程阻塞（一直等待直到线程返回结果）方式直接将结果返回到Response
            Response response = call.execute();
            //2.异步请求调用的方法是call.enqueue(Callback callback)，该方法需要传入一个Callback等待结果回调的接口
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.w("cozing", "请求失败");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.w("cozing", "请求成功");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
