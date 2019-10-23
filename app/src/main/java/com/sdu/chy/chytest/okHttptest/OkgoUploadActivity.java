package com.sdu.chy.chytest.okHttptest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.sdu.chy.chytest.R;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;

public class OkgoUploadActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_STORAGE = 0x01;

    Button OkgoFileUpload;
    TextView OkgoUploadSize;
    TextView OkgoProgress;
    TextView OkgoSpeedNet;
    NumberProgressBar OkgoProgressUpload;
    NumberFormat numberFormat;

    private static String picUrl = "http://www.sportshot.cn/file/goodsPic/2018111524-1.jpg";//图片地址
    private static String serverUrl = "http://192.168.1.104:8080/chyWebTest/FileUploadServlet";//服务器地址
    private static String downloadPath = "/sdcard/download/1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okgo_upload);

        initView();
    }

    void initView(){
        OkgoFileUpload = (Button)findViewById(R.id.btn_okgo_file_upload);
        OkgoUploadSize = (TextView)findViewById(R.id.txt_okgo_upload_size);
        OkgoProgress = (TextView)findViewById(R.id.txt_okgo_progress_upload);
        OkgoSpeedNet = (TextView)findViewById(R.id.txt_okgo_speed_net_upload);
        OkgoProgressUpload = (NumberProgressBar)findViewById(R.id.progress_okgo_upload);

        OkgoFileUpload.setOnClickListener(new MyClickListener());

        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);

    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_okgo_file_upload:
                    okgoFileUpload();
                    break;
            }
        }
    }

    public void okgoFileUpload() {
        //上传图片路径
        File uploadFile=new File(downloadPath);
        if(uploadFile.exists()){
            Log.i("exists", "okgoFileUpload: File Exists");
        }else{
            Log.i("not exists", "okgoFileUpload: File not Exists");
        }

        //拼接参数
        OkGo.<String>post(serverUrl)//
                .tag(this)//
                .params("uploadFile",uploadFile)
                .execute( new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        OkgoFileUpload.setText("上传完成");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        OkgoFileUpload.setText("上传出错");
                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                        String downloadLength = Formatter.formatFileSize(getApplicationContext(), progress.currentSize);
                        String totalLength = Formatter.formatFileSize(getApplicationContext(), progress.totalSize);
                        OkgoUploadSize.setText(downloadLength + "/" + totalLength);
                        String speed = Formatter.formatFileSize(getApplicationContext(), progress.speed);
                        OkgoSpeedNet.setText(String.format("%s/s", speed));
                        OkgoProgress.setText(numberFormat.format(progress.fraction));
                        OkgoProgressUpload.setMax(10000);
                        OkgoProgressUpload.setProgress((int) (progress.fraction * 10000));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }
}
