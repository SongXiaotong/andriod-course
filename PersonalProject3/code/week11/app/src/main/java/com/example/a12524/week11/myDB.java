package com.example.a12524.week11;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class myDB extends SQLiteOpenHelper {
    private static final String DB_NAME= "system";
    private static final String ACCOUNT_TABLE = "account";
    private static final String COMMENT_TABLE = "comment";
    private static final int DB_VERSION = 2;
    private String loginUser = "";

    public myDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE1 = "CREATE TABLE if not exists "
                + ACCOUNT_TABLE
                + " (name TEXT PRIMARY KEY, password TEXT, head BLOB)";
        db.execSQL(CREATE_TABLE1);
        String CREATE_TABLE2 = "CREATE TABLE if not exists "
                + COMMENT_TABLE
                + " (name TEXT, comment TEXT, time TEXT, number INTEGER, status INTEGER, goodlist TEXT, Constraint commentid PRIMARY KEY (name, comment, time))";
        db.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS comments");
        this.onCreate(db);
    }
    public void upgradeComment(String username, String comment, String time, int number, int status, String user){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT goodlist FROM comment WHERE name = ? AND comment = ? AND time = ?", new String[]{username, comment, time});
        String oldlist = "";
        if(cursor.moveToFirst()){
            oldlist = cursor.getString(cursor.getColumnIndex("goodlist"));
        }
        Log.e("myDB", "old:"+oldlist + "status " + status);
        if(!havegood(oldlist, user) && status == 1){
            Log.e("myDB1", "add");
            if(!oldlist.equals(""))
                oldlist += ",";
            oldlist = oldlist + user;
        }
        else if(status == 0){
            Log.e("myDB1", "minus");
            oldlist = deleteSubString(oldlist, user);
        }
        ContentValues cv = new ContentValues();
        cv.put("number", number);
        cv.put("goodlist",oldlist);
        cv.put("status",status);
        Log.e("myDB", "new:"+oldlist);
        String whereClause11 = "name=? AND comment=? AND time=?";
        String[] str = {username, comment, time};
        db.update(COMMENT_TABLE, cv, whereClause11, str);
        Log.e("new status", String.valueOf(status));
        db.close();
    }
    public void insertAccount(String name, String password, byte[] img) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("password", password);
        cv.put("head", img);
        db.insert(ACCOUNT_TABLE, null, cv);
        db.close();
    }
    public void insertComment(String name, String comment, String time, int number, int status, String names) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("comment", comment);
        cv.put("time", time);
        cv.put("number", number);
        if(havegood(names, name))
            cv.put("status", 1);
        else
            cv.put("status", 0);
        cv.put("goodlist", names);
        db.insert(COMMENT_TABLE, null, cv);
        db.close();
    }
    public HashMap<String, Object> findAccount(String name) {
        SQLiteDatabase db = getWritableDatabase();

        HashMap<String, Object> map = new HashMap<String, Object>();
        try{
            Cursor cursor = db.rawQuery("SELECT name, password, head FROM account WHERE name = ?", new String[]{name});
            if(cursor.moveToFirst()){
                map.put("name", cursor.getString(cursor.getColumnIndex("name")));
                map.put("password", cursor.getString(cursor.getColumnIndex("password")));
                map.put("head", cursor.getBlob(cursor.getColumnIndex("head")));
            }

        }catch (NullPointerException e){

        }
        return map;
    }
    public void deleteComment(String name, String comment, String time){
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=? AND comment=? AND time=?";
        String[] whereArgs={name, comment, time};
        db.delete(COMMENT_TABLE, whereClause, whereArgs);
        db.close();
    }
    public ArrayList<CommentInfo> getComments() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(COMMENT_TABLE, null, null, null, null, null, null);
        ArrayList<CommentInfo> list = new ArrayList<CommentInfo>();
       if(cursor.moveToFirst()){
           do{
               HashMap<String, Object> user = findAccount(cursor.getString(cursor.getColumnIndex("name")));
               byte[] temp_head =(byte[]) user.get("head");
               CommentInfo comment;
               if(!havegood(cursor.getString(cursor.getColumnIndex("goodlist")), loginUser) ) {
                   comment = new CommentInfo(temp_head, cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("time")), cursor.getString(cursor.getColumnIndex("comment")), cursor.getInt(cursor.getColumnIndex("number")), R.mipmap.white, 0);
                    Log.e("comment status", String.valueOf(comment.getStatus()));
               }
               else{
                   comment = new CommentInfo(temp_head, cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("time")), cursor.getString(cursor.getColumnIndex("comment")), cursor.getInt(cursor.getColumnIndex("number")), R.mipmap.red, 1);
                   Log.e("comment status"+cursor.getString(cursor.getColumnIndex("name")), String.valueOf(comment.getStatus()));
               }
               list.add(comment);
           }while(cursor.moveToNext());
       }
        return list;
    }

    public String deleteSubString(String list, String name){
        String[] test = list.split(",");
        String res = "";
        for(int i = 0; i < test.length; ++i){

            if(!test[i].equals(name) && !res.equals("") && !test[i].equals(" ")){
                res = res + "," + test[i];
            }
            else if(!test[i].equals(name) && res.equals("") && !test[i].equals(" ")){
                res += test[i];
            }
        }
        return res;
    }
    public Boolean havegood(String liststring, String name){
        Log.e("have good", liststring+" "+name);
        if(liststring.equals("")){
            return false;
        }
        String[] all = liststring.split(",");
        for(int i = 0; i < all.length; ++i){
            if(all[i].equals(name)){
                return true;
            }
        }
            return false;

    }

    public void getUser(String username){
        this.loginUser = username;
    }

    public Boolean getStatus(String name, String comment, String time, String user){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT goodlist FROM comment WHERE name = ? AND comment = ? AND time = ?", new String[]{name, comment, time});
        String oldlist = "";
        if(cursor.moveToFirst()){
            oldlist = cursor.getString(cursor.getColumnIndex("goodlist"));
        }
        Log.e("old list in getstatus", oldlist);
        if(havegood(oldlist, user)){
            return true;
        }
        else{
            return false;
        }
    }

}
