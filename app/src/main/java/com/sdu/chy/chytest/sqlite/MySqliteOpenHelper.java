package com.sdu.chy.chytest.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

    //创建一个类集成Sqliteopenhelper需添加一个构造方法，并复写onCreate,onUpgrade
    //sqliteopenhelper用于创建数据库以及版本管理

    public MySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //数据库第一次创建时调用，适合做表结构的初始化（执行sql语句）
        //db.execSQL("create table userInfo(_id integer primary key autoincrement,username varchar(20),password varchar(20))");
        //创建表结构
        String sql = "create table "+"user"+"("+
                "id"+" integer primary key autoincrement,"+
                "username"+" varchar(20)," +
                "password"+" integer)";
        db.execSQL(sql);//执行sql语句
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Toast.makeText(SqliteActivity.this,oldVersion+"升级到"+newVersion,Toast.LENGTH_SHORT);
    }
}
