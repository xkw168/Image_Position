package com.example.xkw.imagepos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by xkw on 2018/6/15.
 */

public class DataBaseUtil {

    private IndexedImageDbHelper dbHelper;
    private static final String TABLE_NAME = "local_image";

    public DataBaseUtil(Context context){
        dbHelper = new IndexedImageDbHelper(context);
    }

    public void insert(String uri, String location){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uri", uri);
        values.put("location", location);
        //利用SQLiteDatabase对象，对数据进行添加
        long insert = sqLiteDatabase.insert(TABLE_NAME, null, values);//这里的null就始终写成null就好了
    }

    public String query(String location){
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query(TABLE_NAME,
                    new String[]{"uri"},
                    "location = ?",
                    new String[] {location},
                    null, null, null);
            return cursor.getString(cursor.getColumnIndex("location"));
        }
        catch (Exception e){
            Log.e("DataBaseUtil:", e.toString());
        }
        finally {
            if (cursor != null){
                cursor.close();
            }
            if (db != null){
                db.close();
            }
        }
        return null;
    }
}
