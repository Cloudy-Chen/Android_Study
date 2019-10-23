package com.sdu.chy.chytest.okHttptest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.sdu.chy.chytest.R;
import com.sdu.chy.chytest.sqlite.MyListAdapter;

import java.io.File;
import java.net.URL;
import java.text.NumberFormat;

public class OkgoDownloadActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_STORAGE = 0x01;

    Button OkgoFileDownload;
    TextView OkgoDownloadSize;
    TextView OkgoProgress;
    TextView OkgoSpeedNet;
    NumberProgressBar OkgoProgressDownload;
    NumberFormat numberFormat;
    Button OkgoShowpicBtn;
    ImageView OkgoShowpicImg;

    private static String picUrl = "http://www.sportshot.cn/file/goodsPic/2018111524-1.jpg";//图片地址
    private static String serverUrl = "http://192.168.1.104:8080/chyWebTest/FileUploadServlet";//服务器地址
    private static String downloadPath = "/sdcard/download/1.jpg";//Environment.getExternalStorageDirectory()+"download/1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okgo_download);

        initView();
    }

    void initView(){
        OkgoFileDownload = (Button)findViewById(R.id.btn_okgo_file_download);
        OkgoDownloadSize = (TextView)findViewById(R.id.txt_okgo_download_size);
        OkgoProgress = (TextView)findViewById(R.id.txt_okgo_progress);
        OkgoSpeedNet = (TextView)findViewById(R.id.txt_okgo_speed_net);
        OkgoProgressDownload = (NumberProgressBar)findViewById(R.id.progress_okgo_download);
        OkgoShowpicBtn = (Button)findViewById(R.id.btn_okgo_show_pic);
        OkgoShowpicImg = (ImageView)findViewById(R.id.img_okgo_show_pic);

        OkgoFileDownload.setOnClickListener(new MyClickListener());
        OkgoShowpicBtn.setOnClickListener(new MyClickListener());

        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(2);

        checkSDCardPermission();
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_okgo_file_download:
                    okgoFileDownload();
                    break;
                case R.id.btn_okgo_show_pic:
                    okgoShowDownloadImg();
                    break;
            }
        }
    }

    void okgoFileDownload(){
        OkGo.<File>get(picUrl)
                .tag(this)
                .execute(new FileCallback("1.jpg") {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        OkgoFileDownload.setText("正在下载中");
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        OkgoFileDownload.setText("下载完成");
                    }

                    @Override
                    public void onError(Response<File> response) {
                        OkgoFileDownload.setText("下载出错");
                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                        String downloadLength = Formatter.formatFileSize(getApplicationContext(), progress.currentSize);
                        String totalLength = Formatter.formatFileSize(getApplicationContext(), progress.totalSize);
                        OkgoDownloadSize.setText(downloadLength + "/" + totalLength);
                        String speed = Formatter.formatFileSize(getApplicationContext(), progress.speed);
                        OkgoSpeedNet.setText(String.format("%s/s", speed));
                        OkgoProgress.setText(numberFormat.format(progress.fraction));
                        OkgoProgressDownload.setMax(10000);
                        OkgoProgressDownload.setProgress((int) (progress.fraction * 10000));
                    }
                });
    }

    void okgoShowDownloadImg(){
        File mFile=new File(downloadPath);
        //若该文件存在
        if (mFile.exists()) {
            Bitmap bitmap= BitmapFactory.decodeFile(downloadPath);
            OkgoShowpicImg.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"没有找到下载文件",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(this);
    }



    /** 检查SD卡权限 */
    protected void checkSDCardPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //获取权限
            } else {
                Toast.makeText(this,"权限被禁止，无法下载文件！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
