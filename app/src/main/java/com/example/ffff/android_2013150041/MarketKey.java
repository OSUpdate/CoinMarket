package com.example.ffff.android_2013150041;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarketKey extends AppCompatActivity {
    private ArrayList<String> currency;
    private HashMap<String,String> available;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_key);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final SQLiteHandle dbhelper = new SQLiteHandle(getApplicationContext(),"user.db",null,1);
        final SQLiteDatabase database = dbhelper.getWritableDatabase();
        ActionBar ab = getSupportActionBar() ;
        Intent intent = getIntent();
        id = intent.getStringExtra("userid");
        ab.setTitle("비트렉스");
        final EditText apikey = (EditText) findViewById(R.id.apikey);
        final EditText secret = (EditText) findViewById(R.id.secret);
        apikey.setText("");
        secret.setText("");
        final TextView state = (TextView) findViewById(R.id.state);
        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {

                        Parse parse = new Parse(apikey.getText().toString(),secret.getText().toString());
                        if(parse.checkParse()) {
                            Intent intent = new Intent();
                            currency = parse.getCurrency();
                            available = parse.getAvailable();
                            intent.putExtra("currency",currency);
                            intent.putExtra("available",available);
                            dbhelper.insertMarket(database,id,"bittrex", apikey.getText().toString(),secret.getText().toString());
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                        else
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    state.setText("잘못된 값입니다.");
                                }
                            });
                        }


                /*Parse parse = null;
                try {
                    parse = new Parse("https://bittrex.com/api/v1.1/public/getticker");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                final Document doc = parse.getDoc();
                */
                        //Log.d("document",doc.text());
                /*
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), doc.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                */
                        //123   Toast.makeText(getApplicationContext(),doc.body().toString(),Toast.LENGTH_LONG).show();
                    }
                }).start();
            }
        });


        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bittrex));

    }
}
