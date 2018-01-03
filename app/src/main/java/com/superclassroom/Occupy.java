package com.superclassroom;

import android.database.Cursor;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timber on 2017/11/4.
 */

public class Occupy extends AppCompatActivity {
    private EditText mOccupyClass;
    private Button mSureOccupy;
    private Button mOccupyBack;
    private MyDBHelper dbHelper;
    private SQLiteDatabase db1;
    private String tmpname;
    private Spinner mStarttime;
    private Spinner mEndtime;
    private List<Integer> list1;
    private List<Integer> list2;
    private ArrayAdapter<Integer> arr1;
    private ArrayAdapter<Integer> arr2;
    private int list1sel;
    private int list2sel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.occupy);

        Intent i = getIntent();
        tmpname = i.getStringExtra("user_name2");


        mOccupyClass = (EditText)findViewById(R.id.occupyclass);
        mOccupyBack = (Button)findViewById(R.id.occupyback);
        mSureOccupy = (Button)findViewById(R.id.sureoccupy);

        mStarttime = (Spinner)findViewById(R.id.starttime);
        mEndtime = (Spinner)findViewById(R.id.endtime);

        mOccupyBack.setOnClickListener(m_register_Listener);
        mSureOccupy.setOnClickListener(m_register_Listener);
        dbHelper=new MyDBHelper(this,"Classroom.db",null,5);
        db1 = dbHelper.getWritableDatabase();

        list1 = new ArrayList<Integer>();
        list1.add(0);list1.add(1);list1.add(2);list1.add(3);list1.add(4);list1.add(6);list1.add(7);list1.add(8);list1.add(9);list1.add(10);list1.add(11);list1.add(12);
        list1.add(13);list1.add(14);list1.add(15);list1.add(16);list1.add(17);list1.add(18);list1.add(19);list1.add(20);list1.add(21);list1.add(22);list1.add(23);list1.add(24);


        list2 = new ArrayList<Integer>();
        list2.add(0);list2.add(1);list2.add(2);list2.add(3);list2.add(4);list2.add(6);list2.add(7);list2.add(8);list2.add(9);list2.add(10);list2.add(11);list2.add(12);
        list2.add(13);list2.add(14);list2.add(15);list2.add(16);list2.add(17);list2.add(18);list2.add(19);list2.add(20);list2.add(21);list2.add(22);list2.add(23);list2.add(24);

        arr1 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,list1);
        arr1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mStarttime.setAdapter(arr1);

        arr2 = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,list2);
        arr2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEndtime.setAdapter(arr2);

        mStarttime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                list1sel = (int)mStarttime.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mEndtime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                list2sel = (int)mEndtime.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    OnClickListener m_register_Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.occupyback:
                    Intent intent_Occupy_to_roomactivity = new Intent(Occupy.this,classroomactivity.class) ;
                    //Bundle bundle1 = new Bundle();
                    //bundle1.putString("name",tmpname);
                    //intent_Occupy_to_roomactivity.putExtras(bundle1);
                    intent_Occupy_to_roomactivity.putExtra("user_name1",tmpname);
                    startActivity(intent_Occupy_to_roomactivity);
                    finish();
                    break;
                case R.id.sureoccupy:
                    Cursor cursor = db1.rawQuery("select * from Classroom", null);
                    String ClassName = mOccupyClass.getText().toString().trim();
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String stage = cursor.getString(cursor.getColumnIndex("stage"));
                        String stime = cursor.getString(cursor.getColumnIndex("stime"));
                        String etime = cursor.getString(cursor.getColumnIndex("etime"));
                        if((name.equals(ClassName) )&& (stage.equals("free"))&&(list1sel<list2sel))
                        {
                            ContentValues contentValues1 = new ContentValues();
                            ContentValues contentValues2 = new ContentValues();
                            ContentValues contentValues3 = new ContentValues();
                            ContentValues contentValues4 = new ContentValues();
                            contentValues1.put("stage","busy");
                            contentValues2.put("stime", list1sel);
                            contentValues3.put("etime",list2sel);
                            contentValues4.put("username",tmpname);
                            String [] classname = {ClassName};
                            db1.update("Classroom",contentValues1,"name = ?",classname);
                            db1.update("Classroom",contentValues2,"name = ?",classname);
                            db1.update("Classroom",contentValues3,"name = ?",classname);
                            db1.update("Classroom",contentValues4,"name = ?",classname);
                            Toast.makeText(getApplicationContext(), "占用成功",Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else if(name.equals(ClassName) && stage.equals("busy"))
                        {
                            Toast.makeText(getApplicationContext(), "该教室已被使用",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    Intent intent_Occupy_to_roomactivity1 = new Intent(Occupy.this,classroomactivity.class) ;    //切换Login Activity至User Activity
                    //Bundle bundle2 = new Bundle();
                    //bundle2.putString("name",tmpname);
                    //intent_Occupy_to_roomactivity1.putExtras(bundle2);
                    intent_Occupy_to_roomactivity1.putExtra("user_name1",tmpname);
                    startActivity(intent_Occupy_to_roomactivity1);
                    finish();
                    break;
            }
        }
    };
}

