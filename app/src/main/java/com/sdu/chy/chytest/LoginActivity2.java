package com.sdu.chy.chytest;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity2 extends AppCompatActivity {

    //区别两个目录
    /*
    1./data/data：
        (1)获取：context.getFileDir().getPath();
        (2)是一个应用程序的私有目录，只有当前应用有权访问并且读写，其他应用无权访问
        (3)一些安全性较高，占用app内存较小的文件存放该目录
    2./sdcard：
        (1)获取：Environment.getExternalStorageDirectory().getPath();
        (2)是一个外部存储目录，声明WRITE_EXTERNALSTORAGE权限便可读写sdcard目录
        (3)一些安全性不高，占内存较大的目录存放该目录
    */

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_info;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        et_username = (EditText) findViewById(R.id.test3_username);
        et_password = (EditText) findViewById(R.id.test3_password);
        btn_login = (Button)findViewById(R.id.test3_button1);
        btn_info = (Button)findViewById(R.id.test3_button2);

        btn_login.setOnClickListener(new MyClickListener());
        btn_info.setOnClickListener(new MyClickListener());

        mContext = this;

    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            switch (v.getId()){
                case R.id.test3_button1:
                    Boolean flag = new UserInfoUtil().saveUserInfo(username,password);
                    if(flag){
                        Toast.makeText(LoginActivity2.this,"登录成功",Toast.LENGTH_SHORT).show();
                        et_username.setText("");
                        et_password.setText("");
                    }
                    else Toast.makeText(LoginActivity2.this,"登录失败",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.test3_button2:
                    Map<String,String> res = new UserInfoUtil().getUserInfo();
                    et_username.setText(res.get("username"));
                    et_password.setText(res.get("password"));
                    break;
            }
        }
    }

    public class UserInfoUtil{

        //保存用户名和密码到SD卡
        public boolean saveUserInfo(String username,String password){
            try {
                String userInfo = username + "&&" + password;
                String path = "/storage/emulated/0";//制定保存路径
                String path2 = Environment.getExternalStorageDirectory().getPath();//通过Environment获得sd卡信息
                                                                                   //申请权限WRITE_EXTERNAL_STORAGE
                //存储前判断sd卡的剩余空间
                File sdCard_fileDir = Environment.getExternalStorageDirectory();
                long useableSpace = sdCard_fileDir.getUsableSpace();//527396864(<=503*1024*1024)
                String useableSpace_str = Formatter.formatFileSize(mContext,useableSpace);//503MB

                if(useableSpace < 1024*1024*200){
                    //剩余空间<200M
                    Toast.makeText(LoginActivity2.this,"剩余空间不足："+useableSpace_str,Toast.LENGTH_SHORT);
                    return false;
                }

                File file = new File(path2, "userInfo.txt");//创建file
                FileOutputStream fileOutputStream = new FileOutputStream(file);//创建文件写入流
                fileOutputStream.write(userInfo.getBytes());
                fileOutputStream.close();
                return true;
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            return false;
        }

        //从sd卡获取用户名密码并回显
        public Map<String,String> getUserInfo(){
            try {
                String path = "/storage/emulated/0";//制定保存路径
                String path2 = Environment.getExternalStorageDirectory().getPath();
                File file = new File(path2, "userInfo.txt");//创建file
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                String readline = bufferedReader.readLine();
                String[] split = readline.split("&&");
                HashMap<String,String> hashMap = new HashMap<String,String>();
                hashMap.put("username",split[0]);
                hashMap.put("password",split[1]);
                bufferedReader.close();
                fileInputStream.close();
                return hashMap;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }

}
