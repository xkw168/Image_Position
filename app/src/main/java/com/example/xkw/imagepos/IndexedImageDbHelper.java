package com.example.xkw.imagepos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IndexedImageDbHelper extends SQLiteOpenHelper
{
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "image";
    private static final String TABLE_NAME = "local_image";

    //Constructor
    public IndexedImageDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //TODO: complete the fields according to the keys in record hashmap.
        /*String CREATE_TABLE_SQL = "CREATE TABLE " + LocalImage.TABLE_NAME + "("
                + LocalImage.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + ")";*/
        String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + "("
                + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + "uri varchar(50) NOT NULL,"
                + "location varchar(30) NOT NULL"
                + ")";
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys = 1;");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV )
    {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + LocalImage.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldV, int newV )
    {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + LocalImage.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }
}
