package com.sdu.chy.chytest.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // DAO(Data Access Object) 数据访问对象是一个面向对象的数据库接口
    // 是进行数据操作的类, 是对于数据库中的数据做增删改查等操作的代码。

    //创建SqliteOpenHelper帮助类的一个对象，调用getReadableDatabase()方法会帮助我们创建打开一个数据库
    //context上下文,name数据库文件名称,factory创建cursor游标对象-结果集，默认为Null,version数据库版本号，从1开始，调用onUpgrade方法
    //创建的数据库位于私有目录下com.sdu.chy.chytest.reatedatabase/databases/user.db
    MySqliteOpenHelper mySqliteOpenHelper;
    SQLiteDatabase db;

    UserDao(Context context){
       mySqliteOpenHelper = new MySqliteOpenHelper(context,"user.db",null,1);
    }

    public List findAll() {
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        List<UserBean> list = new ArrayList();
        Cursor cursor = db.query("user",null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));

            UserBean userInfo = new UserBean();
            userInfo.setId(id);
            userInfo.setUsername(username);
            userInfo.setPassword(password);
            list.add(userInfo);
        }
        cursor.close();
        db.close();
        return list;
    }

    public List findByUsername(String username)
    {
        SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query("user",null,"username=?",new String[]{username},null,null,null);
        List<UserBean> result = new ArrayList();
        if(cursor!=null&&cursor.getCount()>0){
            while (cursor.moveToNext()){
                Integer id = cursor.getInt(0);
                String username_str = cursor.getString(1);
                String password_str = cursor.getString(2);
                UserBean bean = new UserBean();
                bean.setId(id);
                bean.setUsername(username_str);
                bean.setPassword(password_str);
                result.add(bean);
            }
            cursor.close();
        }
        db.close();
        return result;
    }

    public boolean addUser(String username, String password) {
        //调用GetReadableDatabase方法初始化数据库的创建
        //返回一个sqliteDatabase对象
        db = mySqliteOpenHelper.getReadableDatabase();
        //db.execSQL("insert into user(username,password) values(?,?)", new String[]{username,password});
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("password",password);
        //返回值代表添加这个新行的id，-1代表添加失败
        long result = db.insert("user",null,values);
        db.close();
        if(result==-1)return false;
        else return true;
    }

    //用sharedPreferences存储数据
    public boolean deleteUser(String username) {
        db = mySqliteOpenHelper.getReadableDatabase();
        int result = db.delete("user","username=?",new String[]{username});
        db.close();
        if(result==-1)return false;
        else return true;
    }

}
