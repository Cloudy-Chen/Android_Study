package com.sdu.chy.chytest.okHttptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdu.chy.chytest.R;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.Call;

public class OkgoSendActivity extends AppCompatActivity {

    public static final String URL = "http://192.168.1.104:8080/chyWebTest/LoginServlet";

    private Button btn_send_login;
    private EditText et_send_userName;
    private EditText et_send_password;
    private TextView txt_send_data;
    private MyClickListener myClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okgo_send);

        btn_send_login = (Button) findViewById(R.id.btn_send_login);
        et_send_userName = (EditText) findViewById(R.id.et_send_userName);
        et_send_password = (EditText) findViewById(R.id.et_send_password);
        txt_send_data = (TextView) findViewById(R.id.txt_send_data);

        btn_send_login.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            String username = et_send_userName.getText().toString().trim();
            String password = et_send_password.getText().toString().trim();

            switch (v.getId()){
                case R.id.btn_send_login:
                    okgoSendTest(username,password);
                    break;
            }
        }
    }

    private void okgoSendTest(String username,String password){
        //String方式回调

//        HashMap<String, String> data = new HashMap<>();
//        data.put("username", username);
//        data.put("password",password);
//        JSONObject jsonObject = new JSONObject(data);

        OkGo.<String>post(URL)
                .tag(this)
                //.upJson(jsonObject)
                .params("username",username)
                .params("password",password)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        txt_send_data.setText(body);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        String body = response.body();
                        txt_send_data.setText(body);
                    }
                });
    }
}
