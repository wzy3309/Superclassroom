package com.superclassroom;

/**
 * Created by Timber on 2017/11/3.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDBHelper extends SQLiteOpenHelper {

    //定义一个创建表Book的SQLite语句
    private static final String CREATE_Classroom = "create table Classroom("
            + "id integer primary key autoincrement, "
            + "name text,"
            + "stime integer, "
            + "etime integer,"
            + "stage text,"
            + "username text)";


    private Context mContext;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;     //获取Context实例
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Classroom);
        Toast.makeText(mContext, "成功创建Database", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists Class");        //如果存在表Book，则删除该表
        onCreate(db);       //重新调用onCreate()，创建两张表
    }
}