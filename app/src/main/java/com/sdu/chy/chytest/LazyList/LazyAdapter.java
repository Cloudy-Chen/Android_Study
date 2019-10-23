package com.sdu.chy.chytest.LazyList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdu.chy.chytest.R;
import com.sdu.chy.chytest.okHttptest.MovieBean;

import java.util.List;

/**
 * Created by danding on 2019/4/8.
 */

public class LazyAdapter extends BaseAdapter {

    private List<MovieBean> movieList;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public LazyAdapter(Context context, List<MovieBean> movieList) {
        this.movieList = movieList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(context);
    }

    public int getCount() {
        return movieList.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item_lazy_layout, null);

        TextView title = (TextView)vi.findViewById(R.id.lazy_title);
        TextView summary = (TextView)vi.findViewById(R.id.lazy_summary);
        ImageView image=(ImageView)vi.findViewById(R.id.lazy_img);

        MovieBean movie = movieList.get(position);
        title.setText(movie.getMovieName());
        summary.setText(movie.getSummary());
        imageLoader.DisplayImage(movie.getCoverImg(), image);

        return vi;
    }
}