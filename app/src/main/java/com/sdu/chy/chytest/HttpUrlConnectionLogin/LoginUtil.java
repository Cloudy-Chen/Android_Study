package com.sdu.chy.chytest.HttpUrlConnectionLogin;

import android.content.Context;

import com.sdu.chy.chytest.HttpUrlConnectionNews.StreamUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginUtil {
    //使用UrlConnection请求一个Url地址并且获取返回内容
      String path = "http://192.168.1.138:8080/chyWebTest/LoginServlet";

      public Boolean login(Context context,String username,String password){
          try{
              Boolean isSuccess = false;
              //创建一个Url对象
              URL url = new URL(path);
              //获取一个HttpConnection对象（用Http协议通过网络发送和获取数据）
              HttpURLConnection openConnection = (HttpURLConnection)url.openConnection();
              //为URLConnection对象设置一些请求参数，请求方式，链接的超时时间
              openConnection.setRequestMethod("POST");//请求方式
              openConnection.setConnectTimeout(10*1000);//设置超时时间（防止用户等待时间过长）
              String body = "username="+username+"&password="+password;

              openConnection.setRequestProperty("Content-length",body.length()+"");
              openConnection.setRequestProperty("Cache-Control","max-age=0");
              openConnection.setRequestProperty("Origin","http://192.168.1.138:8080");

              openConnection.setDoOutput(true);
              openConnection.getOutputStream().write(body.getBytes());

              int code = openConnection.getResponseCode();//再获取URL请求的数据前需判断响应码
              if(code==200){

                  InputStream inputStream = openConnection.getInputStream();
                  String result = StreamUtil.StreamToString(inputStream);
                  isSuccess = false;
                  if(result.contains(("success"))){
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
