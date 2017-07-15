package com.example.jonny.whatsnext.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WakaDbHelper extends SQLiteOpenHelper {

    public WakaDbHelper(Context context) {
        super(context, WakaContract.DB_NAME, null, WakaContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + WakaContract.WakaEntry.TABLE + " ( " +
                WakaContract.WakaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WakaContract.WakaEntry.COL_WAKA_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WakaContract.WakaEntry.TABLE);
        onCreate(db);
    }
}
