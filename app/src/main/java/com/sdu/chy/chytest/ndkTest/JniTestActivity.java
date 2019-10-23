package com.sdu.chy.chytest.ndkTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sdu.chy.chytest.R;

public class JniTestActivity extends AppCompatActivity {

    private TextView JniTextView;
    private Button JniScheduleBtn;

    private JniClickListener jniClickListener = new JniClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);
        initView();
    }

    public void initView(){
        JniTextView = (TextView)findViewById(R.id.jni_text_view);
        JniScheduleBtn = (Button) findViewById(R.id.jni_btn_schedule);

        JniScheduleBtn.setOnClickListener(jniClickListener);
    }

    public class JniClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.jni_btn_schedule:
                    JniTextView.setText(new JNI().sayHello());
                    break;
            }
        }
    }
}
