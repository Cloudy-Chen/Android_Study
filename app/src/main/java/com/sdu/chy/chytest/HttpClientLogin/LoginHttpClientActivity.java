package com.sdu.chy.chytest.HttpClientLogin;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdu.chy.chytest.HttpUrlConnectionLogin.LoginUtil;
import com.sdu.chy.chytest.HttpUrlConnectionNews.NewsAdapter;
import com.sdu.chy.chytest.R;

import java.util.List;

public class LoginHttpClientActivity extends AppCompatActivity {

    /*
    网络post,get实现登录案例
    */

    private Button btn_get_login;
    private Button btn_post_login;

    private Context mContext;

    private EditText et_username;
    private EditText et_password;

    private LoginHttpClientUtil loginHttpClientUtil;

    private MyClickListener myClickListener;

    private String username;
    private String password;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_httpclient);

        et_username = (EditText) findViewById(R.id.login_username_httpclient);
        et_password = (EditText) findViewById(R.id.login_password_httpclient);
        btn_get_login = (Button)findViewById(R.id.login_get_button);
        btn_post_login = (Button)findViewById(R.id.login_post_button);

        mContext = this;

        myClickListener = new MyClickListener();

        btn_get_login.setOnClickListener(myClickListener);
        btn_post_login.setOnClickListener(myClickListener);

        loginHttpClientUtil = new LoginHttpClientUtil();

        //主线程创建一个Handler对象
        handler = new Handler(){
            //calledFromWrongThreadException从错误的线程调用（UI操作只能通过主线程，子线程不能更新UI空间）
            @Override
            public void handleMessage(Message msg) {
                //重写handler的handleMessage方法，用来接受并处理子线程发来的消息，并可执行UI操作
                if(msg.obj.equals("success")) {
                    Toast.makeText(LoginHttpClientActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginHttpClientActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    public void login(String username,String password,Integer flag){
        //flag=0表示get/flag=1表示post
        Boolean result = false;

        if(flag==0)
            result = loginHttpClientUtil.LoginGet(mContext,username,password);
        else
            result = loginHttpClientUtil.LoginPost(mContext,username,password);

        //子线程创建一个Message对象，将获取的数据绑定给Msg,通过主线程中handler对象将msg发送给主线程
        Message msg = Message.obtain();
        if(result)msg.obj = "success";
        else msg.obj = "fail";
        handler.sendMessage(msg);
    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            username = et_username.getText().toString().trim();
            password = et_password.getText().toString().trim();

            switch (v.getId()){
                case R.id.login_get_button:
                    //创建一个新线程执行HttpClient网络请求
                    Thread getThread = new Thread(new LoginGetThread());
                    getThread.start();
                    break;
                case R.id.login_post_button:
                    //创建一个新线程执行HttpClient网络请求
                    Thread postThread = new Thread(new LoginPostThread());
                    postThread.start();
                    break;
            }
        }
    }

    class LoginGetThread implements Runnable{
        public void run(){
            login(username,password,0);
        }
    }

    class LoginPostThread implements Runnable{
        public void run(){
            login(username,password,1);
        }
    }

}
