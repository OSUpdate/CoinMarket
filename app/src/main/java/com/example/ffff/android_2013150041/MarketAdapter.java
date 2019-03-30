package com.example.ffff.android_2013150041;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class MarketAdapter extends BaseAdapter {
    private ArrayList<MarketItem> marketItemList = new ArrayList<MarketItem>();

    public MarketAdapter()
    {
    }

    @Override
    public int getCount() {
        return this.marketItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.marketItemList.get(position);
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
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView) ;
        TextView nameTextView = (TextView) convertView.findViewById(R.id.textView) ;

        MarketItem marketItem = marketItemList.get(position);

        iconImageView.setImageDrawable(marketItem.getIcon());
        nameTextView.setText(marketItem.getName());

        return convertView;
    }
    public void addItem(Drawable icon, String title) {
        MarketItem item = new MarketItem();

        item.setIcon(icon);
        item.setName(title);

        marketItemList.add(item);
    }

}
