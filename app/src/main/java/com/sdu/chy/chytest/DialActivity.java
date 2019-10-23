package com.sdu.chy.chytest;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class DialActivity extends AppCompatActivity {

    private EditText et_number;
    private Button bt_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        et_number = (EditText) findViewById(R.id.test1_edit_text);
        bt_commit = (Button)findViewById(R.id.test1_button);
        bt_commit.setOnClickListener(new MyClickListener());

    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            String number = et_number.getText().toString().trim();

            switch (v.getId()){
                case R.id.test1_button:
//                  Toast.makeText(MainActivity.this,number,Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();//创建一个意图，实现目的
                    intent.setAction(Intent.ACTION_DIAL);//设置意图对象的动作：拨打电话
                    intent.setData(Uri.parse("tel:"+number));//设置意图对象的数据：拨打的电话号码
                    startActivity(intent);
                    break;
            }
        }
    }
}


