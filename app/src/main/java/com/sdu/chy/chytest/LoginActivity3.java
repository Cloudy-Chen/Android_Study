package com.sdu.chy.chytest;

import android.content.Context;
import android.content.SharedPreferences;
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

public class LoginActivity3 extends AppCompatActivity {

    /*
    SharedPreferences简称Sp(后面都会称Sp)，是一种轻量级的数据存储方式,采用Key/value的方式进行映射，
    最终会在手机的/data/data/package_name/shared_prefs/目录下以xml的格式存在。
    */

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private Button btn_info;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        et_username = (EditText) findViewById(R.id.test4_username);
        et_password = (EditText)findViewById(R.id.test4_password);
        btn_login = (Button)findViewById(R.id.test4_button1);
        btn_info = (Button)findViewById(R.id.test4_button2);

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
                case R.id.test4_button1:
                    Boolean flag = new UserInfoUtil().saveUserInfo(username,password);
                    if(flag){
                        Toast.makeText(LoginActivity3.this,"登录成功",Toast.LENGTH_SHORT).show();
                        et_username.setText("");
                        et_password.setText("");
                    }
                    else Toast.makeText(LoginActivity3.this,"登录失败",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.test4_button2:
                    Map<String,String> res = new UserInfoUtil().getUserInfo();
                    et_username.setText(res.get("username"));
                    et_password.setText(res.get("password"));
                    break;
            }
        }
    }

    public class UserInfoUtil{

        //用sharedPreferences存储数据
        public boolean saveUserInfo(String username,String password){

                String userInfo = username + "&&" + password;

                //1.通过context对象创建一个sharedPreferences对象
                //存储到/data/data/package_name/shared_prefs/userinfo.txt.xml(xml文件)
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("userinfo.txt",mContext.MODE_PRIVATE);
                //2.通过sharedPreferences对象获取一个Editor对象
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //3.向Editor中添加数据
                editor.putString("username",username);
                editor.putString("password",password);
                //4.提交editor对象
                editor.commit();
                return true;
        }

        //用sharedPreferences读取数据
        public Map<String,String> getUserInfo(){
            try {
                //通过context对象创建一个sharedPreference对象
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("userinfo.txt",mContext.MODE_PRIVATE);
                //通过sharedPreference获取存放的数据
                //key:存放数据时用的Key，defaultValue默认值，根据业务需求写
                String username = sharedPreferences.getString("username","");
                String password = sharedPreferences.getString("password","");
                HashMap<String,String> hashMap = new HashMap<String, String>();
                hashMap.put("username",username);
                hashMap.put("password",password);
                return hashMap;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }

}
