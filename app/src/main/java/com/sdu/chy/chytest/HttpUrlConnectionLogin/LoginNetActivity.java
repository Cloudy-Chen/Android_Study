package com.sdu.chy.chytest.HttpUrlConnectionLogin;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdu.chy.chytest.R;
import com.sdu.chy.chytest.HttpUrlConnectionNews.NewsAdapter;

import java.util.List;

public class LoginNetActivity extends AppCompatActivity {

    /*
    网络post,get实现登录案例
    */

    private Button btn_login;

    private Context mContext;

    private EditText et_username;
    private EditText et_password;

    private List newsList;
    private NewsAdapter newsAdapter;
    private LoginUtil loginUtil;

    private MyClickListener myClickListener;

    private String username;
    private String password;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_net);

        et_username = (EditText) findViewById(R.id.login_username);
        et_password = (EditText)findViewById(R.id.login_password);
        btn_login = (Button)findViewById(R.id.login_button);

        mContext = this;

        myClickListener = new MyClickListener();

        btn_login.setOnClickListener(myClickListener);

        loginUtil = new LoginUtil();

        //主线程创建一个Handler对象
        handler = new Handler(){
            //calledFromWrongThreadException从错误的线程调用（UI操作只能通过主线程，子线程不能更新UI空间）
            @Override
            public void handleMessage(Message msg) {
                //重写handler的handleMessage方法，用来接受并处理子线程发来的消息，并可执行UI操作
                if(msg.obj.equals("success")) {
                    Toast.makeText(LoginNetActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginNetActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        };

    }

    public void login(String username,String password){
        Boolean result = loginUtil.login(mContext,username,password);
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

            switch (v.getId()){
                case R.id.login_button:
                    username = et_username.getText().toString().trim();
                    password = et_password.getText().toString().trim();
                    //子线程操作与主线程操作是异步的（注意顺序）
                    Thread newsThread = new Thread(new NewsThread());
                    newsThread.start();
                    break;
            }
        }
    }

    class NewsThread implements Runnable{
        public void run(){
            login(username,password);
        }
    }

}
