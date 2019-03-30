package com.example.ffff.android_2013150041;


import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parse {
	private Bittrex bittrex;
	private JSONArray jsonArray = null;
    public Parse(String key,String secret)
    {
        bittrex = new Bittrex(key,secret);
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(bittrex.getbalances());
            if(jsonObject.get("success").equals(true))
            {
            	jsonArray = (JSONArray) jsonObject.get("result");
            }
        }
        catch (ParseException e)
        {

        }
    }
    public boolean checkParse()
    {
        if(jsonArray == null)
            return false;
        return true;
    }
    public ArrayList<String> getCurrency()
    {
    	ArrayList<String> result = new ArrayList<String>();
    	for(int i = 0; i <this.jsonArray.size();i++)
    	{
    		JSONObject temp = (JSONObject) jsonArray.get(i);
    		result.add(temp.get("Currency").toString());
    	}
        return result;
    }

    public HashMap<String,String> getAvailable()
    {
    	HashMap<String,String> innerMap = new HashMap<String,String>();
    	for(int i = 0; i <this.jsonArray.size();i++)
    	{

    		JSONObject temp = (JSONObject) jsonArray.get(i);
    		innerMap.put(temp.get("Currency").toString(),temp.get("Available").toString());
    		Log.d(temp.get("Currency").toString(),temp.get("Available").toString());
    	}
    	return innerMap;
    }

}
