package com.example.ffff.android_2013150041;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CoinAdapter extends BaseAdapter{
    private ArrayList<CoinItem> coinItemList = new ArrayList<CoinItem>();

    public CoinAdapter()
    {
    }

    @Override
    public int getCount() {
        return this.coinItemList.size();
    }

    @Override
    public CoinItem getItem(int position) {
        return this.coinItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.market_item, parent, false);
        }
        TextView nameTextView = (TextView) convertView.findViewById(R.id.textView) ;

        CoinItem coinItem = coinItemList.get(position);

        nameTextView.setText(coinItem.getName());

        return convertView;
    }
    public void addItem(String title, String available) {
        CoinItem item = new CoinItem();

        item.setName(title);
        item.setAvailable(available);

        coinItemList.add(item);
    }

}
