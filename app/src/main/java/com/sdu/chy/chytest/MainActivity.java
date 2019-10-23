package com.sdu.chy.chytest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sdu.chy.chytest.HttpClientLogin.LoginHttpClientActivity;
import com.sdu.chy.chytest.HttpUrlConnectionLogin.LoginNetActivity;
import com.sdu.chy.chytest.HttpUrlConnectionNews.NewsActivity;
import com.sdu.chy.chytest.LazyList.LazyListActivity;
import com.sdu.chy.chytest.UploadAndDownloadFile.UploadDownloadFileActivity;
import com.sdu.chy.chytest.asyncHttpClient.LoginAsyncHttpClientActivity;
import com.sdu.chy.chytest.fragment.FragmentTestActivity;
import com.sdu.chy.chytest.interview.competition.CompetitionActivity;
import com.sdu.chy.chytest.interview.eventbus.EventBusTestActivity;
import com.sdu.chy.chytest.myView.myAnimation.MyAnimationActivity;
import com.sdu.chy.chytest.myView.myViewUtils.MyViewActivity;
import com.sdu.chy.chytest.ndkTest.JniTestActivity;
import com.sdu.chy.chytest.okHttptest.OkHttpMainActivity;
import com.sdu.chy.chytest.recyclerViewTest.RecyclerViewActivity;
import com.sdu.chy.chytest.sqlite.SqliteActivity;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CODE=8;

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btn10;
    private Button btn11;
    private Button btn12;
    private Button btn13;
    private Button btn14;
    private Button btn15;
    private Button btn16;
    private Button btn17;
    private Button btn18;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);
        btn5 = (Button)findViewById(R.id.button5);
        btn6 = (Button)findViewById(R.id.button6);
        btn7 = (Button)findViewById(R.id.button7);
        btn8 = (Button)findViewById(R.id.button8);
        btn9 = (Button)findViewById(R.id.button9);
        btn10 = (Button)findViewById(R.id.button10);
        btn11 = (Button)findViewById(R.id.button11);
        btn12 = (Button)findViewById(R.id.button12);
        btn13 = (Button)findViewById(R.id.button13);
        btn14 = (Button)findViewById(R.id.button14);
        btn15 = (Button)findViewById(R.id.button15);
        btn16 = (Button)findViewById(R.id.button16);
        btn17 = (Button)findViewById(R.id.button17);
        btn18 = (Button)findViewById(R.id.button18);

        btn1.setOnClickListener(new MyClickListener());
        btn2.setOnClickListener(new MyClickListener());
        btn3.setOnClickListener(new MyClickListener());
        btn4.setOnClickListener(new MyClickListener());
        btn5.setOnClickListener(new MyClickListener());
        btn6.setOnClickListener(new MyClickListener());
        btn7.setOnClickListener(new MyClickListener());
        btn8.setOnClickListener(new MyClickListener());
        btn9.setOnClickListener(new MyClickListener());
        btn10.setOnClickListener(new MyClickListener());
        btn11.setOnClickListener(new MyClickListener());
        btn12.setOnClickListener(new MyClickListener());
        btn13.setOnClickListener(new MyClickListener());
        btn14.setOnClickListener(new MyClickListener());
        btn15.setOnClickListener(new MyClickListener());
        btn16.setOnClickListener(new MyClickListener());
        btn17.setOnClickListener(new MyClickListener());
        btn18.setOnClickListener(new MyClickListener());


        requestGranted();

    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.button1:
                    Intent intent1 = new Intent(MainActivity.this,DialActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.button2:
                    Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent2);
                    break;
                case R.id.button3:
                    Intent intent3 = new Intent(MainActivity.this,LoginActivity2.class);
                    startActivity(intent3);
                    break;
                case R.id.button4:
                    Intent intent4 = new Intent(MainActivity.this,LoginActivity3.class);
                    startActivity(intent4);
                    break;
                case R.id.button5:
                    Intent intent5 = new Intent(MainActivity.this,SqliteActivity.class);
                    startActivity(intent5);
                    break;
                case R.id.button6:
                    Intent intent6 = new Intent(MainActivity.this,NewsActivity.class);
                    startActivity(intent6);
                    break;
                case R.id.button7:
                    Intent intent7 = new Intent(MainActivity.this, LoginNetActivity.class);
                    startActivity(intent7);
                    break;
                case R.id.button8:
                    Intent intent8 = new Intent(MainActivity.this, LoginHttpClientActivity.class);
                    startActivity(intent8);
                    break;
                case R.id.button9:
                    Intent intent9 = new Intent(MainActivity.this, LoginAsyncHttpClientActivity.class);
                    startActivity(intent9);
                    break;
                case R.id.button10:
                    Intent intent10 = new Intent(MainActivity.this, UploadDownloadFileActivity.class);
                    startActivity(intent10);
                    break;
                case R.id.button11:
                    Intent intent11 = new Intent(MainActivity.this, OkHttpMainActivity.class);
                    startActivity(intent11);
                    break;
                case R.id.button12:
                    Intent intent12 = new Intent(MainActivity.this, LazyListActivity.class);
                    startActivity(intent12);
                    break;
                case R.id.button13:
                    Intent intent13 = new Intent(MainActivity.this, RecyclerViewActivity.class);
                    startActivity(intent13);
                    break;
                case R.id.button14:
                    Intent intent14 = new Intent(MainActivity.this, MyAnimationActivity.class);
                    startActivity(intent14);
                    break;
                case R.id.button15:
                    Intent intent15 = new Intent(MainActivity.this, JniTestActivity.class);
                    startActivity(intent15);
                    break;
                case R.id.button16:
                    Intent intent16 = new Intent(MainActivity.this, FragmentTestActivity.class);
                    startActivity(intent16);
                    break;
                case R.id.button17:
                    Intent intent17 = new Intent(MainActivity.this, CompetitionActivity.class);
                    startActivity(intent17);
                    break;
                case R.id.button18:
                    Intent intent18 = new Intent(MainActivity.this, EventBusTestActivity.class);
                    startActivity(intent18);
                    break;
            }
        }
    }

    private void requestGranted(){

        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {

                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.INTERNET,

                }
        );
        // 如果这3个权限全都拥有, 则直接执行备份代码
        if (isAllGranted) {
            //doBackup();

            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[] {

                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.SYSTEM_ALERT_WINDOW,
                        Manifest.permission.INTERNET,
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行备份代码
                //doBackup();


            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                //openAppDetails();
            }
        }

    }
}
