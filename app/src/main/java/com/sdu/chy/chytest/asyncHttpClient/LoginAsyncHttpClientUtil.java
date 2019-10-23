package com.sdu.chy.chytest.asyncHttpClient;

import android.content.Context;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sdu.chy.chytest.HttpUrlConnectionNews.StreamUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LoginAsyncHttpClientUtil {

      public void LoginGet(final Context context,String username,String password){

          try{
            String path = "http://192.168.1.138:8080/chyWebTest/LoginServlet?username="+ URLEncoder.encode(username,"UTF-8")
                    +"&password="+URLEncoder.encode(password,"UTF-8");
            //创建一个AsyncHttpClient对象
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get(path, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                    //statusCode:状态码
                    //headers:头信息
                    //responseBody:返回的内容，返回的实体
                    //判断状态码
                    if(statusCode==200){
                        //获取结果
                        try{
                            String result = new String(responseBody,"utf-8");
                            if(result.equals("success"))
                            Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context,"登录失败",Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int i, cz.msebera.android.httpclient.Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("fail");
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void LoginPost(final Context context,String username,String password){
        try{
            String path = "http://192.168.1.138:8080/chyWebTest/LoginServlet";

            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username",username);
            params.put("password",password);

            asyncHttpClient.post(path, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if(statusCode==200){
                        //获取结果
                        try{
                            String result = new String(responseBody,"utf-8");
                            if(result.equals("success"))
                                Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(context,"登录失败",Toast.LENGTH_SHORT).show();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("fail");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
