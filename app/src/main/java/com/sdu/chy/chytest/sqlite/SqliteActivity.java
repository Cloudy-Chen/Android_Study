package com.sdu.chy.chytest.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sdu.chy.chytest.R;

import java.util.ArrayList;
import java.util.List;

public class SqliteActivity extends AppCompatActivity {

    /*
    知识点：sqlite、listview
    sqlite嵌入式轻量级数据库（不用搭建服务器）
    */

    private EditText et_username;
    private EditText et_password;
    private Button btn_search;
    private Button btn_add;
    private Button btn_delete;
    private Button btn_refresh;
    private ListView lv;

    private Context mContext;

    private MySqliteOpenHelper mySqliteOpenHelper;
    private SQLiteDatabase db;

    private UserDao userDao;
    private List<UserBean> userList;
    private MyListAdapter myListAdapter;

    private MyClickListener myClickListener;
    private MyItemClickListener myItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        et_username = (EditText) findViewById(R.id.test5_username);
        et_password = (EditText) findViewById(R.id.test5_password);

        btn_search = (Button)findViewById(R.id.test5_search);
        btn_add = (Button)findViewById(R.id.test5_add);
        btn_delete = (Button)findViewById(R.id.test5_delete);
        btn_refresh = (Button)findViewById(R.id.test5_refresh);

        lv = (ListView) findViewById(R.id.test5_lv);

        mContext = this;

        myClickListener = new MyClickListener();
        myItemClickListener = new MyItemClickListener();

        btn_search.setOnClickListener(myClickListener);
        btn_delete.setOnClickListener(myClickListener);
        btn_add.setOnClickListener(myClickListener);
        btn_refresh.setOnClickListener(myClickListener);

        userDao = new UserDao(mContext);
        userList = new ArrayList<>();
        myListAdapter = new MyListAdapter(mContext,userList);
        lv.setAdapter(myListAdapter);
        lv.setOnItemClickListener(myItemClickListener);

        fetchData();
    }

    public void fetchData(){
        userList = userDao.findAll();
        myListAdapter = new MyListAdapter(mContext,userList);
        lv.setAdapter(myListAdapter);
    }

    private class MyClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {

            String username = et_username.getText().toString().trim();
            String password = et_password.getText().toString().trim();

            switch (v.getId()){
                case R.id.test5_search:
                    userList = userDao.findByUsername(username);
                    myListAdapter = new MyListAdapter(mContext,userList);
                    lv.setAdapter(myListAdapter);
                    break;
                case R.id.test5_add:
                    boolean flag1 = userDao.addUser(username,password);
                    if(flag1)Toast.makeText(SqliteActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(SqliteActivity.this,"添加失败",Toast.LENGTH_SHORT).show();

                    fetchData();

                    break;
                case R.id.test5_delete:
                    boolean flag2 = userDao.deleteUser(username);
                    if(flag2)Toast.makeText(SqliteActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                    else Toast.makeText(SqliteActivity.this,"删除失败",Toast.LENGTH_SHORT).show();

                    fetchData();

                    break;
                case R.id.test5_refresh:

                    fetchData();

                    break;
            }
        }
    }

    private class MyItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            UserBean user = (UserBean)parent.getItemAtPosition(position);
            et_username.setText(user.getUsername());
            et_password.setText(user.getPassword());
        }
    }

}
