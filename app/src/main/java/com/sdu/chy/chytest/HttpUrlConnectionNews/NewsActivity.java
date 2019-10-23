package com.sdu.chy.chytest.HttpUrlConnectionNews;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.sdu.chy.chytest.R;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    /*
    Android网络基础
    总结：主线程不能做耗时的操作，网络请求是耗时的操作，需放到子线程中做，子线程不能更新UI控件的内容（UI），所以产生矛盾。
        解决方法是用Handler将子线程消息放松到主线程，通过主线程更新UI
    */

    private ListView lv;
    private Button btn_refresh;

    private Context mContext;

    private List newsList;
    private NewsAdapter newsAdapter;
    private NewsUtil newsUtil;

    private MyClickListener myClickListener;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        lv = (ListView) findViewById(R.id.test6_lv);
        btn_refresh = (Button)findViewById(R.id.test6_refresh);

        mContext = this;

        myClickListener = new MyClickListener();

        btn_refresh.setOnClickListener(myClickListener);

        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(mContext,newsList);
        newsUtil = new NewsUtil();
        lv.setAdapter(newsAdapter);

        //主线程创建一个Handler对象
        handler = new Handler(){
            //calledFromWrongThreadException从错误的线程调用（UI操作只能通过主线程，子线程不能更新UI空间）
            @Override
            public void handleMessage(Message msg) {
                //重写handler的handleMessage方法，用来接受并处理子线程发来的消息，并可执行UI操作
                if(msg.obj.equals("success")) {
                    newsAdapter = new NewsAdapter(mContext, newsList);
                    lv.setAdapter(newsAdapter);
                }
            }
        };

    }

    public void fetchData(){
        newsList = newsUtil.getAllNews(mContext);
        //子线程创建一个Message对象，将获取的数据绑定给Msg,通过主线程中handler对象将msg发送给主线程
        Message msg = Message.obtain();
        msg.obj = "success";
        handler.sendMessage(msg);
    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.test6_refresh:
                    //子线程操作与主线程操作是异步的（注意顺序）
                    Thread newsThread = new Thread(new NewsThread());
                    newsThread.start();
                    break;
            }
        }
    }

    class NewsThread implements Runnable{
        public void run(){
            //ANR application not response应用无响应
            //4.0后网络操作限制在子线程中进行，因为网络是耗时的操作（网络链接各种情况，网络不好等等）可能造成ANR
            //android中耗时的操作（请求网络，大文件拷贝，数据库操作）需要在子线程中做
            //创建一个子线程做网络请求
            fetchData();
        }
    }

}
