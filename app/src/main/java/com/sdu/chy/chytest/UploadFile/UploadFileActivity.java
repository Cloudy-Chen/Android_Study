package com.sdu.chy.chytest.UploadFile;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sdu.chy.chytest.R;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class UploadFileActivity extends AppCompatActivity {

    private static String picUrl = "http://www.sportshot.cn/file/goodsPic/2018111524-1.jpg";//图片地址
    private static String serverUrl = "http://192.168.1.104:8080/chyWebTest/FileUploadServlet";//服务器地址

    private Button btn_upload;
    private Button btn_download;
    private TextView file_text;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);

        mContext = this;

        btn_upload = (Button)findViewById(R.id.file_upload);
        btn_download = (Button)findViewById(R.id.file_download);
        file_text = (TextView) findViewById(R.id.file_text);

        btn_upload.setOnClickListener(new MyClickListener());
        btn_download.setOnClickListener(new MyClickListener());

    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

                switch (v.getId()){
                case R.id.file_upload:

                    File file = new File(Environment.getExternalStorageDirectory().getPath()+"/123.jpg");
                    if(file.exists()) {
                        //判断是否有此文件
                        Map<String, Object> map = new HashMap<>();
                        map.put("uid", "1653");
                        map.put("file", file);

                        OkHttpUtils.getInstance().uploadFile(serverUrl, map, new CallBack() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(UploadFileActivity.this,"上传成功",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed(Exception e) {
                                Toast.makeText(UploadFileActivity.this,"上传失败:"+e,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    break;
                case R.id.file_download:

                    String sdcardPath = Environment.getExternalStorageDirectory().getPath()+"/123.jpg";
                    File file2 = new File(sdcardPath);

                    OkHttpUtils.getInstance().downloadFile(file2 , picUrl , new CallBackPro() {

                        @Override
                        public void onSuccess(Object o) {
                            String filePath = o.toString();
                            Toast.makeText(UploadFileActivity.this,"下载成功!\n文件位置为："+filePath,Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailed(Exception e) {
                            Toast.makeText(UploadFileActivity.this,"下载失败",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onProgressBar(Long i) {
                            String progress = i.toString()+"%";
                            file_text.setText(progress);
                        }

                    });
                    break;
            }
        }
    }

}
