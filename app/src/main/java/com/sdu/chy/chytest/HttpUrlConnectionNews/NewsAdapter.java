package com.sdu.chy.chytest.HttpUrlConnectionNews;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdu.chy.chytest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {

    List<NewsBean> list = new ArrayList<>();
    Context context;

    public NewsAdapter(Context context, List<NewsBean> list)
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
            view = View.inflate(context, R.layout.item_news_layout,null);
            TextView title = (TextView) view.findViewById(R.id.item_news_title);
            TextView description = (TextView) view.findViewById(R.id.item_news_description);
            NewsBean news = list.get(position);
            title.setText(news.getTitle());
            description.setText(news.getDescription());
        }
        return view;
    }

}

