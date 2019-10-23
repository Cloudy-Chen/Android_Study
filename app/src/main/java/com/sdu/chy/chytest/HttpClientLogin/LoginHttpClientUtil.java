package com.sdu.chy.chytest.HttpClientLogin;

import android.content.Context;
import android.net.http.HttpResponseCache;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class LoginHttpClientUtil {
    //使用UrlConnection请求一个Url地址并且获取返回内容
      //String path = "http://192.168.1.138:8080/chyWebTest/LoginServlet";

      public Boolean LoginGet(Context context,String username,String password){
        try{
            String path = "http://192.168.1.138:8080/chyWebTest/LoginServlet?username="+username+"&password="+password;
            Boolean isSuccess = false;
            //1.创建一个HttpClient对象
            HttpClient httpClient = new DefaultHttpClient();
            //2.设置请求方式
            HttpGet httpGet = new HttpGet(path);
            //3.执行一个Http请求（返回HttpResponse）
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //4.获取请求的状态码
            StatusLine statusLine = httpResponse.getStatusLine();
            int code = statusLine.getStatusCode();
            //5.判断状态吗后获取内容
            if(code==200){
                //获取实体内容，中封装有Http请求返回流信息
                HttpEntity entity = httpResponse.getEntity();
                InputStream inputStream = entity.getContent();
                String result = StreamUtil.StreamToString(inputStream);
                if(result.equals(("success"))){
                    isSuccess = true;
                }
            }
            return isSuccess;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Boolean LoginPost(Context context,String username,String password){
        try{
            String path = "http://192.168.1.138:8080/chyWebTest/LoginServlet";
            Boolean isSuccess = false;
            //1.创建一个HttpClient对象
            HttpClient httpClient = new DefaultHttpClient();
            //2.设置请求方式
            HttpPost httpPost= new HttpPost(path);

            //创建集合封装请求数据
            ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
            BasicNameValuePair username1 = new BasicNameValuePair("username",username);
            BasicNameValuePair password1 = new BasicNameValuePair("password",password);
            arrayList.add(username1);
            arrayList.add(password1);
            //创建一个entity
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(arrayList,"utf-8");
            httpPost.setEntity(encodedFormEntity);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            StatusLine statusLine = httpResponse.getStatusLine();
            int code = statusLine.getStatusCode();
            //5.判断状态吗后获取内容
            if(code==200){
                //获取实体内容，中封装有Http请求返回流信息
                HttpEntity entity = httpResponse.getEntity();
                InputStream inputStream = entity.getContent();
                String result = StreamUtil.StreamToString(inputStream);
                if(result.equals(("success"))){
                    isSuccess = true;
                }
            }
            return isSuccess;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
