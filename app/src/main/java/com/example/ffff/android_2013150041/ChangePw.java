package com.example.ffff.android_2013150041;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class ChangePw extends AppCompatActivity {
	private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("비밀번호 변경");

		Intent intent = getIntent();
		id = intent.getStringExtra("userid");
		final SQLiteHandle dbhelper = new SQLiteHandle(getApplicationContext(),"user.db",null,1);
		final SQLiteDatabase database = dbhelper.getWritableDatabase();

		final Button changePw = (Button) findViewById(R.id.changePw);
		final EditText pw = (EditText) findViewById(R.id.pw);
		final EditText newPw = (EditText) findViewById(R.id.newPw);
		final EditText newPwCheck = (EditText) findViewById(R.id.newCheckPw);
		final TextView state = (TextView)findViewById(R.id.state);
		Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
		changePw.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
				if(newPw.getText().toString().equals(newPwCheck.getText().toString()))
				{
		    		try
		    		{
		    		    if (newPw.getText().toString().equals(newPwCheck.getText().toString())) {
							Toast.makeText(getApplicationContext(),id,Toast.LENGTH_SHORT).show();
							if(dbhelper.updateUser(database,id ,pw.getText().toString(), newPw.getText().toString()))
								finish();
							else
								state.setText("비밀번호를 확인해주세요.");
						}
		    		}
		    		catch(SQLiteException e)
		    		{

		    		}
				}
            }
        });

    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId())
		{
			case android.R.id.home:
					finish();
					return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
