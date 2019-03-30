package com.example.ffff.android_2013150041;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText id,pw;
    TextView registerLink;
    TextView loginState;
    Button login;
    SQLiteHandle dbhelper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView loginState = (TextView) findViewById(R.id.state);
        id = (EditText) findViewById(R.id.id);
        pw = (EditText) findViewById(R.id.pw);
        dbhelper = new SQLiteHandle(getApplicationContext(),"user.db",null,1);
        database = dbhelper.getWritableDatabase();
        login = (Button) findViewById(R.id.loginBtn);
        registerLink = (TextView) findViewById(R.id.registerLink);

        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher match, String url) {
                return "";
            }
        };

        Linkify.addLinks(registerLink, Pattern.compile("가입하기"), "register://",null,mTransform);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //userId = dbhelper.autoLogin(database);

                    if (dbhelper.selectUser(database, id.getText().toString(), pw.getText().toString())) {
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        intent.putExtra("userid",id.getText().toString());
                        intent.putExtra("autologin", 0);
                        startActivity(intent);
                    }
                    else
                        loginState.setText("아이디 혹은 비밀번호를 확인해주세요.");
                }
                catch (SQLException e)
                {
                    loginState.setText("로그인에 실패했습니다.");
                }

            }
        });
        setFont(id);
        setFont(pw);
        autoLogin();
    }

    private void setFont(EditText text){
        Typeface typeface = Typeface.createFromAsset( getAssets() , "font/nanumgothic.ttf" );
        text.setTypeface(typeface);
    }
    private void autoLogin()
    {
        String userId = dbhelper.autoLogin(database);
        if(!TextUtils.isEmpty(userId))
        {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra("userid",userId);
            intent.putExtra("autologin", 1);
            startActivity(intent);
        }
    }
}
