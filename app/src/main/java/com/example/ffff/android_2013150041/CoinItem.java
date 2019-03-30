package com.example.ffff.android_2013150041;

import android.graphics.drawable.Drawable;

public class CoinItem {
    private String currency;
    private String available;

    public void setName(String name) {this.currency = name;}
    public void setAvailable(String available)
    {
        this.available = available;
    }
    public String getName() {
        return this.currency ;
    }
    public String getAvailable() {
        return this.available ;
    }

}
