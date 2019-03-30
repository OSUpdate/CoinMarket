package com.example.ffff.android_2013150041;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingTab extends Fragment{
    private String id;
    private int autoLogin;
    private Typeface typeface;
    public SettingTab(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_setting_tab, null);
        final SQLiteHandle dbhelper = new SQLiteHandle(getActivity().getApplicationContext(),"user.db",null,1);
        final SQLiteDatabase database = dbhelper.getWritableDatabase();
        id = getArguments().getString("userid");
        autoLogin = getArguments().getInt("autologin");
        Log.d("test",String.valueOf(autoLogin));
        final CheckedTextView loginCheck = (CheckedTextView) view.findViewById(R.id.loginCheck);
        final TextView logout = (TextView)view.findViewById(R.id.logout);
        LinearLayout root = (LinearLayout) view.findViewById(R.id.setLayout);
        final TextView changePw = (TextView) view.findViewById(R.id.changePw);
        if(autoLogin == 0) {
            loginCheck.setCheckMarkDrawable(null);
            loginCheck.setChecked(false);
        }
        else
        {
            loginCheck.setCheckMarkDrawable(R.drawable.ic_check_green_38dp);
            loginCheck.setChecked(true);
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        loginCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loginCheck.isChecked()){
                    Log.d("false id",id);
                    loginCheck.setCheckMarkDrawable(null);
                    loginCheck.setChecked(false);
                    dbhelper.updateAutoLogin(database,id,0);


                }
                else{
                    Log.d("true id",id);
                    loginCheck.setCheckMarkDrawable(R.drawable.ic_check_green_38dp);
                    loginCheck.setChecked(true);
                    dbhelper.updateAutoLogin(database,id,1);
                }

            }

        });

        changePw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ChangePw.class);
                intent.putExtra("userid",id);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initFont(){
        Typeface typeface = Typeface.createFromAsset( getActivity().getAssets() , "font/nanumgothic.ttf" );
    }
    private void setFontAllView(ViewGroup view){

        for(int i=0;i<view.getChildCount();i++){

            View child = view.getChildAt(i);
            if(child instanceof TextView){
                ((TextView)child).setTypeface(typeface);
            }
            else if(child instanceof ViewGroup){
                setFontAllView((ViewGroup)view);
            }
        }

    }
}
