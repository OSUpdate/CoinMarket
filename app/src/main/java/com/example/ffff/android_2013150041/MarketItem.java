package com.example.ffff.android_2013150041;

import android.graphics.drawable.Drawable;

public class MarketItem {
    private Drawable icon;
    private  String name;

    public void setIcon(Drawable icon)
    {
        this.icon = icon;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Drawable getIcon() {
        return this.icon;
    }
    public String getName() {
        return this.name ;
    }

}
