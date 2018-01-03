package com.superclassroom;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import java.util.ArrayList;

/**
 * Created by Timber on 2017/11/6.
 */

public class Release extends Activity {
    private MyDBHelper moh;
    private SQLiteDatabase sd;
    private ArrayList<classroom> release_list;
    private ListView lv;
    private Button mReleaseback;                      //返回按钮
    private Button mRelease;                     //占用按钮
    private String tmpname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release);
        Intent i = getIntent();
        tmpname = i.getStringExtra("user_name1");
        moh = new MyDBHelper(this, "Classroom.db", null, 5);
        sd = moh.getReadableDatabase();
        release_list = new ArrayList<>();
        Cursor cursor = sd.rawQuery("select * from Classroom", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int stime = cursor.getInt(cursor.getColumnIndex("stime"));
            int etime = cursor.getInt(cursor.getColumnIndex("etime"));
            String stage = cursor.getString(cursor.getColumnIndex("stage"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            if (username.equals(tmpname)) {
                classroom st = new classroom(name, stime,etime, stage, username);
                release_list.add(st);
            }
        }

        lv = (ListView) findViewById(R.id.release_lv);
        lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return release_list.size();
            }

            //ListView的每一个条目都是一个view对象
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view;
                //对ListView的优化，convertView为空时，创建一个新视图；convertView不为空时，代表它是滚出
                //屏幕，放入Recycler中的视图,若需要用到其他layout，则用inflate(),同一视图，用fiindViewBy()
                if (convertView == null) {
                    view = View.inflate(getBaseContext(), R.layout.releaseroomlayout, null);
                } else {
                    view = convertView;
                }

                //从studentlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                classroom st = release_list.get(position);
                TextView name = (TextView) view.findViewById(R.id.room_name);
                TextView time = (TextView) view.findViewById(R.id.room_time);
                TextView stage = (TextView) view.findViewById(R.id.room_stage);
                name.setText(st.getName());
                time.setText(st.getStime()+"-"+st.getEtime());
                stage.setText(st.getStage());

                return view;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
        });
        mReleaseback = (Button) findViewById(R.id.releaseback);
        mRelease = (Button) findViewById(R.id.releaseall);
        mReleaseback.setOnClickListener(mmm);
        mRelease.setOnClickListener(mmm);
    }

    OnClickListener mmm = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.releaseback:
                    Intent intent_release_to_certer = new Intent(Release.this,UserCerter.class);
                    intent_release_to_certer.putExtra("user_name0",tmpname);
                    startActivity(intent_release_to_certer);
                    finish();
                    break;
                case R.id.releaseall:
                    Cursor cursor = sd.rawQuery("select * from Classroom", null);
                    while(cursor.moveToNext()){
                        String username = cursor.getString(cursor.getColumnIndex("username"));
                        if(username.equals(tmpname)){
                            ContentValues contentValues1 = new ContentValues();
                            ContentValues contentValues2 = new ContentValues();
                            ContentValues contentValues3 = new ContentValues();
                            contentValues1.put("stage","free");
                            contentValues2.put("time", "null");
                            contentValues3.put("username","");
                            String [] username1 = {username};
                            sd.update("Classroom",contentValues1,"username = ?",username1);
                            sd.update("Classroom",contentValues2,"username = ?",username1);
                            sd.update("Classroom",contentValues3,"username = ?",username1);
                        }
                    }
                    Intent intent_releasesuccess_to_certer =new Intent(Release.this,UserCerter.class);
                    intent_releasesuccess_to_certer.putExtra("user_name0",tmpname);
                    startActivity(intent_releasesuccess_to_certer);
                    finish();
                    Toast.makeText(getApplicationContext(), "解除成功",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
