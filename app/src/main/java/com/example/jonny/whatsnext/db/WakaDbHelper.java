package com.example.jonny.whatsnext.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jonny.whatsnext.WhatsNext;

public class WakaDbHelper extends SQLiteOpenHelper {

    private static WakaDbHelper mInstance = null;

    public WakaDbHelper(Context context) {
        super(context, WakaContract.DB_NAME, null, WakaContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + WakaContract.WakaEntry.TABLE + " ( " +
                WakaContract.WakaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WakaContract.WakaEntry.COL_WAKA_TITLE + " TEXT NOT NULL, " +
                WakaContract.WakaEntry.COL_WAKA_DESCRIPTION + " TEXT NOT NULL, " +
                WakaContract.WakaEntry.COL_WAKA_VALUE + " REAL NOT NULL, " +
                WakaContract.WakaEntry.COL_WAKA_COST + " REAL NOT NULL, " +
                WakaContract.WakaEntry.COL_WAKA_TIME + " REAL NOT NULL, " +
                WakaContract.WakaEntry.COL_WAKA_COMPLETED_ON + " DATETIME);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + WakaContract.WakaEntry.TABLE);
        onCreate(db);
    }

    public static WakaDbHelper getInstance() {
        if (mInstance == null) {
            mInstance = new WakaDbHelper(WhatsNext.getAppContext());
        }
        return mInstance;
    }
}