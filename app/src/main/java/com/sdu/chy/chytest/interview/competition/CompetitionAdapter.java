package com.sdu.chy.chytest.interview.competition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdu.chy.chytest.R;

import java.util.ArrayList;
import java.util.List;

public class CompetitionAdapter extends BaseAdapter {

    private Context context;
    private ArrayList dataList;
    private LayoutInflater mInflater;

    public CompetitionAdapter(Context context,ArrayList dataList) {
        this.context = context;
        this.dataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            // 没有缓存
            convertView = mInflater.inflate(R.layout.item_competition_listview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView)convertView.findViewById(R.id.text_competition_listview);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text.setText(dataList.get(position).toString());
        return convertView;
    }

    static class ViewHolder{
        // 使用ViewHolder,避免findViewById带来的性能损耗
        public TextView text;
    }

}
