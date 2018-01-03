package com.superclassroom;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.view.View.OnClickListener;
/**
 * Created by Timber on 2017/11/6.
 */

public class UserCerter extends Activity {
    private Button mCheaklist;
    private Button mRoomlist;
    private Button Release;
    private String tempname;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercenter);
        /*Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("user_name0");*/
        Intent i = getIntent();
        tempname = i.getStringExtra("user_name0");

        mCheaklist = (Button)findViewById(R.id.courselist);
        mRoomlist = (Button)findViewById(R.id.roominf);
        Release = (Button)findViewById(R.id.release);

        mCheaklist.setOnClickListener(mListen);
        mRoomlist.setOnClickListener(mListen);
        Release.setOnClickListener(mListen);
    }

    OnClickListener mListen = new OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.courselist:
                    break;
                case R.id.roominf:
                    Intent intent_usercenter_to_classroomactivity = new Intent(UserCerter.this,classroomactivity.class);
                    //Bundle bundle1 = new Bundle();
                   // bundle1.putString("name",tempname);
                    //intent_usercenter_to_classroomactivity.putExtras(bundle1);
                    intent_usercenter_to_classroomactivity.putExtra("user_name1",tempname);
                    startActivity(intent_usercenter_to_classroomactivity);
                    finish();
                    break;
                case R.id.release:
                    Intent intent_usercenter_to_release = new Intent(UserCerter.this,Release.class);
                    intent_usercenter_to_release.putExtra("user_name1",tempname);
                    //Bundle bundle2 = new Bundle();
                    //bundle2.putString("name",tempname);
                    //intent_usercenter_to_release.putExtras(bundle2);
                    startActivity(intent_usercenter_to_release);
                    finish();
                    break;
            }
        }
    };
}
