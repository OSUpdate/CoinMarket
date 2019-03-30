package com.example.ffff.android_2013150041;

import android.app.Activity;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class UserTab extends Fragment{
    private int REQUEST_ACT = 1;
    private ArrayList<String> currency;
    private HashMap<String,String> available;
    private String id;
    private ListView listView;
    private CoinAdapter adapter = new CoinAdapter();
    private SQLiteHandle dbhelper;
    private SQLiteDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_user_tab, null);

        setHasOptionsMenu(true);
        listView = (ListView) view.findViewById(R.id.listview);
        HashMap<String,String> map = null;
        dbhelper = new SQLiteHandle(getActivity(),"user.db",null,1);
        database = dbhelper.getWritableDatabase();
        try{
            map = dbhelper.selectMarket(database, id);
        }
        catch (SQLException e)
        {
            //에러처리
        }
        if(map != null)
        {
            Parse parse = new Parse(map.get("apikey"),map.get("secret"));
            currency = parse.getCurrency();
            available = parse.getAvailable();
            listView.setAdapter(adapter);
            for(int i =0;i<available.size();i++) {
                Log.d("available",available.get(currency.get(i)));
                adapter.addItem(currency.get(i),"수량 : "+Double.toString(Double.parseDouble(available.get(currency.get(i)))));
            }
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() != null && getActivity() instanceof Main2Activity)
        {
            id = ((Main2Activity)getActivity()).getData();
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.addpopup,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.addApi:
                Intent intent = new Intent(getActivity(),AddMarket.class);
                intent.putExtra("userid",id);
                startActivityForResult(intent,REQUEST_ACT );
                break;
            case R.id.refreshApi:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> map = null;
                        try {
                            map = dbhelper.selectMarket(database, id);
                        } catch (SQLException e) {
                            //에러처리
                        }
                        if (map != null) {
                            Parse parse = new Parse(map.get("apikey"), map.get("secret"));
                            currency = parse.getCurrency();
                            available = parse.getAvailable();
                        }
                        for (int i = 0; i < available.size(); i++) {
                            Log.d("available", available.get(currency.get(i)));
                            if (adapter.getCount() > 0) {
                                for (int j = 0; j < adapter.getCount(); j++) {
                                    if (adapter.getItem(j).getName().equals(currency.get(i)))
                                        break;
                                }
                            } else
                                adapter.addItem(currency.get(i),"수량 : "+Double.toString(Double.parseDouble(available.get(currency.get(i)))));
                        }
                    }
                }).start();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id",id);
        outState.putStringArrayList("currency",currency);
        outState.putSerializable("available",available);
        outState.putParcelable("listadapter",listView.onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            id = savedInstanceState.getString("id");
            currency = savedInstanceState.getStringArrayList("currency");
            available = (HashMap<String, String>) savedInstanceState.getSerializable("available");
            listView.onRestoreInstanceState(savedInstanceState.getParcelable("listadapter"));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ACT)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                currency = (ArrayList<String>) data.getSerializableExtra("currency");
                available = (HashMap<String, String>)data.getSerializableExtra("available");
                listView.setAdapter(adapter);
                for(int i =0;i<available.size();i++) {
                    Log.d("available",available.get(currency.get(i)));
                    if(adapter.getCount() > 0) {
                        for (int j = 0; j < adapter.getCount(); j++) {
                            if (adapter.getItem(j).getName().equals(currency.get(i)))
                                break;
                        }
                    }
                    else
                        adapter.addItem(currency.get(i),"수량 : "+Double.toString(Double.parseDouble(available.get(currency.get(i)))));
                }



            }
        }
    }
}
