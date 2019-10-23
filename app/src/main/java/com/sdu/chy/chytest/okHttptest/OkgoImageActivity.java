package com.sdu.chy.chytest.okHttptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sdu.chy.chytest.R;

import org.w3c.dom.Text;

import okhttp3.Call;

public class OkgoImageActivity extends AppCompatActivity {

    public static final String IMG_URL = "http://www.sportshot.cn/file/goodsPic/2018111524-1.jpg";

    private Button btn_image;
    private ImageView img_image;
    private TextView txt_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okgo_image);

        btn_image = (Button)findViewById(R.id.btn_image);
        img_image = (ImageView)findViewById(R.id.img_image);
        txt_image = (TextView)findViewById(R.id.txt_image);

        btn_image.setOnClickListener(new MyClickListener());
    }

    private class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.btn_image:
                    okHttpTestByImageRequest();
                    break;
            }
        }
    }

    private void okHttpTestByImageRequest(){
        //String方式回调
        OkGo.<Bitmap>get(IMG_URL)
                .tag(this)
                .execute(new BitmapCallback(){
                    @Override
                    public void onSuccess(Response<Bitmap> response) {
                        //handleResponse(response);
                        img_image.setImageBitmap(response.body());
                    }

                    @Override
                    public void onError(Response<Bitmap> response) {
                        Call call = response.getRawCall();
                        if (call != null) {
                            txt_image.setText("请求失败  请求方式：" + call.request().method() + "\n" + "url：" + call.request().url());
                        }
                        }
                    }
                );
    }
}
