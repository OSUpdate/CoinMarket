package com.example.ffff.android_2013150041;

import android.app.Activity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("회원가입");
        final SQLiteHandle dbhelper = new SQLiteHandle(getApplicationContext(),"user.db",null,1);
        final SQLiteDatabase database = dbhelper.getWritableDatabase();
		final Button register = (Button) findViewById(R.id.register);

		final EditText id = (EditText) findViewById(R.id.registerId);
		final EditText pw = (EditText) findViewById(R.id.registerPw);
		final EditText pwCheck = (EditText) findViewById(R.id.registerCheckPw);
		final TextView state = (TextView)findViewById(R.id.state);
		register.setOnClickListener(new View.OnClickListener(){
		@Override
			public void onClick(View v){
				if(pw.getText().toString().equals(pwCheck.getText().toString())){
					//try문 처리
					try {
						if(dbhelper.insertUser(database, id.getText().toString(), pw.getText().toString()))
							finish();
						else
							state.setText("이미 존재하는 아이디입니다.");
					}
					catch (SQLException e)
					{
						state.setText("");
					}
				}
				else {
					//TextView에 아이디 비밀번호 실패명령 받음
				}
			}
		});
    }
}
