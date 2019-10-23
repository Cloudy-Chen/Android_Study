package com.sdu.chy.chytest.okHttptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.utils.OkLogger;
import com.sdu.chy.chytest.DialActivity;
import com.sdu.chy.chytest.HttpClientLogin.LoginHttpClientActivity;
import com.sdu.chy.chytest.HttpUrlConnectionLogin.LoginNetActivity;
import com.sdu.chy.chytest.HttpUrlConnectionNews.NewsActivity;
import com.sdu.chy.chytest.LoginActivity;
import com.sdu.chy.chytest.LoginActivity2;
import com.sdu.chy.chytest.LoginActivity3;
import com.sdu.chy.chytest.MainActivity;
import com.sdu.chy.chytest.R;
import com.sdu.chy.chytest.UploadAndDownloadFile.UploadDownloadFileActivity;
import com.sdu.chy.chytest.asyncHttpClient.LoginAsyncHttpClientActivity;
import com.sdu.chy.chytest.sqlite.SqliteActivity;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.Call;

public class OkgoPostGetActivity extends AppCompatActivity {

    public static final String URL = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

    private Button btn_get;
    private Button btn_post;
    private TextView txt_status;
    private TextView txt_data;
    private MyClickListener myClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okgo_post_get);

        btn_get = (Button) findViewById(R.id.btn_get);
        btn_post = (Button) findViewById(R.id.btn_post);
        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_data = (TextView) findViewById(R.id.txt_data);

        btn_get.setOnClickListener(new MyClickListener());
        btn_post.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_get:
                    okHttpTestByGet();
                    break;
                case R.id.btn_post:
                    okHttpTestByPost();
                    break;
            }
        }
    }

    private void okHttpTestByGet(){
        //String方式回调
        OkGo.<String>get(URL)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Call call = response.getRawCall();
                        String body = response.body();
                        if (call != null) {
                            txt_status.setText("请求成功  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
                            txt_data.setText(body);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Call call = response.getRawCall();
                        String body = response.body();
                        if (call != null) {
                            txt_status.setText("请求失败  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
                            txt_data.setText(body);
                        }
                    }
                });
    }

    private void okHttpTestByPost(){
        //Json方式回调
        OkGo.<String>post(URL)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Call call = response.getRawCall();
                        String body = response.body();
                        if (call != null) {
                            txt_status.setText("请求成功  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
                            txt_data.setText(body);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        Call call = response.getRawCall();
                        String body = response.body();
                        if (call != null) {
                            txt_status.setText("请求失败  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
                            txt_data.setText(body);
                        }
                    }

                });
    }
}
