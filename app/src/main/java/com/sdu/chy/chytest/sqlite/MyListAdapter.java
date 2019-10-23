package com.sdu.chy.chytest.sqlite;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sdu.chy.chytest.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyListAdapter extends BaseAdapter {

    List<UserBean> list = new ArrayList<>();
    Context context;

    public MyListAdapter(Context context,List<UserBean> list)
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
            view = View.inflate(context, R.layout.item_user_layout,null);
            TextView username = (TextView) view.findViewById(R.id.item_username);
            TextView password = (TextView)view.findViewById(R.id.item_password);
            UserBean user = list.get(position);
            username.setText(user.getUsername());
            password.setText(user.getPassword());
        }
        return view;
    }

}

