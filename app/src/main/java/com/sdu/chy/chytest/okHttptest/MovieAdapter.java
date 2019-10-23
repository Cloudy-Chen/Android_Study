package com.sdu.chy.chytest.okHttptest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.Response;
import com.sdu.chy.chytest.HttpUrlConnectionLogin.LoginNetActivity;
import com.sdu.chy.chytest.HttpUrlConnectionNews.NewsAdapter;
import com.sdu.chy.chytest.R;

import java.net.URL;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class MovieAdapter extends BaseAdapter {

    List<MovieBean> list = new ArrayList<>();
    ViewHolder viewHolder = null;
    String picUrl = null;
    Context context;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            //重写handler的handleMessage方法，用来接受并处理子线程发来的消息，并可执行UI操作
            viewHolder.movie_image.setImageBitmap((Bitmap) msg.obj);
        }
    };

    private class ViewHolder{
        private TextView movie_title;
        private TextView movie_summary;
        private ImageView movie_image;
    }


    public MovieAdapter(Context context, List<MovieBean> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView!=null)viewHolder = (ViewHolder) convertView.getTag();
        else{
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_movie_layout,null);

            viewHolder = new ViewHolder();
            viewHolder.movie_title = (TextView) convertView.findViewById(R.id.movie_title);
            viewHolder.movie_summary = (TextView) convertView.findViewById(R.id.movie_summary);
            viewHolder.movie_image = (ImageView)convertView.findViewById(R.id.movie_image);

            convertView.setTag(viewHolder);
        }


        MovieBean movie = list.get(position);
        viewHolder.movie_title.setText(movie.getMovieName());
        viewHolder.movie_summary.setText(movie.getSummary());

        //方法1：okGo
        getBitmapFromUrlByOkgo(viewHolder,movie.getCoverImg());

        //方法2：handler处理
//        picUrl = movie.getCoverImg();
//        Thread movieIamgeThread = new Thread(new MovieImageThread());
//        movieIamgeThread.start();
        return convertView;
    }

    private void getBitmapFromUrl(ViewHolder viewHolder,String url){
        try {
            URL picUrl = new URL(url);
            Bitmap bitmap = BitmapFactory.decodeStream(picUrl.openStream());
            viewHolder.movie_image.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getBitmapFromUrlByOkgo(final ViewHolder viewHolder, String url){
                OkGo.<Bitmap>get(url)
                .tag(this)
                .execute(new BitmapCallback(){
                             @Override
                             public void onSuccess(Response<Bitmap> response) {
                                 //handleResponse(response);
                                 viewHolder.movie_image.setImageBitmap(response.body());
//                                 Message msg = Message.obtain();
//                                 msg.obj = response.body();
//                                 handler.sendMessage(msg);
                             }

                             @Override
                             public void onError(Response<Bitmap> response) {
                                 Toast.makeText(context,"请求失败",Toast.LENGTH_SHORT).show();
                             }
                         }
                );
    }

    class MovieImageThread implements Runnable{
        public void run(){
            getBitmapFromUrl(viewHolder,picUrl);
        }
    }

}

