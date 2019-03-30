package com.example.ffff.android_2013150041;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddMarket extends AppCompatActivity {
    private int REQUEST_ACT = 1;
    private ArrayList<String> currency;
    private HashMap<String,String> available;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_market);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar ab = getSupportActionBar() ;
        ab.setTitle("거래소 추가");
        Intent intent = getIntent();
        id = intent.getStringExtra("userid");
        ListView listView;
        MarketAdapter adapter = new MarketAdapter();

        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_bittrex_word_mark),"Bittrex");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //MarketItem item = (MarketItem) parent.getItemAtPosition(position);
                Intent intent = new Intent(AddMarket.this,MarketKey.class);
                intent.putExtra("userid",id);
                startActivityForResult(intent,REQUEST_ACT);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ACT)
        {
            if(resultCode == RESULT_OK)
            {
                currency = (ArrayList<String>) data.getSerializableExtra("currency");
                available = (HashMap<String, String>)data.getSerializableExtra("available");
                setResult(RESULT_OK,data);
                finish();
            }
        }
    }
}
