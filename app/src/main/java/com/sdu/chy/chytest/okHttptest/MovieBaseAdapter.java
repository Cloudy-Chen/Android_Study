package com.sdu.chy.chytest.okHttptest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdu.chy.chytest.HttpUrlConnectionNews.NewsBean;
import com.sdu.chy.chytest.R;

import java.util.ArrayList;
import java.util.List;

public class MovieBaseAdapter extends BaseAdapter {

    List<MovieBean> list = new ArrayList<>();
    Context context;
    TextView movie_title;
    TextView movie_summary;
    ImageView movie_image;

    public MovieBaseAdapter(Context context, List<MovieBean> list)
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
        View view = null;
        if(convertView!=null)view=convertView;
        else{
            view = View.inflate(context, R.layout.item_movie_layout,null);
            MovieBean movie = list.get(position);
            movie_title = (TextView)view.findViewById(R.id.movie_title);
            movie_summary = (TextView)view.findViewById(R.id.movie_summary);
            movie_image = (ImageView)view.findViewById(R.id.movie_image);

            movie_title.setText(movie.getMovieName());
            movie_summary.setText(movie.getSummary());

        }
        return view;
    }

}

