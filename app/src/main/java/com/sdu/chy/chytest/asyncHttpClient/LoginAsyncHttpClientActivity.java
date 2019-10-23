package com.sdu.chy.chytest.asyncHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdu.chy.chytest.HttpClientLogin.LoginHttpClientUtil;
import com.sdu.chy.chytest.R;

public class LoginAsyncHttpClientActivity extends AppCompatActivity {

    /*
    网络post,get实现登录案例
    */

    private Button btn_get_login;
    private Button btn_post_login;

    private Context mContext;

    private EditText et_username;
    private EditText et_password;

    private LoginAsyncHttpClientUtil loginAsyncHttpClientUtil;

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

        loginAsyncHttpClientUtil = new LoginAsyncHttpClientUtil();

    }

    public void login(String username,String password,Integer flag){
        //flag=0表示get/flag=1表示post
        //ascyn不用单独创建线程。内部已经封装好,并且在主线程执行（可更改UI）

        if(flag==0)
            loginAsyncHttpClientUtil.LoginGet(mContext,username,password);
        else
            loginAsyncHttpClientUtil.LoginPost(mContext,username,password);
    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            username = et_username.getText().toString().trim();
            password = et_password.getText().toString().trim();

            switch (v.getId()){
                case R.id.login_get_button:
                    login(username,password,0);
                    break;
                case R.id.login_post_button:
                    login(username,password,1);
                    break;
            }
        }
    }

}
