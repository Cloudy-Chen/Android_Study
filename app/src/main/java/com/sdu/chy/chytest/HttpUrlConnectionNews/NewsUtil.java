package com.sdu.chy.chytest.HttpUrlConnectionNews;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsUtil {
    //使用UrlConnection请求一个Url地址并且获取返回内容
//    String newsPath1 = "http://localhost:8080/chyWebTest/NewsServlet";
      String newsPath2 = "http://192.168.1.138:8080/chyWebTest/NewsServlet?android=chy";

      public List<NewsBean> getAllNews(Context context){
          List<NewsBean> newsList = new ArrayList();
          try{
              //创建一个Url对象
              URL url = new URL(newsPath2);
              //获取一个HttpConnection对象（用Http协议通过网络发送和获取数据）
              HttpURLConnection connection = (HttpURLConnection)url.openConnection();
              //为URLConnection对象设置一些请求参数，请求方式，链接的超时时间
              connection.setRequestMethod("GET");//请求方式
              connection.setConnectTimeout(10*1000);//设置超时时间（防止用户等待时间过长）
              int code = connection.getResponseCode();//再获取URL请求的数据前需判断响应码
              if(code==200){
                  // 200成功，返回有效数据
                  // 300跳转或重定向
                  // 400错误
                  // 500服务器访问异常
                  //获取有效数据并将获取的流数据解析成String
                  //获取服务器返回的流
                  InputStream inputStream = connection.getInputStream();
                  //将获取的流转换为字符串
                  String result = StreamUtil.StreamToString(inputStream);
                  //解析获取新闻字符串（JSON字符串）到List集合中
                  //result:{"news":
                  // [{"title":"标题1","description":"描述1"},
                  //  {"title":"标题2","description":"描述2"}]}
//                  JSONObject jsonRoot = new JSONObject(result);//字符串封装为一个Json对象
//                  JSONArray jsonArray = jsonRoot.getJSONArray("news");//news为JsonArray
                  JSONArray jsonArray = new JSONArray(result);
                  for(int i =0;i<jsonArray.length();i++){
                      JSONObject jsonNews = jsonArray.getJSONObject(i);
                      NewsBean newsBean = new NewsBean();
                      newsBean.setTitle(jsonNews.getString("title"));
                      newsBean.setDescription(jsonNews.getString("description"));
                      newsList.add(newsBean);
                  }
              }
              return newsList;
          }catch (Exception e){
              e.printStackTrace();
          }
          return null;
      }
}
