package com.example.ffff.android_2013150041;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.*;
import java.util.HashMap;

public class SQLiteHandle extends SQLiteOpenHelper {
    public static final String tableName = "container";

    public SQLiteHandle(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    public void createTable(SQLiteDatabase db) {
        String userSql = "CREATE TABLE user(" +
                "userid text primary key,"+
                "userpw text,"+
                "auto_login integer);";

        String marketSql = "CREATE TABEL market("+
                "market text,"+
                "apikey text primary key,"+
                "secret text,"+
                "userid text);";
        try {
            db.execSQL(userSql);
            db.execSQL(marketSql);
        }
        catch (SQLException e) {
        }
    }
    public HashMap<String,String> selectMarket(SQLiteDatabase db, String id)
    {
        HashMap<String,String> map = new HashMap<String, String>();
        db.beginTransaction();
        try
        {
            String sql = "select apikey,secret from market where userid="+id;
            Cursor cursor = db.rawQuery(sql,null);
            db.setTransactionSuccessful();
            if(cursor.getCount() > 0)
            {
                map.put("apikey",cursor.getString(1));
                map.put("secret",cursor.getString(2));
                return map;
            }
            return null;
        }
        catch (SQLiteException e)
        {
            throw e;
        }
        finally {
            db.endTransaction();
        }
    }
    public boolean insertMarket(SQLiteDatabase db, String id, String market, String apikey, String secret)
    {
        db.beginTransaction();
        try
        {
            if(!selectUser(db,id))
            {
                String sql = "insert into market(market, apikey, secret,userid) " + " values(" + market + ", " + apikey + ", " + secret + ", "+id+");";
                db.execSQL(sql);
                db.setTransactionSuccessful();
                return true;
            }

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
        return false;
    }
    public boolean updateAutoLogin(SQLiteDatabase db,String id,int setAutoLogin)
    {
        int count;
        db.beginTransaction();
        try
        {
            if(selectUser(db,id)) {
                //String sql = "update user set userpw=" + newPw + " where userid= "+id+" and userpw= " + pw;
                ContentValues updateValues = new ContentValues();
                updateValues.put("auto_login",setAutoLogin);
                count = db.update("user",updateValues,"userid=?",new String[]{id});
                Log.d("count",Integer.toString(count));
                db.setTransactionSuccessful();
                if(count > 0)
                    return true;
            }

        }
        catch (SQLException e)
        {
            throw e;
        }
        finally {
            db.endTransaction();
        }
        return false;
    }
    public String autoLogin(SQLiteDatabase db)
    {
        db.beginTransaction();
        try
        {
            String sql = "select userid from user where auto_login = 1;";
            Cursor cursor = db.rawQuery(sql,null);
            db.setTransactionSuccessful();
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                return cursor.getString(0);
            }
            return null;

        }
        catch (SQLiteException e)
        {
            throw e;
        }
        finally {
            db.endTransaction();
        }


    }
    public boolean selectUser(SQLiteDatabase db, String id)
    {
        db.beginTransaction();
        try
        {
            String sql = "select userid from user where userid="+id ;
            Cursor cursor = db.rawQuery(sql,null);
            db.setTransactionSuccessful();
            if(cursor.getCount() > 0)
                return true;
            return false;
        }
        catch (SQLiteException e)
        {
            throw e;
        }
        finally {
            db.endTransaction();
        }

    }

    public boolean selectUser(SQLiteDatabase db, String id, String pw)
    {
        db.beginTransaction();
        try
        {
            String sql = "select userid from user where userid=" + id +" and userpw="+pw ;
            Cursor cursor = db.rawQuery(sql,null);
            db.setTransactionSuccessful();
            if(cursor.getCount() > 0)
                return true;
            return false;

        }
        catch (SQLiteException e)
        {
            throw e;
        }
        finally {
            db.endTransaction();
        }

    }
    public boolean insertUser(SQLiteDatabase db, String id, String pw)
    {
        db.beginTransaction();
        try
        {
            if(!selectUser(db,id,pw))
            {
                String sql = "insert into user(userid, userpw, auto_login) " + " values(" + id + ", " + pw + ", " + 0 + ");";
                db.execSQL(sql);
                db.setTransactionSuccessful();
                return true;
            }

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
        }
        return false;
    }

    public boolean updateUser(SQLiteDatabase db,String id ,String pw, String newPw)
    {
        int count;

	    db.beginTransaction();
	    try
	    {
	        if(selectUser(db,id,pw)) {
                //String sql = "update user set userpw=" + newPw + " where userid= "+id+" and userpw= " + pw;
                ContentValues updateValues = new ContentValues();
                updateValues.put("userpw",newPw);
                count = db.update("user",updateValues,"userid=? and userpw=?",new String[]{id,pw});
                Log.d("count",Integer.toString(count));
                db.setTransactionSuccessful();
                if(count > 0)
                    return true;

            }

	    }
	    catch(SQLiteException e)
	    {
	        throw e;
	    }
	    finally {
            db.endTransaction();
        }
        return false;
    }
}
