package com.sdu.chy.chytest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_info;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;//context上下文、环境，应用环境的全局信息接口，常设为全局变量，可以获取应用程序的全局信息
                        //context.getFileDir(),getAssets(),getResources(),getPackage()

        et_username = (EditText) findViewById(R.id.test2_username);
        et_password = (EditText) findViewById(R.id.test2_password);
        btn_login = (Button)findViewById(R.id.test2_button1);
        btn_info = (Button)findViewById(R.id.test2_button2);

        btn_login.setOnClickListener(new MyClickListener());
        btn_info.setOnClickListener(new MyClickListener());

    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            switch (v.getId()){
                case R.id.test2_button1:
                    Boolean flag = new UserInfoUtil().saveUserInfo(username,password);
                    if(flag){
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        et_username.setText("");
                        et_password.setText("");
                    }
                    else Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.test2_button2:
                    Map<String,String> res = new UserInfoUtil().getUserInfo();
                    et_username.setText(res.get("username"));
                    et_password.setText(res.get("password"));
                    break;
            }
        }
    }

    public class UserInfoUtil{

        //保存用户名和密码到私有目录（本地）
        public boolean saveUserInfo(String username,String password){
            try {
                String userInfo = username + "&&" + password;
                String path = "/data/user/0/com.sdu.chy.chytest/files";//制定保存路径
                String path2 = mContext.getFilesDir().getPath();//通过context获得应用私有目录

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

        //获取用户名密码并回显
        public Map<String,String> getUserInfo(){
            try {
                String path = "/data/user/0/com.sdu.chy.chytest/files";//制定保存路径
                String path2 = mContext.getFilesDir().getPath();
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
