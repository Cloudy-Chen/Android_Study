package com.sdu.chy.chytest.okHttptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdu.chy.chytest.LoginActivity;
import com.sdu.chy.chytest.LoginActivity2;
import com.sdu.chy.chytest.LoginActivity3;
import com.sdu.chy.chytest.MainActivity;
import com.sdu.chy.chytest.R;
import com.sdu.chy.chytest.sqlite.SqliteActivity;

public class OkHttpMainActivity extends AppCompatActivity {

    private Button btn_post_get;
    private Button btn_img;
    private Button btn_json;
    private Button btn_send;
    private Button btn_file_download;
    private Button btn_file_upload;
    private MyClickListener myClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_main);
        btn_post_get = (Button)findViewById(R.id.btn_post_get);
        btn_img = (Button)findViewById(R.id.btn_img);
        btn_json = (Button)findViewById(R.id.btn_json);
        btn_send = (Button)findViewById(R.id.btn_send);
        btn_file_download = (Button)findViewById(R.id.btn_file_download);
        btn_file_upload = (Button)findViewById(R.id.btn_file_upload);

        btn_post_get.setOnClickListener(new MyClickListener());
        btn_img.setOnClickListener(new MyClickListener());
        btn_json.setOnClickListener(new MyClickListener());
        btn_send.setOnClickListener(new MyClickListener());
        btn_file_download.setOnClickListener(new MyClickListener());
        btn_file_upload.setOnClickListener(new MyClickListener());

    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_post_get:
                    Intent intent1 = new Intent(OkHttpMainActivity.this,OkgoPostGetActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.btn_img:
                    Intent intent2 = new Intent(OkHttpMainActivity.this,OkgoImageActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.btn_json:
                    Intent intent3 = new Intent(OkHttpMainActivity.this,OkHttpJsonActivity.class);
                    startActivity(intent3);
                    break;
                case R.id.btn_send:
                    Intent intent4 = new Intent(OkHttpMainActivity.this,OkgoSendActivity.class);
                    startActivity(intent4);
                    break;
                case R.id.btn_file_download:
                    Intent intent5 = new Intent(OkHttpMainActivity.this,OkgoDownloadActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.btn_file_upload:
                    Intent intent6 = new Intent(OkHttpMainActivity.this,OkgoUploadActivity.class);
                    startActivity(intent6);
                    break;
            }
        }
    }

}
