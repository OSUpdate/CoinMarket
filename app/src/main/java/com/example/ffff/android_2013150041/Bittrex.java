package com.example.ffff.android_2013150041;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Bittrex {
    private final String baseUrl = "https://bittrex.com/api/v1.1",method_public = "public",method_account="account",method_market="market";
    private final String ticker = "getticker",currencies = "getcurrencies", markets = "getmarkets", balances = "getbalances";
    private String apikey;
    private String secret;
    private Document market ;
    public Bittrex(String key, String secret)
    {
        this.apikey = key;
        this.secret = secret;
        /*
        String apikey = "295605dc7cdf496da8936c58f0e676d2";
        String url2 = "https://bittrex.com/api/v1.1/account/getbalances?apikey="+key+"&nonce=" +Encryption.getNonce();
        try {
            market = Jsoup.connect(url2)
                    .header("Content-Type","application/json")
                    .header("apisign",Encryption.encrySHA512("1106ef66f9b846a1927bf9662b890dc2",url2))
                    .ignoreContentType(true)
                    .get();
            Log.d("test",market.text());
        }
       catch (IOException e)
       {
           e.printStackTrace();
       }
       */
    }
    private String getResponse(String url)
    {
        try {
            return Jsoup.connect(url)
                    .header("Content-Type","application/json")
                    .ignoreContentType(true)
                    .get()
                    .text();
        }
        catch (IOException e)
        {
            //�뿉�윭
        }

        return null;
    }
    private String getResponse(String url,String key, String data)
    {
        try {
            return Jsoup.connect(url)
                    .header("Content-Type","application/json")
                    .data(key,"BTC-"+data)
                    .ignoreContentType(true)
                    .get()
                    .text();
        }
        catch (IOException e)
        {
            //�뿉�윭
        }

        return null;
    }
    private String getResponseAccount(String url)
    {
        try {
            return Jsoup.connect(url)
                    .header("Content-Type","application/json")
                    .header("apisign",Encryption.encrySHA512(this.secret,url))
                    .ignoreContentType(true)
                    .get()
                    .text();
        }
        catch (IOException e)
        {
            //�뿉�윭
        }

        return null;
    }
    private String getResponseAccount(String url,String key, String data)
    {
        try {
            return Jsoup.connect(url+"apikey="+apikey+"&nonce"+Encryption.getNonce()+"&"+key+"="+data)
                    .header("Content-Type","application/json")
                    .header("apisign",Encryption.encrySHA512(this.secret,url))
                    .ignoreContentType(true)
                    .get()
                    .text();
        }
        catch (IOException e)
        {
            //�뿉�윭
        }

        return null;
    }
    // public 硫붿냼�뱶�엯�땲�떎.
    public String getmarkets()
    {
        return getResponse(baseUrl+"/"+method_public+"/"+markets);
    }
    public String getcurrencise()
    {
        return getResponse(baseUrl+"/"+method_public+"/"+currencies);
    }
    public String getticker(String param)
    {
        return getResponse(baseUrl+"/"+method_public+"/"+ticker,"market",param);
    }
    //Account 硫붿냼�뱶�엯�땲�떎.
    public String getbalances()
    {
        return getResponseAccount(baseUrl+"/"+method_account+"/"+balances+"?"+"apikey="+this.apikey+"&nonce="+Encryption.getNonce());
    }
}
